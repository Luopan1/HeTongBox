package com.test720.hetong.module.security;

import android.widget.TextView;

import com.test720.hetong.R;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.SystemListBean;

import butterknife.BindView;

public class SystemActivity extends BaseToolbarActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private SystemListBean systemListBean;
    @Override
    protected int getContentView() {
        return R.layout.activity_body_system;
    }

    @Override
    protected void initData() {
        initToobar("系统");
        systemListBean = (SystemListBean) getIntent().getSerializableExtra("data");
        tvTitle.setText(systemListBean.getTitle());
        tvContent.setText(systemListBean.getContent());
//        if ("1".equals(type)) {
//            initToobar("车身体系统");
//
//        }
//        else if ("2".equals(type)) {
//            initToobar("底盘系统");
//            tvTitle.setText("刹车故障");
//            tvContent.setText("监测刹车系统(刹车盘、刹车片、刹车液等)，异常时发生告警，提醒用户到店检查维修");
//            tvInstructions.setText("故障代码: C0049 刹车液(子错误)\n汽车制动液又称为刹车油，是用于汽车液压制动系统中传递压力的液体。当司机踩刹车时，从脚踏板上踩下去的力量，由刹车总泵的活塞，通过刹车油传递能量到车轮各分泵，使摩擦片涨开，达到停止车辆前进的目的，当停止刹车时，返回弹簧拉回摩擦片到原来的");
//            tvInstructions.setVisibility(View.VISIBLE);
//        }
//        else if ("2".equals(type)) {
//            initToobar("动力系统");
//            tvTitle.setText("故障");
//            tvContent.setText("监测刹车系统(刹车盘、刹车片、刹车液等)，异常时发生告警，提醒用户到店检查维修");
//        }






    }
}
