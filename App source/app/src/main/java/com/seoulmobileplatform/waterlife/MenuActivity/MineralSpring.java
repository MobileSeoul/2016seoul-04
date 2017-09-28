package com.seoulmobileplatform.waterlife.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.seoulmobileplatform.waterlife.R;

import java.util.ArrayList;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-23
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class MineralSpring extends AppCompatActivity {

    // ListView
    ArrayList<Listviewitem> data;
    ListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mineral_spring);

        // Initialize
        Initialize();
    }

    /*===================================================
                      Initialize
    ===================================================*/
    private void Initialize() {
        // TODO skydoves - MineralSprings List Init : Auto-generated method stub
        // ListView
        ListView listView=(ListView)findViewById(R.id.mineralspring_listview);
        data=new ArrayList();

        // Set Adapter and Click Listner //
        adapter=new ListviewAdapter(this, R.layout.item_mineralspring, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListViewItemClickListener());

        // add ListItems
        Listviewitem listviewitem0 = new Listviewitem(0, R.drawable.mw_00, "#나들이 약수터", "서울 중랑구의 나들이공원에 위치하며 \n올해의 건강약수터 1위로 선정되었습니다.");
        data.add(listviewitem0);
        Listviewitem listviewitem1 = new Listviewitem(0, R.drawable.mw_01, "#거북 약수터", "서울 은평구에 위치하며 \n올해의 맛좋은약수터 1위로 선정되었습니다.");
        data.add(listviewitem1);
        Listviewitem listviewitem2 = new Listviewitem(0, R.drawable.mw_03, "#산토끼옹달샘", "서울 서초구의 청계산에 위치하며 \n올해의 맛좋은약수터 2위로 선정되었습니다.");
        data.add(listviewitem2);
        Listviewitem listviewitem3 = new Listviewitem(0, R.drawable.mw_02, "#호천 약수터", "서울 관악구의 관악산에 위치하며 \n올해의 건강약수터 3위로 선정되었습니다.");
        data.add(listviewitem3);
        Listviewitem listviewitem4 = new Listviewitem(0, R.drawable.mw_04, "#율암 약수터", "서울 강남구의 인릉산에 위치하며 \n올해의 맛좋은약수터 3위로 선정되었습니다.");
        data.add(listviewitem4);


    }

    /*===================================================
                       ListView Item
    ===================================================*/
    //region
    private class Listviewitem {
        private int index;
        private int image;
        private String title;
        private String content;

        public int getIndex(){return index;}
        public int getImage(){return image;}
        public String getTitle(){return title;}
        public String getContent(){return content;}

        public Listviewitem(int index, int image, String title, String content){
            this.index = index;
            this.image = image;
            this.title=title;
            this.content = content;
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
        public String getItem(int position){return data.get(position).getTitle();}

        @Override
        public long getItemId(int position){return position;}

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){

            if(convertView==null)
                convertView=inflater.inflate(layout,parent,false);

            Listviewitem listviewitem=data.get(position);

            // Set item Views //
            // ImageView : Main ImageView
            ImageView imv_main = (ImageView)convertView.findViewById(R.id.item_mineralspring_img);
            Bitmap bmp_main = BitmapFactory.decodeResource(getResources(), data.get(position).getImage());
            imv_main.setImageBitmap(bmp_main);

            // TextView : Title
            TextView tv_item_title = (TextView)convertView.findViewById(R.id.item_mineralspring_tv_title);
            tv_item_title.setText(listviewitem.getTitle());

            // TextView : Content
            TextView tv_item_content = (TextView)convertView.findViewById(R.id.item_mineralspring_tv_content);
            tv_item_content.setText(listviewitem.getContent());

            return convertView;
        }
    }

    // ListView Item Touch Event
    private class ListViewItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View v, final int pos, long id)
        {
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
                                Intent intent = new Intent(getApplicationContext(), MineralSpringMapActivity.class);
                                intent.putExtra("position", pos);
                                startActivity(intent);
                            }

                            @Override
                            public void onAnimationCancel(final View view) {
                            }
                        })
                        .withLayer()
                        .start();
            }
        }
    };
    //endregion

}
