package com.test720.hetong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by LuoPan on 2017/10/19 15:33.
 */

public class HttpUtils {
    public Observable<String> getData(final String url, final HttpParams params, int what) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        OkGo.<String>post(url).params(params).execute(new StringCallback() {

                            @Override
                            public void onStart(Request<String, ? extends Request> request) {

                            }

                            @Override
                            public void onSuccess(Response<String> response) {
                                JSONObject jsonObject = JSON.parseObject(response.body());
                                subscriber.onNext(jsonObject.toString());
                                subscriber.onCompleted();


                            }

                            @Override
                            public void onError(Response<String> response) {
                                subscriber.onError(response.getException());
                            }

                        });
                    } catch (Exception e) {
                    }

                }
            }
        });

    }


}
