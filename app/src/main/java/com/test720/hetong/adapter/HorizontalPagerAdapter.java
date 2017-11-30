package com.test720.hetong.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test720.hetong.R;
import com.test720.hetong.bean.Car;

import java.util.List;

/**
 * @author LuoPan on 2017/11/28 11:38.
 */

public class HorizontalPagerAdapter extends PagerAdapter {
    private List<Car> mCars;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public HorizontalPagerAdapter(List<Car> cars, LayoutInflater layoutInflater, Context context) {
        mCars = cars;
        mLayoutInflater = layoutInflater;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCars.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.item_car_item, container, false);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView carNumber = (TextView) view.findViewById(R.id.carNumber);
        TextView carName = (TextView) view.findViewById(R.id.carName);

        Glide.with(mContext).load(mCars.get(position).getIcon()).into(icon);
        carNumber.setText(mCars.get(position).getCarLicense());
        carName.setText(mCars.get(position).getCarName());
        container.addView(view);
        return view;
    }
}
