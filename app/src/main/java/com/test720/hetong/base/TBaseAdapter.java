package com.test720.hetong.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.test720.hetong.utils.ToastUtil;

/**
 * Created by jie on 2016/8/17.
 * 测试时候的适配器    不用的时候可以删除该类
 */

public abstract class TBaseAdapter extends BaseAdapter {
    protected Context mContext;
    protected int count = 0;
    protected String name;
    protected Activity mActivity;
    public TBaseAdapter(Activity mActivity, int count) {
        this.mActivity = mActivity;
        this.count = count;
    }

    @Override
    public int getCount() {
            return count > 0 ? count : 8;
    }

    @Override
    public Object getItem(int position) {
            return count > 0 ? count : 8;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getXView(position, convertView, parent);
    }

    public abstract View getXView(int position, View convertView, ViewGroup parent);



    public void ShowToast(String msg)
    {
        ToastUtil.show(mContext,msg);
    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
    }


    /**
     * 封装跳转方式
     *
     * @param intent 传递参数
     */
    public void jumpToActivity(Intent intent) {

        mContext.startActivity(intent);
    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param bundle 传递参数
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if (bundle != null) intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


}