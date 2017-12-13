package com.test720.hetong;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;


import cn.jpush.android.api.JPushInterface;

/**
 * @author LuoPan on 2017/11/30 17:51.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.e(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.e(TAG, "用户点击打开了通知");

            openNotification(context, bundle);

        } else {
            Log.e(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            Log.e(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
        Intent mIntent = new Intent(context, MainActivity.class);
        mIntent.putExtras(bundle);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }
}
/*使用自定义消息
使用自定义消息，在客户端App里一定要写代码，去接受 JPush SDK 的广播，从而取得推送下来的消息内容。具体请参考文档：接收推送消息Receiver。
 以下代码来自于推聊。*/

