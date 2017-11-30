package com.test720.hetong.network;


import com.test720.hetong.App;
import com.test720.hetong.utils.Constants;
import com.test720.hetong.utils.L;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;



/**
 * Created by lichuanbei on 2016/10/17.
 *
 * 网络请求框架封装--封装Https,并设置网络请求时公共参数
 */

public class HttpsRequest {
    private volatile static HttpsRequest instance;

    /**
     * 该类单例模式，获取类实例
     *
     * @return
     */
    private static HttpsRequest getInstance() {
        if (null == instance) {
            synchronized (HttpsRequest.class) {
                if (null == instance) {
                    instance = new HttpsRequest();
                }
            }
        }
        return instance;
    }

    private HttpsRequest() {
    }

    /**
     * 封装后的Retrofit,每次请求时调用HttpsRequest.provideClientApi()即可获取Retrofit实例
     *
     * @return
     */
    public static ApiService provideClientApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.httpHost)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpsRequest.getInstance().getOkHttpClinet())
                .build();
        ApiService ApiService = retrofit.create(ApiService.class);
        return ApiService;
    }





    /**
     * okhttpClinet通用设置为https访问-使用拦截器设置请求头
     *
     * @return
     */
    protected OkHttpClient getOkHttpClinet() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.cookieJar(new CookieManger(App.getInstance()));
//        //设置https
//        SSLSocketFactory sslSocketFactory = new SslContextFactory().getSslSocket().getSocketFactory();
//        okHttpClient.sslSocketFactory(sslSocketFactory);
        //设置超时
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);//
        okHttpClient.readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS);
        //错误重连
        okHttpClient.retryOnConnectionFailure(true);
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .build();
                return chain.proceed(request);
            }
        });
        //设置是否打印Log//打印请求log日志//根据是否debug模式决定
        if (L.DEBUG_SYSOUT) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(loggingInterceptor);
        }
        okHttpClient.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return okHttpClient.build();
    }



}