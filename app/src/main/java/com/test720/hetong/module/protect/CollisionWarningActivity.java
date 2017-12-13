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
import com.test720.hetong.bean.CashBean;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;
import com.test720.hetong.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollisionWarningActivity extends BaseToolbarActivity {


    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mRefreshlayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private int page = 1;
    List<CashBean.DataBean.ListBean> mCashBeanList = new ArrayList<>();
    BaseRecyclerAdapter<CashBean.DataBean.ListBean> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_collision_warning;
    }

    public void getData() {
        mSubscription = apiService.collisionList("1", page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    Gson gson = new Gson();
                    CashBean cashBean = gson.fromJson(jsonObject.toString(), CashBean.class);
                    if (cashBean.getData().getList().size() < 1)
                        mRefreshlayout.setEnableLoadmore(false);
                    else
                        mRefreshlayout.setEnableLoadmore(true);
                    if (page == 1)
                        mCashBeanList.clear();

                    mCashBeanList.addAll(cashBean.getData().getList());
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
            mAdapter = new BaseRecyclerAdapter<CashBean.DataBean.ListBean>(this, mCashBeanList, R.layout.item_crash_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, CashBean.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setImageByUrl(R.id.mapImage, Constants.getCashImage(mCashBeanList.get(position).getLongX(), mCashBeanList.get(position).getLat()));
                    holder.setImageResource(R.id.icon, R.mipmap.ic_launcher);
                    holder.setText(R.id.time, mCashBeanList.get(position).getTime());
                    if (item.getCollision_type().equals("0")) {
                        holder.setText(R.id.cashKind, "驻车碰撞警告                                                 ");
                    } else {
                        holder.setText(R.id.cashKind, "行车碰撞警告                                                 ");
                    }
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
    protected void initData() {

    }

    @Override
    protected void initView() {
        initToobar("驻车碰撞告警");
        //设置刷新头
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
}
