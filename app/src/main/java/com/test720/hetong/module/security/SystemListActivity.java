package com.test720.hetong.module.security;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.test720.hetong.R;
import com.test720.hetong.adapter.BaseRecyclerAdapter;
import com.test720.hetong.adapter.BaseRecyclerHolder;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.SystemError;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;
import com.test720.hetong.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemListActivity extends BaseToolbarActivity {


    @BindView(R.id.errorImage)
    ImageView mErrorImage;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private String type = "";
    private String id = "";
    private List<SystemError.DataBean.ListBean> systemListBeanList = new ArrayList<>();
    BaseRecyclerAdapter<SystemError.DataBean.ListBean> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_system_list;
    }

    @Override
    protected void initData() {
        mSubscription = apiService.faultDetail("1", id).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    Gson gson = new Gson();
                    SystemError systemError = gson.fromJson(jsonObject.toString(), SystemError.class);
                    systemListBeanList.addAll(systemError.getData().getList());
                    Glide.with(SystemListActivity.this).load(Constants.imagePath + systemError.getData().getImgsrc()).asBitmap().into(mErrorImage);
                    mTvTitle.setText(systemError.getData().getName());
                    mTvContent.setText(systemError.getData().getDescribe());
                    setAdapter();
                }
            }
        });

    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<SystemError.DataBean.ListBean>(this, systemListBeanList, R.layout.item_systemerror_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, SystemError.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.errorCode, "故障代码：" + item.getGzm_data());
                    holder.setText(R.id.errorDescribe, item.getGzm_describe());
                    holder.setText(R.id.errorDetail, item.getGzm_detail());
                }
            };
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initView() {
        initToobar(getIntent().getStringExtra("title"));
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
    }

}
