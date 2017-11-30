package com.test720.hetong.module.protect;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test720.hetong.R;
import com.test720.hetong.adapter.BaseRecyclerAdapter;
import com.test720.hetong.adapter.BaseRecyclerHolder;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.TrafficBean;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IllegalToRemindActivity extends BaseToolbarActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mRefreshlayout;
    private int page = 1;
    List<TrafficBean.DataBean.ListBean> trafficLits = new ArrayList<>();
    BaseRecyclerAdapter<TrafficBean.DataBean.ListBean> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_illegal_to_remind;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void setListener() {
        mRefreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
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
        mSubscription = apiService.trafficViolation("110", page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    Gson gson = new Gson();
                    TrafficBean trafficBean = gson.fromJson(jsonObject.toString(), TrafficBean.class);
                    if (trafficBean.getData().getList().size() < 1)
                        mRefreshlayout.setEnableLoadmore(false);
                    else
                        mRefreshlayout.setEnableLoadmore(true);
                    if (page == 1)
                        trafficLits.clear();

                    trafficLits.addAll(trafficBean.getData().getList());
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

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<TrafficBean.DataBean.ListBean>(this, trafficLits, R.layout.item_weizhang_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, TrafficBean.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setImageResource(R.id.icon, R.mipmap.ic_launcher);
                    holder.setText(R.id.time, item.getTime());
                }
            };
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    //停止刷新
    private void onStopLoad() {
        if (page == 1) {
            mRefreshlayout.finishRefreshing();
        } else {
            mRefreshlayout.finishLoadmore();
        }
    }

    @Override
    protected void initView() {
        initToobar("交通违章提醒");
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
        mRefreshlayout.setHeaderView(headerView);
        mRefreshlayout.setEnableLoadmore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshlayout.startRefresh();
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshlayout.finishRefreshing();
            }
        }, 2000);
    }
}
