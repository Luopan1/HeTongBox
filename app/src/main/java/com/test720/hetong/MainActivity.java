package com.test720.hetong;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test720.hetong.adapter.BaseRecyclerAdapter;
import com.test720.hetong.adapter.BaseRecyclerHolder;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.Classification;
import com.test720.hetong.mine.MineActivity;
import com.test720.hetong.module.dynamic.DynamicMonitorActivity;
import com.test720.hetong.module.protect.ProtectMonitorActivity;
import com.test720.hetong.module.secretary.TravelSecretaryActivity;
import com.test720.hetong.module.security.SecurityMonitorActivity;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;
import com.test720.hetong.notify.MyNotifyActivity;
import com.test720.hetong.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseToolbarActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    List<Classification.DataBean.ListBean> mClassificationList = new ArrayList<>();
    BaseRecyclerAdapter<Classification.DataBean.ListBean> mainAdapter;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mRefreshlayout;
    private int index = 0;
    private String count = "0";
    private String protectNum = "0";

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
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

    public void getData() {
        mSubscription = apiService.keeperList("110").compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    mClassificationList.clear();
                    Gson gson = new Gson();
                    Classification classification = gson.fromJson(jsonObject.toString(), Classification.class);
                    mClassificationList.addAll(classification.getData().getList());
                    setAdapter();
                } else {
                    ShowToast(jsonObject.getString("msg"));
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

    private void setAdapter() {
        if (mainAdapter == null) {
            mainAdapter = new BaseRecyclerAdapter<Classification.DataBean.ListBean>(this, mClassificationList, R.layout.item_main_kind_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, Classification.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.title, item.getName());
                    holder.setImageByUrl(R.id.titleImage, Constants.imagePath + item.getLogosrc());
                    if (Double.parseDouble(item.getCount()) == 0) {
                        holder.getView(R.id.notifyNumRela).setVisibility(View.GONE);
                    } else {
                        holder.getView(R.id.notifyNumRela).setVisibility(View.VISIBLE);
                        holder.setText(R.id.notifyNum, item.getCount());
                    }
                }
            };
            mRecyclerView.setAdapter(mainAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mainAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    switch (position) {
                        case 0://汽车系统安全监控
                            index = 0;
                            count = mClassificationList.get(position).getCount();
                            jumpToActivity(SecurityMonitorActivity.class, false);
                            break;
                        case 1://汽车动态监控
                            index = 1;

                            jumpToActivity(DynamicMonitorActivity.class, false);
                            mClassificationList.get(position).setCount("0");
                            mainAdapter.notifyDataSetChanged();
                            break;
                        case 2://汽车保护监控
                            index = 2;
                            protectNum = mClassificationList.get(position).getCount();
                            jumpToActivity(ProtectMonitorActivity.class, false);
                            break;
                        case 3://汽车行驶秘书
                            index = 3;
                            jumpToActivity(TravelSecretaryActivity.class, false);
                            mClassificationList.get(position).setCount("0");
                            mainAdapter.notifyDataSetChanged();
                            break;
                        case 4://通知
                            index = 4;
                            jumpToActivity(MyNotifyActivity.class, false);
                            mClassificationList.get(position).setCount("0");
                            mainAdapter.notifyDataSetChanged();
                            break;
                        case 5:
                            jumpToActivity(MineActivity.class, false);
                            mClassificationList.get(position).setCount("0");
                            mainAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            });
        } else {
            mainAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initView() {
        initToobar("汽车管家");
        setTopLeftButton();

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
