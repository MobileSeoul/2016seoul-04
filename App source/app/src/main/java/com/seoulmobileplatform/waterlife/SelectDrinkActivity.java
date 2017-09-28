package com.seoulmobileplatform.waterlife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoulmobileplatform.waterlife.Systems.ExceptionLog;
import com.seoulmobileplatform.waterlife.Systems.Systems_DB;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-16
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class SelectDrinkActivity extends AppCompatActivity {

    @Bind(R.id.selectdrink_rcyv)
    RecyclerView recyclerView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;

    // DB
    private SQLiteDatabase db;
    private Systems_DB systems_db;
    private ExceptionLog exceptionLog;
    private ArrayList<Item> items;

    private final String TAG = "SelectDrinkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_drink);
        ButterKnife.bind(this);

        // Initialize DB
        db = openOrCreateDatabase(getResources().getString(R.string.db), Context.MODE_PRIVATE, null);
        systems_db = new Systems_DB(this, db);
        exceptionLog = new ExceptionLog(this, TAG);

        // Add Items
        items = new ArrayList<>();
        items.add(new Item(R.drawable.ic_glass0, "125ml"));
        items.add(new Item(R.drawable.ic_glass01, "250ml"));
        items.add(new Item(R.drawable.ic_glass06, "330ml"));
        items.add(new Item(R.drawable.ic_glass05, "500ml"));
        items.add(new Item(R.drawable.ic_glass07, "750ml"));
        items.add(new Item(R.drawable.ic_glass04, "1000ml"));

        // Set RecyclerView
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Adapter = new MyAdpater(items, this);
        recyclerView.setAdapter(Adapter);

        // RecyclerView Item TouchListener
        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getApplicationContext(), recyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, final int position) {
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
                                        // add a record
                                        systems_db.addRecord(items.get(position).getImagetitle());
                                        // Notify Data Change
                                        ((MainActivity)MainActivity.mContext).UpdateFragments();
                                        // Show Bade
                                        ((MainActivity)MainActivity.mContext).ShowBadge(1);
                                        finish();
                                    }

                                    @Override
                                    public void onAnimationCancel(final View view) {

                                    }
                                })
                                .withLayer()
                                .start();
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }
        ));
    }


    /*===================================================
                  RecyclerView & ViewHolder
     ===================================================*/
    //region
    // RecyclerView OnClickListener
    public static class RecyclerViewOnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
        private OnItemClickListener mListener;
        private GestureDetector mGestureDetector;

        public RecyclerViewOnItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            this.mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(childView != null && mListener != null){
                        mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(child, rv.getChildAdapterPosition(child));
                return true;
            }
            return false;
        }

        public interface OnItemClickListener {
            void onItemClick(View v, int position);
            void onItemLongClick(View v, int position);
        }
    }

    // RecyclerView Adapter
    class MyAdpater extends RecyclerView.Adapter<MyAdpater.ViewHolder>
    {
        private Context context;
        private ArrayList<Item> mItems;

        public MyAdpater(ArrayList<Item> items, Context mContext)
        {
            mItems = items;
            context = mContext;
        }

        // onCreate ViewHolder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectdrink,parent,false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        // Bind ViewHolder
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.imageView.setImageResource(mItems.get(position).image);
            holder.textView.setText(mItems.get(position).imagetitle);
        }

        // getItem Count
        @Override
        public int getItemCount() {
            return mItems.size();
        }

        // ViewHolder
        public class ViewHolder  extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;

            public ViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.item_selectdrink_img);
                textView = (TextView) view.findViewById(R.id.item_selectdrink_tv);
            }
        }
    }

    // Item Object
    public class Item {
        int image;
        String imagetitle;

        public int getImage() {
            return image;
        }

        public String getImagetitle() {
            return imagetitle;
        }

        public Item(int image, String imagetitle) {
            this.image = image;
            this.imagetitle = imagetitle;
        }
    }
    //endregion

    // Button Click : Close
    @OnClick(R.id.selectdrink_btn_close)
    void Click_Close(View v)
    {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Close DB
        if(db != null)
            db.close();
    }
}
