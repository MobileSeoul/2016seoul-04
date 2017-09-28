package com.seoulmobileplatform.waterlife.Systems;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-16
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class Systems_DB {

    Context mContext;
    private SQLiteDatabase db;
    private Systems systems;
    private String TAG = "System_DB";
    private ExceptionLog exceptionLog;

    public Systems_DB(Context context, SQLiteDatabase db)
    {
        this.mContext = context;
        this.db = db;
        this.systems = new Systems(context);
        this.exceptionLog = new ExceptionLog(context, TAG);
    }

    // Create DB Table //
    public void CreateTable(){
        // RecordList
        String query_recordList = "CREATE TABLE RecordList(pk_recordid integer primary key autoincrement, recorddate DATETIME DEFAULT (datetime('now','localtime')), amount varchar(4));";
        db.execSQL(query_recordList);

        // AlarmList
        String query_alarmList = "CREATE TABLE AlarmList(requestcode integer primary key, daylist varchar(20), starttime varchar(20), endtime varchar(20), interval integer);";
        db.execSQL(query_alarmList);

        Log.e(TAG, "SUCCESS Create Table");
    }

    // Add a Record //
    public void addRecord(String amount)
    {
        String query_addRecord = "Insert Into RecordList (amount) Values('"+  amount +"');";
        db.execSQL(query_addRecord);
        Log.e(TAG, "SUCCESS Record Inserted : " + amount);
    }

    // remove a Record //
    public void deleteRecord(int index)
    {
        String query_addRecord = "Delete from RecordList Where pk_recordid = '" + index + "'";
        db.execSQL(query_addRecord);
        Log.e(TAG, "SUCCESS Record Deleted : " + index);
    }

    // get Today Drink Amount //
    public int getDayDrinkAmount(String datetime)
    {
        int TotalAmount = 0;
        Cursor cursor = db.rawQuery("select * from RecordList where recorddate >= datetime(date('"+datetime+"','localtime')) and recorddate < datetime(date('"+datetime+"', 'localtime', '+1 day'))" , null);
        try {
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    TotalAmount += cursor.getInt(2);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            exceptionLog.Exception_DB_Get(e.getMessage());
        }
        finally {
            cursor.close();
        }
        return TotalAmount;
    }

    // Add a Alarm //
    public void addAlarm(int requestcode, String daylist, String starttime, String endtime, int interval)
    {
        String query_addRecord = "Insert Into AlarmList Values("+requestcode+",'" + daylist +"','" + starttime + "','" + endtime + "', " + interval +");";
        db.execSQL(query_addRecord);
        Log.e(TAG, "SUCCESS Record Inserted : " + requestcode);
    }

    // remove a Record //
    public void deleteAlarm(int requestcode)
    {
        String query_addRecord = "Delete from AlarmList Where requestcode = '" + requestcode + "'";
        db.execSQL(query_addRecord);
        Log.e(TAG, "SUCCESS Record Deleted : " + requestcode);
    }
}
