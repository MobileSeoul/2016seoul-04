package com.seoulmobileplatform.waterlife;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-17
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class MyMarkerView extends MarkerView {

    private TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        // TODO skydoves - Custom MarkerView : Auto-generated method stub
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // Callbacks : Everytime the MarkerView is redrawn
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true)+"ml");
        } else {
            tvContent.setText(Utils.formatNumber(e.getVal(), 0, true)+"ml");
        }
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}