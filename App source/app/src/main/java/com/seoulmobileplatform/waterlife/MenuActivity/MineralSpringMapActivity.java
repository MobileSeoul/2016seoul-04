package com.seoulmobileplatform.waterlife.MenuActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.seoulmobileplatform.waterlife.R;
import com.seoulmobileplatform.waterlife.Systems.ExceptionLog;
import com.seoulmobileplatform.waterlife.Systems.NHNMapSystems.NMapPOIflagType;
import com.seoulmobileplatform.waterlife.Systems.NHNMapSystems.NMapViewerResourceProvider;

import butterknife.ButterKnife;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-24
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class MineralSpringMapActivity extends NMapActivity implements NMapView.OnMapViewTouchEventListener, NMapView.OnMapStateChangeListener {

    // Systems
    String TAG = "MineralSpringMapActivity";
    ExceptionLog exceptionLog;

    // Naver MapView
    double[] pos = new double[2];
    public static final String API_KEY = "[#Your API-Key#]";
    private NMapView mMapView = null;
    private NMapController mMapController = null;
    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    NMapOverlayManager mOverlayManager;

    private LinearLayout MapContainer;

    // Error Code
    final int Error_getPosition = 0xffffff0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        MapContainer = (LinearLayout)findViewById(R.id.mineralspringmap_mapview);

        // ExceptionLog
        exceptionLog = new ExceptionLog(this, TAG);

        // Get Intent Data
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", Error_getPosition);

        // Error : get Position
        if(position == Error_getPosition) {
            Toast.makeText(this, "지도 데이터를 초기화 하는데 실패 했습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(position  == 0){
            pos[0] = 37.6042271;
            pos[1] = 127.1105722;
        }
        else if(position == 1){
            pos[0] = 37.6057516;
            pos[1] = 126.9391722;
        }
        else if(position == 2){
            pos[0] = 37.4141649;
            pos[1] = 127.0328239;
        }
        else if(position == 3){
            pos[0] = 37.4429377;
            pos[1] = 126.9522476;
        }
        else if(position == 4){
            pos[0] = 37.4447214;
            pos[1] = 127.079023;
        }

        // Initialize Naver MapView
        InitializeNaverMapView();
    }

    // Initialize Naver MapView
    private void InitializeNaverMapView()
    {
        // Inti MapView
        mMapView = new NMapView(this);
        mMapView.setApiKey(API_KEY);
        mMapView.setBuiltInZoomControls(true, null);
        mMapView.setClickable(true);
        setContentView(mMapView);
        mMapController = mMapView.getMapController();
        mMapController.setPanEnabled(true);
        mMapController.setZoomEnabled(true);
        mMapController.setZoomLevel(14);

        // Overlay Manager
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        // Inti MarkerData
        int markerId = NMapPOIflagType.PIN;
        NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
        poiData.beginPOIdata(1);
        poiData.addPOIitem(pos[1], pos[0], "", markerId, 0);
        poiData.endPOIdata();

        // Set Overlay Maker
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);poiDataOverlay.showAllPOIdata(0);
    }

    @Override
    public void onMapInitHandler(NMapView mapView, NMapError errorInfo){
        if (errorInfo == null){
            mMapController.setMapCenter(new NGeoPoint(pos[1], pos[0]));
        }
        else{
            exceptionLog.Exception_NaverMapView("NaverMapView SetMapFocus Error");
        }
    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    @Override
    public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onLongPressCanceled(NMapView nMapView) {

    }

    @Override
    public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

    }

    @Override
    public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

    }
}
