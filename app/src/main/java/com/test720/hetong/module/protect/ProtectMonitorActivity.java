package com.test720.hetong.module.protect;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test720.hetong.R;
import com.test720.hetong.adapter.BaseRecyclerAdapter;
import com.test720.hetong.adapter.BaseRecyclerHolder;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.ProtectBean;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProtectMonitorActivity extends BaseToolbarActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mRefreshlayout;
    private List<ProtectBean> mProtectLists = new ArrayList<>();
    BaseRecyclerAdapter<ProtectBean> mAdapter;
    int cashNum;
    int weizhangNum;
    public static int protectNum = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_protect_monitor;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
        mSubscription = apiService.collisionCount("110").compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    mProtectLists.clear();
                    cashNum = Integer.parseInt(jsonObject.getJSONArray("data").getString(0));
                    weizhangNum = Integer.parseInt(jsonObject.getJSONArray("data").getString(1));
                    mProtectLists.add(new ProtectBean(R.mipmap.ic_launcher, "汽车碰撞警告", cashNum));
                    mProtectLists.add(new ProtectBean(R.mipmap.ic_launcher, "交通违章提醒", weizhangNum));
                    serAdapter();
                }
            }

            @Override
            public void onCompleted() {
                mRefreshlayout.finishRefreshing();
            }

            @Override
            public void onStart() {

            }
        });

    }

    @Override
    public void setListener() {
        mRefreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    private void serAdapter() {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<ProtectBean>(this, mProtectLists, R.layout.item_main_kind_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, ProtectBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.title, item.getTitle());
                    holder.setImageResource(R.id.titleImage, item.getIcon());
                    if (item.getNotifyNum() == 0) {
                        holder.getView(R.id.notifyNumRela).setVisibility(View.GONE);
                    } else {
                        holder.getView(R.id.notifyNumRela).setVisibility(View.VISIBLE);
                        holder.setText(R.id.notifyNum, item.getNotifyNum() + "");
                    }
                }
            };
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    switch (position) {
                        case 0://驻车碰撞告警
                            jumpToActivity(CollisionWarningActivity.class, false);
                            protectNum = mProtectLists.get(position).getNotifyNum();
                            mProtectLists.get(position).setNotifyNum(0);
                            mAdapter.notifyDataSetChanged();
                            break;
                        case 1://交通违章提醒
                            jumpToActivity(IllegalToRemindActivity.class, false);
                            protectNum = mProtectLists.get(position).getNotifyNum();
                            mProtectLists.get(position).setNotifyNum(0);
                            mAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void initView() {
        initToobar("汽车保护监控");
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
    protected void onResume() {
        super.onResume();
        mRefreshlayout.startRefresh();
    }
}
