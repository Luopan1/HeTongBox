package com.test720.hetong.module.dynamic;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.test720.hetong.R;
import com.test720.hetong.adapter.BaseRecyclerAdapter;
import com.test720.hetong.adapter.BaseRecyclerHolder;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.EncloseBean;
import com.test720.hetong.network.RxSchedulersHelper;
import com.test720.hetong.network.RxSubscriber;
import com.test720.hetong.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EnclosureMessageActivity extends BaseToolbarActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout mRefreshlayout;
    List<EncloseBean.DataBean.ListBean> mLists = new ArrayList<>();
    private int page = 1;
    BaseRecyclerAdapter<EncloseBean.DataBean.ListBean> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_enclosure_message;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
        mSubscription = apiService.carFencelist("1", page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getInteger("code") == 1) {
                    Gson gson = new Gson();
                    EncloseBean encloseBean = gson.fromJson(jsonObject.toString(), EncloseBean.class);
                    if (encloseBean.getData().getList().size() < 1)
                        mRefreshlayout.setEnableLoadmore(false);
                    else
                        mRefreshlayout.setEnableLoadmore(true);
                    if (page == 1)
                        mLists.clear();

                    mLists.addAll(encloseBean.getData().getList());
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
            mAdapter = new BaseRecyclerAdapter<EncloseBean.DataBean.ListBean>(this, mLists, R.layout.item_dzwl_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, EncloseBean.DataBean.ListBean item, int position, boolean isScrolling) {

//                    holder.setImageResource(R.mipmap.ic_launcher, R.id.icon);
                    holder.setText(R.id.time, mLists.get(position).getDay());
                    holder.setText(R.id.baojingshijian, mLists.get(position).getData());
                }
            };
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dp2px(this, 15), DensityUtil.dp2px(this, 15), DensityUtil.dp2px(this, 15)));
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
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

    @Override
    protected void initView() {
        initToobar("电子围栏");

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

    //停止刷新
    private void onStopLoad() {
        if (page == 1) {
            mRefreshlayout.finishRefreshing();
        } else {
            mRefreshlayout.finishLoadmore();
        }
    }
}
