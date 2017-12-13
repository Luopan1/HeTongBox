package com.test720.hetong.module.security;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test720.hetong.R;
import com.test720.hetong.adapter.BaseRecyclerAdapter;
import com.test720.hetong.adapter.BaseRecyclerHolder;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.SystemType;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;
import com.test720.hetong.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SecurityMonitorActivity extends BaseToolbarActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    List<SystemType.DataBean.ListBean> mDatalists = new ArrayList<>();
    BaseRecyclerAdapter<SystemType.DataBean.ListBean> mAdapter;
    public static int notifyNum = 0;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mRefreshlayout;
    //    private int allNotifyCount;

    @Override
    protected int getContentView() {
        return R.layout.activity_security_monitor;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
        mSubscription = apiService.systemTypeList("1").compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    mDatalists.clear();
                    Gson gson = new Gson();
                    SystemType data = gson.fromJson(jsonObject.toString(), SystemType.class);
                    mDatalists.addAll(data.getData().getList());
                    setAdapter();
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                mRefreshlayout.finishRefreshing();
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

    private void setAdapter() {

        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<SystemType.DataBean.ListBean>(this, mDatalists, R.layout.item_main_kind_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, SystemType.DataBean.ListBean item, int position, boolean isScrolling) {


                    holder.setText(R.id.title, item.getName());
                    holder.setImageByUrl(R.id.titleImage, Constants.imagePath + item.getLogosrc());
                    if (item.getCount() == 0) {
                        holder.getView(R.id.notifyNumRela).setVisibility(View.GONE);
                    } else {
                        holder.getView(R.id.notifyNumRela).setVisibility(View.VISIBLE);
                        holder.setText(R.id.notifyNum, item.getCount() + "");
                    }
                }
            };

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    switch (position) {
                        case 0:
                            notifyNum = mDatalists.get(position).getCount();
                            mDatalists.get(position).setCount(0);
                            mAdapter.notifyDataSetChanged();
                            Bundle bundle = new Bundle();
                            bundle.putString("type", "1");
                            bundle.putString("id", mDatalists.get(position).getTid());
                            bundle.putString("title", mDatalists.get(position).getName());
                            jumpToActivity(SystemListActivity.class, bundle, false);
                            break;
                        case 1:
                            notifyNum = mDatalists.get(position).getCount();
                            mDatalists.get(position).setCount(0);
                            mAdapter.notifyDataSetChanged();
                            bundle = new Bundle();
                            bundle.putString("type", "2");
                            bundle.putString("id", mDatalists.get(position).getTid());
                            bundle.putString("title", mDatalists.get(position).getName());
                            jumpToActivity(SystemListActivity.class, bundle, false);
                            break;
                        case 2:
                            notifyNum = mDatalists.get(position).getCount();
                            mDatalists.get(position).setCount(0);
                            mAdapter.notifyDataSetChanged();
                            bundle = new Bundle();
                            bundle.putString("type", "3");
                            bundle.putString("id", mDatalists.get(position).getTid());
                            bundle.putString("title", mDatalists.get(position).getName());
                            jumpToActivity(SystemListActivity.class, bundle, false);
                            break;
                        case 3:
                            notifyNum = mDatalists.get(position).getCount();
                            mDatalists.get(position).setCount(0);
                            mAdapter.notifyDataSetChanged();
                            bundle = new Bundle();
                            bundle.putString("type", "4");
                            bundle.putString("id", mDatalists.get(position).getTid());
                            bundle.putString("title", mDatalists.get(position).getName());
                            jumpToActivity(SystemListActivity.class, bundle, false);
                            break;
                        case 4:

                            break;
                        default:
                            Log.e("Error___SecurityMonitor", "out of index");
                    }
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void initView() {
        initToobar("汽车系统安全监控");
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
