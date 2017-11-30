package com.test720.hetong.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.test720.hetong.R;
import com.test720.hetong.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LuoPan on 2017/11/23 12:51.
 */
public class TestFragment extends BaseFragment {

    private TimePickerView pvTime;
    private TimePickerView pvCustomTime;

    @Override
    protected void initData() {

    }

    @Override
    public void initView() {

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    protected int setlayoutResID() {
        return R.layout.fragment_test;
    }
}
