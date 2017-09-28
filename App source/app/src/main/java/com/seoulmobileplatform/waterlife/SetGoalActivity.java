package com.seoulmobileplatform.waterlife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.seoulmobileplatform.waterlife.Systems.Systems;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-15
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class SetGoalActivity extends AppCompatActivity {

    private Systems systems;

    @Bind(R.id.setgoal_edt_goal)
    EditText editText_goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);
        ButterKnife.bind(this);

        // Systems
        systems = new Systems(this);

        // Set WaterGoal
        editText_goal.setText(systems.getString("WaterGoal", "2000"));
    }

    // Button Click : Set Water Goal
    @OnClick(R.id.setgoal_btn_setgoal)
    void Btn_SetGoal(View v){
        if(!editText_goal.getText().toString().equals("") && !editText_goal.getText().toString().equals("0")){
            if(systems.getString("WaterGoal", "null").equals("null")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "목표치를 재설정했습니다.", Toast.LENGTH_SHORT).show();
            systems.putString("WaterGoal", editText_goal.getText().toString());
            finish();
        }
        else
            Toast.makeText(this, "올바른 목표치를 설정해 주세요!", Toast.LENGTH_SHORT).show();
    }

}
