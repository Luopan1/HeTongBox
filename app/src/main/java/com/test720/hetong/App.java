package com.test720.hetong;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.test720.hetong.network.AppManager;


/**
 * Created by jie on 2017/5/8.
 */

public class App extends Application{
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        instance = this;
    }
    private void init()
    {
        //监听当前Activit状态
        ActivitStats();
    }




    public static App getInstance() {
        return instance;
    }


    //监听当前Activit状态
    private void ActivitStats() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                AppManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
