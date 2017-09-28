package com.seoulmobileplatform.waterlife.MenuActivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.seoulmobileplatform.waterlife.R;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-20
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class SettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }
}