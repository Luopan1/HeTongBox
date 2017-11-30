package com.test720.hetong.module.dynamic;

import android.view.View;
import android.widget.RelativeLayout;

import com.test720.hetong.R;
import com.test720.hetong.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DynamicMonitorActivity extends BaseToolbarActivity {

    @BindView(R.id.dingwei)
    RelativeLayout mDingwei;
    @BindView(R.id.guji)
    RelativeLayout mGuji;
    @BindView(R.id.weilan)
    RelativeLayout mWeilan;

    @Override
    protected int getContentView() {
        return R.layout.activity_dynamic_monitor;
    }

    @Override
    protected void initData() {
        initToobar("汽车动态监控");
    }

    @OnClick({R.id.dingwei, R.id.guji, R.id.weilan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dingwei:
                jumpToActivity(CarLocationActivity.class, false);
                break;
            case R.id.guji:
                jumpToActivity(GuiJiActivty.class, false);
                break;
            case R.id.weilan:
                jumpToActivity(EnclosureActivity.class, false);
                break;
        }
    }
}
