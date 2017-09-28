package com.seoulmobileplatform.waterlife.Systems;

import android.content.Context;
import android.util.Log;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-15
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class ExceptionLog {

    private Context mContext;
    private String Tag = "Exceptions";

    public ExceptionLog(Context context, String Tag){
        this.mContext = context;
    }

    public void Exception_DB_Create(String message)
    {
        Log.e(Tag, "DB Create Exception : " + message);
    }

    public void Exception_DB_Get(String message)
    {
        Log.e(Tag, "DB Cursor Exception : " + message);
    }

    public void Exception_DateInit(String message)
    {
        Log.e(Tag, "Date Init Exception : " + message);
    }

    public void Exception_NFCWrite(String message)
    {
        Log.e(Tag, "NFC Write Exception : " + message);
    }

    public void Exception_SetAlarm(String message)
    {
        Log.e(Tag, "Set Alarm Exception : " + message);
    }

    public void Exception_CancelAlarm(String message)
    {
        Log.e(Tag, "Cancel Alarm Exception : " + message);
    }

    public void Exception_XMLParsing(String message)
    {
        Log.e(Tag, "Xml Parsing Exception : " + message);
    }

    public void Exception_NaverMapView(String message)
    {
        Log.e(Tag, "NaverMapView Exception : " + message);
    }
}
