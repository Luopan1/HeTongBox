package com.test720.hetong.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test720.hetong.R;
import com.test720.hetong.base.BaseFragment;
import com.test720.hetong.bean.Car;

import butterknife.BindView;

/**
 * @author LuoPan on 2017/11/28 15:20.
 */

public class CarFragments extends BaseFragment {
    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.carNumber)
    TextView mCarNumber;
    @BindView(R.id.carName)
    TextView mCarName;
    Car mCar;

    public CarFragments() {

    }

    public CarFragments(Car car) {
        this.mCar = car;
    }

    @Override
    protected void initData() {
        Glide.with(getActivity()).load(mCar.getIcon()).into(mIcon);
        mCarName.setText(mCar.getCarName());
        mCarNumber.setText(mCar.getCarLicense());
    }

    @Override
    protected int setlayoutResID() {
        return R.layout.fragment_car;
    }
}
