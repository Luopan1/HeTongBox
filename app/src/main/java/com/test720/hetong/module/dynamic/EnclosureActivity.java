package com.test720.hetong.module.dynamic;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.test720.hetong.R;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.SocketResponsePacket;
import com.xw.repo.BubbleSeekBar;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.amap.api.fence.GeoFenceClient.GEOFENCE_IN;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_OUT;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_STAYED;

public class EnclosureActivity extends BaseToolbarActivity {


    @BindView(R.id.mapView)
    MapView mMapView;
    public static final String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";
    final int REQ_GEO_FENCE = 0x13;
    final String ACTION_GEO_FENCE = "geo fence action";
    @BindView(R.id.close)
    RadioButton mClose;
    @BindView(R.id.open)
    RadioButton mOpen;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.seekBar)
    BubbleSeekBar mSeekBar;
    @BindView(R.id.controlRela)
    RelativeLayout mControlRela;
    private GeoFenceClient mGeoFenceClient;
    AMap aMap;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationListener mLocationListener;
    public AMapLocationClientOption mLocationOption = null;
    MyLocationStyle myLocationStyle;
    private LatLng centerLatLng;
    CircleOptions circleOptions;
    private DPoint mCenterPoint;
    private GeoFenceListener mFenceListenter;
    private float radious = 100f;
    private Vibrator vibrator;
    private SocketClient socketClient;
    private Subscription mSubscribe;


    @Override
    protected int getContentView() {
        return R.layout.activity_enclosure;
    }

    public String GetInetAddress(String host) {
        String IPAddress = "";
        InetAddress ReturnStr1 = null;
        try {
            ReturnStr1 = java.net.InetAddress.getByName(host);
            IPAddress = ReturnStr1.getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return IPAddress;
        }
        return IPAddress;
    }

    @Override
    protected void initData() {
        getData();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mSubscribe = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(GetInetAddress("www.cdbaiweiba.com"));
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                socketClient = new SocketClient("192.168.1.233", 8282);
                socketClient.setConnectionTimeout(1000 * 30);
                socketClient.setHeartBeatInterval(1000 * 10);
                socketClient.setRemoteNoReplyAliveTimeout(1000 * 60);
                socketClient.setCharsetName("UTF-8");
                socketClient.connect();

                socketClient.registerSocketDelegate(new SocketClient.SocketDelegate() {
                    @Override
                    public void onConnected(SocketClient client) {
                        /**绑定信息 */
                        JSONObject obj = new JSONObject();
                        obj.put("type", "bind");
                        obj.put("uid", 2);
                        Log.e("message", obj.toString());
                        socketClient.send(obj.toString());


                        /**心跳包*/
                        JSONObject object = new JSONObject();
                        object.put("type", "ping");
                        Log.e("send_socket_message==", object.toString());
                        socketClient.setHeartBeatMessage(object.toJSONString());


                    }

                    @Override
                    public void onDisconnected(SocketClient client) {
                        Log.e("socket error ==", client.getCharsetName());
                    }


                    @Override
                    public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                        String responseMsg = responsePacket.getMessage();
                        Log.e("socket message==", responseMsg);
                    }
                });

            }
        });


    }

    private void getData() {
        mSubscribe = apiService.carFencetype("110").compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    if (jsonObject.getJSONObject("data").getString("type").equals("1")) {
                        mClose.setChecked(false);
                        mOpen.setChecked(true);
                        Double lat = Double.parseDouble(jsonObject.getJSONObject("data").getString("lat"));
                        Double longx = Double.parseDouble(jsonObject.getJSONObject("data").getString("long"));
                        radious = Float.parseFloat(jsonObject.getJSONObject("data").getString("radius"));

                        //                        radious = 300; //for test
                        mSeekBar.setProgress(radious);

                        centerLatLng = new LatLng(lat, longx);
                        mCenterPoint = new DPoint();
                        mCenterPoint.setLatitude(lat);//纬度
                        mCenterPoint.setLongitude(longx);//经度

                        //添加围栏 设置围栏的大小为radius
                        mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
                        mGeoFenceClient.setGeoFenceListener(mFenceListenter);
                        mGeoFenceClient.addGeoFence(mCenterPoint, radious, "测试");
                        addCircle(centerLatLng, radious);


                        if (radious < 300)
                            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                        else if (radious >= 300 && radious < 500)
                            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                        else if (radious >= 500 && radious <= 1000)
                            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));


                    } else {
                        getCurrentLocation();
                    }
                }
            }
        });
    }


    public void addCircle(LatLng latLng, float radius) {
        Log.e("TAG++", latLng.latitude + "__" + latLng.longitude);
        aMap.clear();
        circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeWidth(4);
        circleOptions.strokeColor(getResources().getColor(R.color.blue));
        circleOptions.fillColor(Color.argb(50, 1, 1, 1));
        //清除会清楚整个地图上的东西，包括自己的定位的点，所以清除后需要添加自己刚才定位的点
        //        aMap.addMarker(new MarkerOptions().position(latLng).title("").snippet(" ").draggable(false));
        aMap.addCircle(circleOptions);
    }

    @Override
    protected void initView() {
        initToobar("电子围栏");
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(GEOFENCE_BROADCAST_ACTION);
        registerReceiver(mGeoFenceReceiver, filter);

        mGeoFenceClient = new GeoFenceClient(getApplicationContext());
        mGeoFenceClient.setActivateAction(GEOFENCE_IN | GEOFENCE_OUT | GEOFENCE_STAYED);


        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.showMyLocation(true);
        //            myLocationStyle.myLocationIcon(bitmap);
        myLocationStyle.interval(1000);
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    public void getCurrentLocation() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation loc) {
                if (loc != null && loc.getErrorCode() == 0) {
                    if (mClose.isChecked()) {
                        //设置地理围栏
                        centerLatLng = new LatLng(30.552904, 104.067894);
                        mCenterPoint = new DPoint();
                        mCenterPoint.setLatitude(30.552904);//纬度
                        mCenterPoint.setLongitude(104.067894);//经度
                        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
                        aMap.setMyLocationStyle(myLocationStyle);

                        addCircle(centerLatLng, radious);

                    }
                } else {
                    ShowToast("定位异常 请稍候再试");
                }
            }
        };
        mLocationClient = new AMapLocationClient(getApplicationContext());

        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setLocationCacheEnable(false);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        mLocationClient.setLocationOption(mLocationOption);

        mLocationClient.startLocation();


    }

    @Override
    public void setListener() {
        mSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                if (centerLatLng != null) {
                    radious = progress;
                    addCircle(centerLatLng, radious);
                }

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }
        });

        mOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSeekBar.setEnabled(false);
                    mOpen.setEnabled(false);
                    mClose.setEnabled(true);

                } else {
                    getCurrentLocation();//获取到当前的位置

                }
            }
        });

        //创建回调监听
        mFenceListenter = new GeoFenceListener() {
            @Override
            public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {

                Log.e("TAG++", "GeoFence" + list.get(0).getRadius());
                if (i == GeoFence.ADDGEOFENCE_SUCCESS) {
                    ShowToast("添加围栏成功");
                    Log.e("TAG+++", i + "添加围栏成功");
                } else {
                    ShowToast("添加围栏失败");
                    Log.e("TAG+++", i + "添加围栏失败");

                }
            }
        };


        mControlRela.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }

    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {

                if (mOpen.isChecked()) {
                    // 接收广播
                    Bundle bundle = intent.getExtras();
                    int status = bundle.getInt("event");
                    if (status == 1) {
                        Toast.makeText(EnclosureActivity.this, "进入地理围栏~", Toast.LENGTH_LONG).show();
                        Log.e("TAG++", " \"进入地理围栏~\"");
                        vibrator.vibrate(1000);
                    } else if (status == 2) {
                        // 离开围栏区域
                        Toast.makeText(EnclosureActivity.this, "离开地理围栏~", Toast.LENGTH_LONG).show();
                        Log.e("TAG++", " 离开地理围栏~");
                        vibrator.vibrate(1000);
                    }
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //会清除所有围栏
        mGeoFenceClient.removeGeoFence();
        mMapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
        try {
            if (mGeoFenceReceiver != null) {
                unregisterReceiver(mGeoFenceReceiver);
            }
        } catch (Exception e) {

        }
        if (mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
            socketClient.disableHeartBeat();
            socketClient.disconnect();
        }
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


    @OnClick({R.id.close, R.id.open})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                if (mClose.isChecked()) {
                    switcEnclouse(0);
                    aMap.clear();

                }
                break;
            case R.id.open:
                if (mOpen.isChecked()) {

                    switcEnclouse(1);

                }

                break;
        }
    }

    public void switcEnclouse(final int type) {
        mSubscription = apiService.openFence("110", mCenterPoint.getLongitude() + "", mCenterPoint.getLatitude() + "", type, radious + "").compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {

                if (jsonObject.getInteger("code") == 1) {

                    if (type == 0) {
                        mSeekBar.setProgress(100f);
                        mSeekBar.setEnabled(true);
                        mOpen.setEnabled(true);
                        mClose.setEnabled(false);
                        //会清除所有围栏
                        mGeoFenceClient.removeGeoFence();
                        mGeoFenceClient.setGeoFenceListener(null);
                        


                    } else {
                        mGeoFenceClient.setGeoFenceListener(mFenceListenter);
                        mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
                        mGeoFenceClient.addGeoFence(mCenterPoint, radious, "测试");
                    }
                }
            }


        });
    }
}
