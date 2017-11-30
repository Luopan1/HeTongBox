package com.test720.hetong.network;


import com.alibaba.fastjson.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jie on 2016/12/8.
 */

public interface ApiService {
    /**
     * 首页
     *
     * @param carsn 汽车设备编号
     */
    @POST("OBD/keeperList")
    @FormUrlEncoded
    Observable<JSONObject> keeperList(@Field("carsn") String carsn);

    /**
     * 通知列表
     *
     * @param carsn 汽车设备编号
     * @param page  分页
     */
    @POST("OBD/carMeunList")
    @FormUrlEncoded
    Observable<JSONObject> carMeunList(@Field("carsn") String carsn, @Field("page") int page);

    /**
     * 安全监控
     *
     * @param carsn 汽车设备编号
     */
    @POST("OBD/systemTypeList")
    @FormUrlEncoded
    Observable<JSONObject> systemTypeList(@Field("carsn") String carsn);

    /**
     * 安全监控
     *
     * @param carsn 汽车设备编号
     * @param tid   故障类型ID
     */
    @POST("OBD/faultDetail")
    @FormUrlEncoded
    Observable<JSONObject> faultDetail(@Field("carsn") String carsn, @Field("tid") String tid);

    /**
     * 汽车定位
     *
     * @param carsn 汽车设备编号
     */
    @POST("OBD/carLocation")
    @FormUrlEncoded
    Observable<JSONObject> carLocation(@Field("carsn") String carsn);

    /**
     * 汽车行驶轨迹
     *
     * @param carsn 汽车设备编号
     * @param time  行车时间’2017-11-21’
     */
    @POST("OBD/carRoute")
    @FormUrlEncoded
    Observable<JSONObject> carRoute(@Field("carsn") String carsn, @Field("time") String time);

    /**
     * 保护监控首页
     *
     * @param carsn 汽车设备编号
     */
    @POST("OBD/collisionCount")
    @FormUrlEncoded
    Observable<JSONObject> collisionCount(@Field("carsn") String carsn);

    /**
     * 汽车碰撞警告列表
     *
     * @param carsn 汽车设备编号
     * @param page  页数
     */
    @POST("OBD/collisionList")
    @FormUrlEncoded
    Observable<JSONObject> collisionList(@Field("carsn") String carsn, @Field("page") int page);

    /**
     * 汽车交通违章列表
     *
     * @param carsn 汽车设备编号
     * @param page  页数
     */
    @POST("OBD/trafficViolation")
    @FormUrlEncoded
    Observable<JSONObject> trafficViolation(@Field("carsn") String carsn, @Field("page") int page);

    /**
     * 汽车电子围栏状态
     *
     * @param carsn 汽车设备编号
     */
    @POST("OBD/carFencetype")
    @FormUrlEncoded
    Observable<JSONObject> carFencetype(@Field("carsn") String carsn);

    /**
     * 汽车电子围栏设置
     *
     * @param carsn  汽车设备编号
     * @param
     * @param lat    纬度
     * @param type   1开启0关闭
     * @param radius 半径
     */
    @POST("OBD/openFence")
    @FormUrlEncoded
    Observable<JSONObject> openFence(@Field("carsn") String carsn, @Field("long") String longx, @Field("lat") String lat, @Field("type") int type, @Field("radius") String radius);


}
