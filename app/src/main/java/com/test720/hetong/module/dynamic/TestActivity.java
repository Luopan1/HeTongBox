package com.test720.hetong.module.dynamic;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;

import java.util.List;

import static com.amap.api.fence.GeoFenceClient.GEOFENCE_IN;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_OUT;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_STAYED;

public class TestActivity extends AppCompatActivity implements AMapLocationListener, LocationSource {
    final String tag = TestActivity.class.getSimpleName();
    final int REQ_LOCATION = 0x12;
    final int REQ_GEO_FENCE = 0x13;
    final String ACTION_GEO_FENCE = "geo fence action";
    public static final String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";
    private GeoFenceClient mGeoFenceClient;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private IntentFilter intentFilter;
    private Vibrator vibrator;
    private LatLng centerLatLng;
    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener onLocationChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = new MapView(this);
        setContentView(mapView);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mLocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mLocationClient.setLocationOption(mLocationOption);
        //处理进出地理围栏事件
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_GEO_FENCE);
        //show my location
        mLocationClient.startLocation();
        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
    }

    //创建回调监听
    final GeoFenceListener fenceListenter = new GeoFenceListener() {

        @Override
        public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {
            Log.e("onGeo", i + "Code");
            if (i == GeoFence.ADDGEOFENCE_SUCCESS) {
                Toast.makeText(TestActivity.this, "添加围栏成功~", Toast.LENGTH_LONG).show();
                Log.e("TAG+++", "添加围栏成功");
            } else {
                Toast.makeText(TestActivity.this, "添加围栏失败~", Toast.LENGTH_LONG).show();
                Log.e("TAG+++", "添加围栏失败");

            }
        }


    };

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收广播
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                // 根据广播的event来确定是在区域内还是在区域外
                int status = bundle.getInt("event");
                String geoFenceId = bundle.getString("fenceId");
                if (status == 1) {
                    Toast.makeText(TestActivity.this, "进入地理围栏~", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(3000);
                } else if (status == 2) {
                    // 离开围栏区域
                    Toast.makeText(TestActivity.this, "离开地理围栏~", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(3000);
                }
            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(broadcastReceiver);
    }

    public void applyPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(TestActivity.this, "没有权限，无法获取位置信息~", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (loc != null && loc.getErrorCode() == 0) {
            //设置地理围栏
            if (centerLatLng == null) {
                centerLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                mGeoFenceClient = new GeoFenceClient(getApplicationContext());
                mGeoFenceClient.setActivateAction(GEOFENCE_IN | GEOFENCE_OUT | GEOFENCE_STAYED);
                mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
                mGeoFenceClient.setGeoFenceListener(fenceListenter);
                addCircle(centerLatLng, 100);
            } else {
                double latitude = loc.getLatitude();
                double longitude = loc.getLongitude();
                Log.d(tag, "当前经纬度: " + latitude + "," + longitude);
                LatLng endLatlng = new LatLng(loc.getLatitude(), loc.getLongitude());
                // 计算量坐标点距离
                double distances = AMapUtils.calculateLineDistance(centerLatLng, endLatlng);
                Toast.makeText(TestActivity.this, "当前距离中心点：" + ((int) distances), Toast.LENGTH_LONG).show();
                if (onLocationChangedListener != null) {
                    onLocationChangedListener.onLocationChanged(loc);
                }
            }
        }
    }

    public void addCircle(LatLng latLng, int radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeWidth(4);
        circleOptions.strokeColor(Color.RED);
        circleOptions.fillColor(Color.BLUE);
        aMap.addCircle(circleOptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }

    }
}

