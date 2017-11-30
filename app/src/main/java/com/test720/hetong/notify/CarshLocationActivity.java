package com.test720.hetong.notify;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.test720.hetong.R;
import com.test720.hetong.base.BaseToolbarActivity;

import butterknife.BindView;

public class CarshLocationActivity extends BaseToolbarActivity {


    @BindView(R.id.mapView)
    MapView mMapView;
    private AMap aMap;

    @Override
    protected int getContentView() {
        return R.layout.activity_carsh_location;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void init(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }

    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String lat = intent.getStringExtra("lat");
        String longx = intent.getStringExtra("long");
        initToobar(title);

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        Double lat_double = Double.parseDouble(lat);
        Double longx_double = Double.parseDouble(longx);

        LatLng latLng = new LatLng(lat_double, longx_double);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);

        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.dingwei)));

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        aMap.addMarker(markerOption);

    }
}
