package com.test720.hetong.utils;

/**
 * Created by jie on 2017/3/10.
 */

public class Constants {
    //请求头
    //    public static String httpHost = "http://120.26.141.238/obd/index.php/Myapi/";
    public static String httpHost = "http://192.168.1.233/obd/index.php/Myapi/";
    public static String imagePath = "http://192.168.1.233/obd/";
    public static String cash = "http://restapi.amap.com/v3/staticmap?location=116.481485,39.990464&zoom=10&size=1024*1024" +
            "&markers=mid,,A:116.481485,39.990464&key=824b8ceeace054d3e1d0a825f6e37959";

    public static String getCashImage(String lon, String lat) {
        return "http://restapi.amap.com/v3/staticmap?location=" + lon + "," + lat + "&zoom=15&size=1024*1024&scale=2" +
                "&markers=mid,,A:" + lon + "," + lat + "&key=824b8ceeace054d3e1d0a825f6e37959";
    }
}
