package com.test720.hetong.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.test720.hetong.utils.ToastUtil;

import java.util.List;

/**
 * Created by jie on 2016/8/17.
 */

public abstract class MBaseAdapter<T> extends BaseAdapter {
    protected List<T> list;
    protected Activity mActivity;
    protected LayoutInflater inflater;
    protected int index;
    public MBaseAdapter(List<T> list, Activity mActivity) {
        this.list = list;
        this.mActivity = mActivity;
        inflater = LayoutInflater.from(mActivity);
    }

    public MBaseAdapter(List<T> list, Activity mActivity,int index) {
        this.list = list;
        this.mActivity = mActivity;
        this.index = index;
        inflater = LayoutInflater.from(mActivity);
    }



    @Override
    public int getCount() {
        if (list != null && list.size() > 0)
            return list.size();
        else
            return 0;
    }

    @Override
    public T getItem(int position) {
        if (list != null && list.size() > 0)
            return list.get(position);
        return null;
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
        ToastUtil.show(mActivity,msg);
    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        mActivity.startActivity(intent);
    }


    /**
     * 封装跳转方式
     *
     * @param intent 传递参数
     */
    public void jumpToActivity(Intent intent) {
        mActivity.startActivity(intent);
    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param bundle 传递参数
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (bundle != null) intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }


}