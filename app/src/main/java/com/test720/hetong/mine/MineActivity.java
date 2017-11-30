package com.test720.hetong.mine;

import android.view.View;
import android.widget.RelativeLayout;

import com.test720.hetong.R;
import com.test720.hetong.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MineActivity extends BaseToolbarActivity {

    @BindView(R.id.setting)
    RelativeLayout mSetting;
    @BindView(R.id.bindCage)
    RelativeLayout mBindCage;
    @BindView(R.id.unBindCage)
    RelativeLayout mUnBindCage;
    @BindView(R.id.outRemind)
    RelativeLayout mOutRemind;
    @BindView(R.id.addKm)
    RelativeLayout mAddKm;

    @Override
    protected int getContentView() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initToobar("和车宝");
    }

    @OnClick({R.id.setting, R.id.bindCage, R.id.unBindCage, R.id.outRemind, R.id.addKm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                break;
            case R.id.bindCage:
                jumpToActivity(BindCageActivity.class, false);
                break;
            case R.id.unBindCage:
                break;
            case R.id.outRemind:
                break;
            case R.id.addKm:
                break;
        }
    }
}
