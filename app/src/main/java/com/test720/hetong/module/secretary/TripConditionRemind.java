package com.test720.hetong.module.secretary;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.WheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.test720.hetong.R;
import com.test720.hetong.base.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TripConditionRemind extends BaseToolbarActivity {


    @BindView(R.id.hour)
    WheelView mHour;
    @BindView(R.id.Minute)
    WheelView mMinute;
    @BindView(R.id.mapView)
    RelativeLayout mMapView;
    @BindView(R.id.startLocation)
    EditText mStartLocation;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.endLocation)
    EditText mEndLocation;
    @BindView(R.id.roadName)
    EditText mRoadName;
    @BindView(R.id.close)
    RadioButton mClose;
    @BindView(R.id.open)
    RadioButton mOpen;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    private List<String> mHourLists;
    private List<String> mMinuteLists;
    int click_y;
    int move_y;

    @Override
    protected int getContentView() {
        return R.layout.activity_trip_condition_remind;
    }

    @Override
    protected void initData() {
        mHourLists = new ArrayList<>();

        mMinuteLists = new ArrayList<>();

        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                mHourLists.add("0" + i);
            } else {
                mHourLists.add(i + "");
            }

        }


        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                mMinuteLists.add("0" + i);
            } else {
                mMinuteLists.add(i + "");
            }
        }


        mHour.setCyclic(true);
        mHour.setDividerColor(getResources().getColor(R.color.bg));
        mHour.setTextColorCenter(getResources().getColor(R.color.white));
        mHour.setTextColorOut(Color.parseColor("#7A7490"));
        mHour.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return mHourLists.size();
            }

            @Override
            public Object getItem(int index) {
                return mHourLists.get(index);
            }

            @Override
            public int indexOf(Object o) {
                return mHourLists.indexOf(o);
            }
        });


        mMinute.setCyclic(true);
        mMinute.setDividerColor(getResources().getColor(R.color.bg));
        mMinute.setTextColorCenter(getResources().getColor(R.color.white));
        mMinute.setTextColorOut(Color.parseColor("#7A7490"));
        mMinute.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return mMinuteLists.size();
            }

            @Override
            public Object getItem(int index) {
                return mMinuteLists.get(index);
            }

            @Override
            public int indexOf(Object o) {
                return mMinuteLists.indexOf(o);
            }
        });


    }

    @Override
    protected void initView() {
        initToobar("出行路况提醒");
    }

    @OnClick({R.id.mapView, R.id.close, R.id.open})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mapView:

                break;
            case R.id.close:

                break;
            case R.id.open:
                if (mStartLocation.getText().toString().trim().isEmpty()||mEndLocation.getText().toString().trim().isEmpty()){
                    ShowToast("终点起点不能为空");
                }else if (mRoadName.getText().toString().trim().isEmpty()){
                    ShowToast("路线名称不能为空");
                }else {

                }

                break;
        }
    }

    @Override
    public void setListener() {

    }
}
