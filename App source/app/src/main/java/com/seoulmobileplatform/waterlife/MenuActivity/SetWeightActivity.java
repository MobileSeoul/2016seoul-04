package com.seoulmobileplatform.waterlife.MenuActivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.seoulmobileplatform.waterlife.R;
import com.seoulmobileplatform.waterlife.SetGoalActivity;
import com.seoulmobileplatform.waterlife.Systems.Systems;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-21
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class SetWeightActivity extends AppCompatActivity {

    private Systems systems;

    @Bind(R.id.setweight_edt_weight)
    EditText editText_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setweight);
        ButterKnife.bind(this);

        // Systems
        systems = new Systems(this);

        // Set UserWeight
        editText_weight.setText(String.valueOf(systems.getInt("userWeight", 60)));
    }

    // Click Buttons
    @OnClick({R.id.setweight_btn_getrecommend, R.id.setweight_btn_setweight})
    void ClickBtn(View v)
    {
        if(!editText_weight.getText().toString().equals("")) {
            switch (v.getId()) {
                case R.id.setweight_btn_getrecommend:
                    // Get Reh Data
                    String Reh = systems.getString("Reh", "60");
                    Snackbar.make(editText_weight, "추천 수분 섭취량은 " + (Integer.parseInt(editText_weight.getText().toString()) * 30 + (500 - Integer.parseInt(Reh) * 3)) + "ml 입니다.",
                            Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).show();
                    break;

                case R.id.setweight_btn_setweight:
                    if(systems.getString("WaterGoal", "null").equals("null")) {
                        Intent intent = new Intent(this, SetGoalActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(this, "체중을 설정하였습니다.", Toast.LENGTH_SHORT).show();
                    systems.putInt("userWeight", Integer.parseInt(editText_weight.getText().toString()));
                    finish();
                    break;
            }
        }
        else
            Toast.makeText(this, "올바른 체중값을 입력해 주세요!", Toast.LENGTH_SHORT).show();
    }
}
