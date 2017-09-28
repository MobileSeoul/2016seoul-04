package com.seoulmobileplatform.waterlife.Systems;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.seoulmobileplatform.waterlife.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-20
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class Systems_Alarm {

    // Context & Systems
    private Context mContext;
    private Systems systems;

    // Alarm Systems
    private AlarmManager mManager;
    private GregorianCalendar mCalendar;

    // DB
    private final String TAG = "Systems_Alarm";
    private SQLiteDatabase db;
    private ExceptionLog exceptionLog;

    public Systems_Alarm(Context context){
        // Context & Systems
        this.mContext = context;
        mManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

        // Initialize DB
        systems = new Systems(mContext);
        db = mContext.openOrCreateDatabase(mContext.getResources().getString(R.string.db), Context.MODE_PRIVATE, null);
        exceptionLog = new ExceptionLog(mContext, TAG);
    }

    // # Set New Alarm # //
    public void setAlarm(int requestcode)
    {
        // TODO skydoves - Set a New Alarm with auto Handling
        // get Local Time
        mCalendar = new GregorianCalendar();
        String daylist="", StartTime="", EndTime="";
        int interval=1;

        // get Alarm Data
        Cursor cursor = db.rawQuery("select * from AlarmList where requestcode = " + requestcode , null);
        try {
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    daylist = cursor.getString(1);
                    StartTime = cursor.getString(2);
                    EndTime = cursor.getString(3);
                    interval = cursor.getInt(4);
                } while (cursor.moveToNext());
            }

            // get Start-End Tiem
            String[] sdate_h = StartTime.split("시 ");
            String[] sdate_m = sdate_h[1].split("분");
            String[] edate_h = EndTime.split("시 ");
            String[] edate_m = edate_h[1].split("분");
            // Check Date is on Week
            boolean check_overday = false;
            String[] mDayList = daylist.split(",");
            for(int i=0; i<mDayList.length ; i++) {
                if(systems.getDateDay(systems.getFarDay(0), "yyyy-MM-dd") <= Integer.parseInt(mDayList[i])) {
                    int c_date = Integer.parseInt(mDayList[i]) - systems.getDateDay(systems.getFarDay(0), "yyyy-MM-dd");
                    String d_date = systems.getFarDay(c_date);
                    String[] l_date = d_date.split("-");

                    // Check over EndTime
                    if(c_date < 0) continue;
                    else if (c_date == 0 && (mCalendar.get(Calendar.HOUR_OF_DAY) > Integer.parseInt(edate_h[0]))) continue;
                    else if(c_date == 0 && (mCalendar.get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(edate_h[0])) && mCalendar.get(Calendar.MINUTE) > Integer.parseInt(edate_m[0])) continue;

                    // Register new Alarm
                    mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), Integer.parseInt(l_date[2]), Integer.parseInt(sdate_h[0]), Integer.parseInt(sdate_m[0]));
                    mManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), interval * 3600 * 1000, pendingIntent(requestcode, Integer.parseInt(mDayList[i])));
                    check_overday = true;
                    break;
                }
            }

            // If Alarm Date is Over a Week
            if(!check_overday){
                int c_date = 7 - (systems.getDateDay(systems.getFarDay(0), "yyyy-MM-dd")  - Integer.parseInt(mDayList[0]));
                String d_date = systems.getFarDay(c_date);
                String[] l_date = d_date.split("-");

                // Register new Alarm
                mCalendar.set(mCalendar.get(Calendar.YEAR), Integer.parseInt(l_date[1])-1, Integer.parseInt(l_date[2]), Integer.parseInt(sdate_h[0]), Integer.parseInt(sdate_m[0]));
                mManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), interval * 3600 * 1000, pendingIntent(requestcode, Integer.parseInt(mDayList[0])));
            }
        } catch (Exception e) {
            exceptionLog.Exception_DB_Get(e.getMessage());
        }
        finally {
            cursor.close();
        }
    }

    // # Get Alarm End Time # //
    public String getEndTime(int requestcode) {
        // TODO skydoves - Get End Time of Alarm
        Cursor cursor = db.rawQuery("select * from AlarmList where requestcode = " + requestcode, null);
        try {
            String e_Time="";
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    e_Time = cursor.getString(3);
                } while (cursor.moveToNext());
            }

            String[] eTime_h = e_Time.split("시 ");
            String[] eTime_m = eTime_h[1].split("분");

            return eTime_h[0] + "," + eTime_m[0];
        }
        catch (Exception e)
        {
            exceptionLog.Exception_DB_Get(e.getMessage());
        }
        finally {
            cursor.close();
        }
        return null;
    }

    // # Cancel Alarm # //
    public void cancelAlarm(int requestcode) {
        // TODO skydoves - Cancel Alarm
        try{
            if(pendingIntent(requestcode, -1) != null) {
                mManager.cancel(pendingIntent(requestcode, -1));
                Log.e(TAG, "Alarm Canceld");
            }
        }
        catch (Exception e){
            exceptionLog.Exception_DB_Get(e.getMessage());
        }
    }

    // # Pending Intent # //
    private PendingIntent pendingIntent(int requestcode, int occurdateint) {
        // TODO skydoves - PendingIntent with Handling requestcode
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("requestcode", requestcode);
        intent.putExtra("occurdateint", occurdateint);
        PendingIntent sender = PendingIntent.getBroadcast(mContext, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return sender;
    }
}
