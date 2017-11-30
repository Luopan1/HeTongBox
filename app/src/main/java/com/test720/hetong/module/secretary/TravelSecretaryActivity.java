package com.test720.hetong.module.secretary;

import android.view.View;
import android.widget.RelativeLayout;

import com.test720.hetong.R;
import com.test720.hetong.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TravelSecretaryActivity extends BaseToolbarActivity {

    @BindView(R.id.layout_report)
    RelativeLayout layoutReport;
    @BindView(R.id.layout_remind)
    RelativeLayout layoutRemind;

    @Override
    protected int getContentView() {
        return R.layout.activity_travel_secretary;
    }

    @Override
    protected void initData() {
        initToobar("汽车行驶秘书");
    }


    @OnClick({R.id.layout_report, R.id.layout_remind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_report://每日行车报告
                jumpToActivity(DrivingReportActivity.class, false);
                break;
            case R.id.layout_remind://出行路况提醒
                jumpToActivity(TripConditionRemind.class, false);

                break;
        }
    }
}
