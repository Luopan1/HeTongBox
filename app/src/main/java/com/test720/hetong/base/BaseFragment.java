package com.test720.hetong.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test720.hetong.network.ApiService;
import com.test720.hetong.network.HttpsRequest;
import com.test720.hetong.utils.SizeUtils;
import com.test720.hetong.utils.ToastUtil;

import butterknife.ButterKnife;


/**
 * Created by jie on 2016/12/26.
 */

public abstract class BaseFragment extends Fragment {
    public ApiService apiService;
    private Activity mContext;
    public SizeUtils sizeUtils;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        apiService = HttpsRequest.provideClientApi();

        sizeUtils = new SizeUtils(getActivity());
        initView();
        initData();
        setListener();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = inflater.inflate(setlayoutResID(),null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    //初始化控件
    public void initView(){};
    //初始化数据
    protected abstract void initData();
    //设置点击事件
    public void setListener(){};
    //设置布局
    protected abstract int setlayoutResID();
    public void ShowToast(String msg)
    {
        ToastUtil.show(getActivity(),msg);
    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }


    /**
     * 封装跳转方式
     *
     * @param intent 传递参数
     */
    public void jumpToActivity(Intent intent) {

        startActivity(intent);
    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param bundle 传递参数
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 启动Activity，
     *
     * @param clazz  到指定页面
     * @param bundle 传递参数
     * @param flags  Intent.setFlag参数，设置为小于0，则不设置
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle, int flags) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        if (flags > 0) {
            intent.setFlags(flags);
        }

        startActivity(intent);
    }
    
}
