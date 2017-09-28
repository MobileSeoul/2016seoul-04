package com.seoulmobileplatform.waterlife.Systems;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-20
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class AlarmReceiver extends BroadcastReceiver {

    private int requestcode, occurdateint;

    // Systems
    Systems systems;
    Systems_Alarm systems_alarm;
    GregorianCalendar mCalendar;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO skydoves - Broadcast Alarm Receiver : Auto-generated method stub
        // get requestcode
        requestcode = intent.getIntExtra("requestcode", -1);
        occurdateint = intent.getIntExtra("occurdateint", -1);

        // Initialize Systems
        systems = new Systems(context);
        systems_alarm = new Systems_Alarm(context);
        mCalendar = new GregorianCalendar();

        // # Check Validate of requestcode # //
        if (requestcode != -1) {
            int cdate = occurdateint - systems.getDateDay(systems.getFarDay(0), "yyyy-MM-dd");
            String[] EndTime = systems_alarm.getEndTime(requestcode).split(",");

            // Over EndTime : Reset next Alarm
            if(cdate < 0)
                ResetAlarm(0);
            else if(cdate == 0 && (mCalendar.get(Calendar.HOUR_OF_DAY) > Integer.parseInt(EndTime[0])))
                ResetAlarm(1);
            else if(cdate == 0 && (mCalendar.get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(EndTime[0])) && (mCalendar.get(Calendar.MINUTE) > Integer.parseInt(EndTime[1])))
                ResetAlarm(2);

            // # Between Start-End Time # //
            else {
                // get Preference Setting Value
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                boolean vibrate = prefs.getBoolean("Setting_Vibrate", true);
                boolean sound = prefs.getBoolean("Setting_Sound", true);

                // Send Notification
                systems.sendNotification("물 한잔 해요", "수분을 보충할 시간입니다!", 1, vibrate, sound);
            }
        }
    }

    // Cancel & Reset new Alarm
    private void ResetAlarm(int resetType)
    {
        // TODO skydoves -  Cancel & Reset Alarm : between resetType
        systems_alarm.cancelAlarm(requestcode);
        systems_alarm.setAlarm(requestcode);
    }
}
