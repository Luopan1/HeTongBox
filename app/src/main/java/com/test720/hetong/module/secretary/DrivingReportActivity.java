package com.test720.hetong.module.secretary;

import android.widget.ListView;

import com.test720.hetong.R;
import com.test720.hetong.adapter.DrivingReportAdapter;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.utils.Utils;

import butterknife.BindView;

public class DrivingReportActivity extends BaseToolbarActivity {


    @BindView(R.id.lv_view)
    ListView lvView;
    private DrivingReportAdapter drivingReportAdapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_driving_report;
    }

    @Override
    protected void initData() {
        initToobar("每日行车报告");
        drivingReportAdapter = new DrivingReportAdapter(Utils.getListString(5),mActivity);
        lvView.setAdapter(drivingReportAdapter);
    }

}
