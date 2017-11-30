package com.test720.hetong.notify;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.test720.hetong.R;
import com.test720.hetong.adapter.NotifyAdapter;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.notify;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;
import com.test720.hetong.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyNotifyActivity extends BaseToolbarActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mRefreshLayout;
    private List<notify.DataBean.ListBean> mNotifyList = new ArrayList<>();
    private NotifyAdapter mAdapter;
    private int page = 1;


    @Override
    protected int getContentView() {
        return R.layout.activity_my_notify;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void setListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });
    }

    public void getData() {
        mSubscription = apiService.carMeunList("110", page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    Gson gson = new Gson();
                    notify notify = gson.fromJson(jsonObject.toString(), notify.class);
                    if (notify.getData().getList().size() < 1)
                        mRefreshLayout.setEnableLoadmore(false);
                    else
                        mRefreshLayout.setEnableLoadmore(true);
                    if (page == 1)
                        mNotifyList.clear();

                    mNotifyList.addAll(notify.getData().getList());
                    setAdapter();
                }
            }

            @Override
            public void onStart() {

            }

            public void onCompleted() {
                super.onCompleted();
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onStopLoad();
                page = page == 1 ? 1 : page - 1;
            }
        });

    }

    //停止刷新
    private void onStopLoad() {
        if (page == 1) {
            mRefreshLayout.finishRefreshing();
        } else {
            mRefreshLayout.finishLoadmore();
        }
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new NotifyAdapter(mNotifyList, this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dp2px(this, 15), DensityUtil.dp2px(this, 15), DensityUtil.dp2px(this, 15)));
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new NotifyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    if (mNotifyList.get(position).getMeun_type().equals("2")) {
                        Bundle bundle = new Bundle();
                        if (mNotifyList.get(position).getCollision_type().equals("0"))
                            bundle.putString("title", "驻车碰撞");
                        else
                            bundle.putString("title", "行车碰撞");
                        bundle.putString("lat", mNotifyList.get(position).getLat());
                        bundle.putString("long", mNotifyList.get(position).getLongX());
                        jumpToActivity(CarshLocationActivity.class, bundle, false);
                    }
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initView() {
        initToobar("通知");
        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.setEnableLoadmore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.startRefresh();
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefreshing();
            }
        }, 2000);
    }
}
