package com.seoulmobileplatform.waterlife.Systems;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seoulmobileplatform.waterlife.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-19
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class AlarmBootReceiver extends BroadcastReceiver {

    private Context mContext;

    // DB
    private SQLiteDatabase db;

    // Alarm Systems
    Systems_Alarm systems_alarm;
    private GregorianCalendar mCalendar;
    private AlarmManager mManager;
    private final int requestC = 99000000;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // TODO skydoves - Broadcast Boot Receiver : Auto-generated method stub
            // Initialize DB & Alarm Systems
            mContext = context;
            db = context.openOrCreateDatabase(context.getResources().getString(R.string.db), Context.MODE_PRIVATE, null);
            systems_alarm = new Systems_Alarm(context);
            mCalendar = new GregorianCalendar();
            mManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

            try {
                Cursor cursor = db.rawQuery("select * from AlarmList", null);
                if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                    do {
                        // Get requestcode
                        int requestcode = cursor.getInt(0);

                        // Reset all of Alarms
                        systems_alarm.setAlarm(requestcode);

                        // Reset LocalWeather Alarm
                        mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), 7, 0);
                        mCalendar.add(Calendar.DATE, 1);
                        mManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 1200 * 1000, pendingIntent(requestC));

                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
            catch (Exception e){
                // None
            }
        }
    }

    // # Pending Intent # //
    private PendingIntent pendingIntent(int requestcode) {
        // TODO skydoves - PendingIntent with Handling requestcode
        Intent intent = new Intent(mContext, LocalWeatherReceiver.class);
        intent.putExtra("requestcode", requestcode);
        PendingIntent sender = PendingIntent.getBroadcast(mContext, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return sender;
    }
}