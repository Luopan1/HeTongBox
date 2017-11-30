package com.test720.hetong.module.dynamic;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.test720.hetong.R;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.CarRoate;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;

public class GuiJiActivty extends BaseToolbarActivity {


    @BindView(R.id.mapView)
    MapView mMapView;
    AMap aMap;
    @BindView(R.id.calendarView)
    HorizontalCalendarView mCalendarView;
    private HorizontalCalendar mHorizontalCalendar;

    @Override
    protected int getContentView() {
        return R.layout.activity_gui_ji_activty;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void init(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initView() {
        initToobar("行车轨迹");
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -5);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 5);


        mHorizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(3)   // Number of Dates cells shown on screen (Recommended 5)
                .dayNameFormat("EEE")      // WeekDay text format
                .dayNumberFormat("dd")    // Date format
                .monthFormat("MMM")      // Month format
                .yearFormat("yyyy年")
                .showDayName(true)      // Show or Hide dayName text
                .showMonthName(true)      // Show or Hide month text
                .textColor(Color.LTGRAY, Color.WHITE)    // Text color for none selected Dates, Text color for selected Date.
                .selectorColor(Color.RED)// Color of the selection indicator bar (default to colorAccent).
                .build();
    }

    @Override
    public void setListener() {
        mHorizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                Log.e("TAG+++", formateDate(date));
                aMap.clear();
                mSubscription = apiService.carRoute("110", formateDate(date)).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
                    @Override
                    public void _onNext(JSONObject jsonObject) {
                        if (jsonObject.getInteger("code") == 1) {
                            List<LatLng> latLngs = new ArrayList<LatLng>();
                            Gson gson = new Gson();
                            CarRoate carRoate = gson.fromJson(jsonObject.toString(), CarRoate.class);
                            for (int i = 0; i < carRoate.getData().getList().size(); i++) {
                                latLngs.add(new LatLng(Double.parseDouble(carRoate.getData().getList().get(i).getLat()), Double.parseDouble(carRoate.getData().getList().get(i).getLongX())));
                            }
                            aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
                            MarkerOptions markerOption = new MarkerOptions();
                            markerOption.draggable(false);//设置Marker可拖动
                            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.mipmap.dingwei)));
                            markerOption.setFlat(false);//设置marker平贴地图效果
                            markerOption.position(latLngs.get(0));
                            aMap.addMarker(markerOption);

                            markerOption.position(latLngs.get(latLngs.size() - 1));
                            aMap.addMarker(markerOption);
                            LatLng lat = new LatLng(Double.parseDouble(carRoate.getData().getList().get(0).getLat()), Double.parseDouble(carRoate.getData().getList().get(0).getLongX()));
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat, 10));

                        }
                    }
                });
            }

        });


    }

    public String formateDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
