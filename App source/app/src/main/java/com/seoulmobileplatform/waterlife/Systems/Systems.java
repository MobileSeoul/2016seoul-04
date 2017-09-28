package com.seoulmobileplatform.waterlife.Systems;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.seoulmobileplatform.waterlife.MainActivity;
import com.seoulmobileplatform.waterlife.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-15
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class Systems {

    Context mContext;

    public Systems(Context context)
    {
        this.mContext = context;
    }


    /*=========================================================================
                                  Preferences
    ==========================================================================*/

    /// # Get Values with key # ///
    public boolean getBoolean(String key, boolean default_value)
    {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        return pref.getBoolean(key, default_value);
    }

    public int getInt(String key, int default_value)
    {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        return pref.getInt(key, default_value);
    }

    public String getString(String key, String default_value)
    {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        return pref.getString(key, default_value);
    }


    /// # Put Values with key # ///
    public void putBoolean(String key, boolean default_value)
    {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, default_value).commit();
    }

    public void putInt(String key, int default_value)
    {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, default_value).commit();
    }

    public void putString(String key, String default_value)
    {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, default_value).commit();
    }


    /*===================================================
                        Date Systems
     ===================================================*/

    // get far date
    public static String getFarDay(int far) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, far);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(cal.getTime());
        return currentDateandTime;
    }

    // get Date Number of Week
    public static int getDateDay(String date, String dateType) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
            Date nDate = dateFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(nDate);
            return cal.get(Calendar.DAY_OF_WEEK) - 1;
        } catch (Exception e) {
        }
        return -1;
    }


    /*=========================================================================
                                   Notification
    ==========================================================================*/

    // Send Notification
    public void sendNotification(String title, String message, int number, boolean vibrate, boolean sound) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.img_waterdrop)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_waterdrop))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setNumber(number)
                .setTicker(message)
                .setContentIntent(pendingIntent);

        if(vibrate) notificationBuilder.setVibrate(new long[]{2000});
        if(sound) notificationBuilder.setSound(defaultSoundUri);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1004, notificationBuilder.build());
    }


    /*=========================================================================
                                     NetWork
    ==========================================================================*/

    // Check Network is Available
    public boolean isNetworkAvailable(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null
                    && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null
                        && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }
}
