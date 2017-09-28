package com.seoulmobileplatform.waterlife.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;

import com.seoulmobileplatform.waterlife.MenuActivity.MineralSpring;
import com.seoulmobileplatform.waterlife.MenuActivity.NFCActivity;
import com.seoulmobileplatform.waterlife.MenuActivity.SetWeightActivity;
import com.seoulmobileplatform.waterlife.MenuActivity.SettingActivity;
import com.seoulmobileplatform.waterlife.R;
import com.seoulmobileplatform.waterlife.SetGoalActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-15
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

/*===================================================
                 'Settings' Fragment
 ===================================================*/
public class Fragment_Settings extends Fragment {

    private Context mContext;
    private View rootView;

    public Fragment_Settings() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO skydoves - Auto-generated method stub
        View rootView = inflater.inflate(R.layout.layout_settings, container, false);
        this.rootView = rootView;
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // getContext
        mContext = getContext();
    }


    /*===================================================
                       Menu Button Click
     ===================================================*/
    @OnClick({R.id.settings_tv_nfc, R.id.settings_tv_recommend, R.id.settings_tv_goal, R.id.settings_tv_mineralspring, R.id.settings_tv_setting})
    void MenuClick(View v) {
        if (v.getScaleX() == 1) {
            ViewCompat.animate(v)
                    .setDuration(200)
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setInterpolator(new CycleInterpolator(0.5f))
                    .setListener(new ViewPropertyAnimatorListener() {

                        @Override
                        public void onAnimationStart(final View view) {
                        }

                        @Override
                        public void onAnimationEnd(final View v) {

                            switch (v.getId()) {
                                case R.id.settings_tv_nfc:
                                    Intent intent0 = new Intent(mContext, NFCActivity.class);
                                    startActivity(intent0);
                                    break;

                                case R.id.settings_tv_recommend :
                                    Intent intent1 = new Intent(mContext, SetWeightActivity.class);
                                    startActivity(intent1);
                                    break;

                                case R.id.settings_tv_goal:
                                    Intent intent2 = new Intent(mContext, SetGoalActivity.class);
                                    startActivity(intent2);
                                    break;

                                case R.id.settings_tv_mineralspring :
                                    Intent intent3 = new Intent(mContext, MineralSpring.class);
                                    startActivity(intent3);
                                    break;

                                case R.id.settings_tv_setting :
                                    Intent intent4 = new Intent(mContext, SettingActivity.class);
                                    startActivity(intent4);
                                    break;
                            }
                        }

                        @Override
                        public void onAnimationCancel(final View view) {
                        }
                    })
                    .withLayer()
                    .start();
        }
    }
}
