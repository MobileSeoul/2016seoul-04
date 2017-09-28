package com.seoulmobileplatform.waterlife.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seoulmobileplatform.waterlife.MainActivity;
import com.seoulmobileplatform.waterlife.MakeAlarmActivity;
import com.seoulmobileplatform.waterlife.R;
import com.seoulmobileplatform.waterlife.Systems.ExceptionLog;
import com.seoulmobileplatform.waterlife.Systems.Systems_Alarm;
import com.seoulmobileplatform.waterlife.Systems.Systems_DB;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-18
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

/*===================================================
              'SetNotiification' Fragment
 ===================================================*/
    public class Fragment_SetNotification extends Fragment {

    // Systems
    private Context mContext;
    private View rootView;

    // Alarm System
    Systems_Alarm systems_alarm;

    // ListView
    ArrayList<Listviewitem> data;
    ListviewAdapter adapter;

    // DB
    private String TAG = "Fragment_SetNotification";
    private SQLiteDatabase db;
    private Systems_DB systems_db;
    private ExceptionLog exceptionLog;

    public Fragment_SetNotification() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO skydoves - Auto-generated method stub
        View rootView = inflater.inflate(R.layout.layout_setnotification, container, false);
        this.rootView = rootView;
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // getContext
        mContext = getContext();

        // Initialize DB & Alarm
        db = mContext.openOrCreateDatabase(getResources().getString(R.string.db), Context.MODE_PRIVATE, null);
        systems_db = new Systems_DB(mContext, db);
        exceptionLog = new ExceptionLog(mContext, TAG);
        systems_alarm = new Systems_Alarm(mContext);

        // Initialize
        Initialize();
    }

    /*===================================================
                      Initialize
    ===================================================*/
    private void Initialize()
    {
        // TODO skydoves - Alarm Record Init : Auto-generated method stub
        // ListView
        ListView listView=(ListView)rootView.findViewById(R.id.setnotification_listview);
        data=new ArrayList();

        // Set Adapter and Click Listner //
        adapter=new ListviewAdapter(mContext, R.layout.item_alarmrecord, data);
        listView.setAdapter(adapter);

        // # Add Items # //
        Cursor cursor = db.rawQuery("select * from AlarmList", null);
        try{
            if (cursor != null && cursor.getCount() > 0 && cursor.moveToLast()) {
                do {
                    //  get columns
                    int requestcode = cursor.getInt(0);
                    String days = cursor.getString(1);
                    String starttime = cursor.getString(2);
                    String endtime = cursor.getString(3);
                    String interval = cursor.getString(4);

                    String sday = "";
                    if(days.contains("0"))
                        sday += "일 ";
                    if(days.contains("1"))
                        sday += "월 ";
                    if(days.contains("2"))
                        sday += "화 ";
                    if(days.contains("3"))
                        sday += "수 ";
                    if(days.contains("4"))
                        sday += "목 ";
                    if(days.contains("5"))
                        sday += "금 ";
                    if(days.contains("6"))
                        sday += "토 ";

                    // add ListItem
                    Listviewitem listviewitem = new Listviewitem(requestcode, sday, starttime + " ~ " + endtime, "간격 : " + interval + "시간");
                    data.add(listviewitem);
                } while (cursor.moveToPrevious());
            }

            // If cursor == null, Message Show
            if(cursor.getCount() == 0)
                rootView.findViewById(R.id.setnotification_tv_message).setVisibility(View.VISIBLE);
        }
        catch (Exception e){
                exceptionLog.Exception_DB_Get(e.getMessage());
        }
        finally {
            cursor.close();
        }
    }

    // Click Fab Button
    @OnClick(R.id.setnotification_fab)
    void Click_Fab_Add(View v)
    {
        ViewCompat.animate(v)
                .setDuration(150)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setInterpolator(new CycleInterpolator(0.5f))
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(final View view) {
                    }
                    @Override
                    public void onAnimationEnd(final View v) {
                        Intent intent = new Intent(mContext, MakeAlarmActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onAnimationCancel(final View view) {
                    }
                })
                .withLayer()
                .start();
    }


    /*===================================================
                        ListView Item
    ===================================================*/
    //region
    private class Listviewitem {
        private int requestcode;
        private String days;
        private String times;
        private String interval;

        public int getrequestcode(){return requestcode;}
        public String getdays(){return days;}
        public String gettimes(){return times;}
        public String getinterval(){return interval;}

        public Listviewitem(int index, String amount, String date, String interval){
            this.requestcode = index;
            this.days = amount;
            this.times=date;
            this.interval = interval;
        }
    }

    /*===================================================
                        ListView Adapter
     ===================================================*/
    private class ListviewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<Listviewitem> data;
        private int layout;

        public ListviewAdapter(Context context, int layout, ArrayList<Listviewitem> data){
            this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data=data;
            this.layout=layout;
        }

        @Override
        public int getCount(){return data.size();}

        @Override
        public String getItem(int position){return data.get(position).getdays();}

        @Override
        public long getItemId(int position){return position;}

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){

            if(convertView==null)
                convertView=inflater.inflate(layout,parent,false);

            Listviewitem listviewitem=data.get(position);

            // Set item Views //
            // Textview : days
            TextView tv_item_days = (TextView)convertView.findViewById(R.id.item_alarmrecord_tv_days);
            tv_item_days.setText(listviewitem.getdays());

            // Textview : Start-End Time
            TextView tv_item_times = (TextView)convertView.findViewById(R.id.item_alarmrecord_tv_times);
            tv_item_times.setText(listviewitem.gettimes());

            // Textview : interval Time
            TextView tv_item_interval = (TextView)convertView.findViewById(R.id.item_alarmrecord_tv_interval);
            tv_item_interval.setText(listviewitem.getinterval());

            // Image Button : delete button
            ImageButton ibtn_item_delete = (ImageButton)convertView.findViewById(R.id.item_alarmrecord_btn_delete);
            ibtn_item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
                    alertDlg.setTitle("알림");

                    // Yes - delete
                    alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick( DialogInterface dialog, int which )
                        {
                            try {
                                // Cancel Alarm
                                systems_alarm.cancelAlarm(data.get(position).getrequestcode());

                                // remove a Alarm Record
                                systems_db.deleteAlarm(data.get(position).getrequestcode());
                                data.remove(position);

                                // update
                                Toast.makeText(mContext, "알림이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                                ((MainActivity) MainActivity.mContext).UpdateFragments();
                            }
                            catch (Exception e){
                                exceptionLog.Exception_CancelAlarm(e.getMessage());
                            }
                        }
                    });

                    // No - cancel
                    alertDlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDlg.setMessage(String.format("해당 알림을 지우시겠습니까?"));
                    alertDlg.show();
                }
            });

            return convertView;
        }
    }
    //endregion

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Close DB
        if(db != null)
            db.close();
    }
}
