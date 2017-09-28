package com.seoulmobileplatform.waterlife;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.seoulmobileplatform.waterlife.MenuActivity.SetWeightActivity;
import com.seoulmobileplatform.waterlife.Systems.ExceptionLog;
import com.seoulmobileplatform.waterlife.Systems.Systems;
import com.seoulmobileplatform.waterlife.Systems.Systems_DB;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-15
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class StartActivity extends AppIntro {

    // DB
    private Systems systems;
    private SQLiteDatabase db;
    private Systems_DB systems_db;
    private ExceptionLog exceptionLog;

    private final String TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Systems
        systems = new Systems(this);
        exceptionLog = new ExceptionLog(this, TAG);

        // Check isAlready set
        if(!systems.getString("WaterGoal", "null").equals("null")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            // DB Init
            try {
                db = openOrCreateDatabase(getResources().getString(R.string.db), Context.MODE_PRIVATE, null);
                systems_db = new Systems_DB(this, db);
                systems_db.CreateTable();
            }
            catch (Exception e){
                exceptionLog.Exception_DB_Create(e.getMessage());
            }
        }

        // add Slides
        addSlide(SlideFragment.newInstance(R.layout.intro1));
        addSlide(SlideFragment.newInstance(R.layout.intro2));
        addSlide(SlideFragment.newInstance(R.layout.intro3));
        addSlide(SlideFragment.newInstance(R.layout.intro4));
        setDoneText("시작하기");
    }

    // Skip
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        ActivityStart();
    }

    // Done
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        ActivityStart();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    // First Activity Start
    private void ActivityStart()
    {
        Intent intent = new Intent(this, SetWeightActivity.class);
        startActivity(intent);
        finish();
    }

}
