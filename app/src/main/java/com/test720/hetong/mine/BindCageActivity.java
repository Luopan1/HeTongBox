package com.test720.hetong.mine;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.test720.hetong.R;
import com.test720.hetong.adapter.MyPagerAdapter;
import com.test720.hetong.base.BaseToolbarActivity;
import com.test720.hetong.bean.Car;
import com.test720.hetong.fragment.CarFragments;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class BindCageActivity extends BaseToolbarActivity {


    @BindView(R.id.cageNumber)
    EditText mCageNumber;
    @BindView(R.id.commit)
    Button mCommit;
    List<Car> cars = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.up)
    ImageView mUp;
    @BindView(R.id.next)
    ImageView mNext;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    List<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    private int index;
    private MyPagerAdapter mAdapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_bind_cage;
    }

    @Override
    protected void initData() {
        cars.add(new Car("http://pic145.nipic.com/file/20171101/22439450_212049819000_2.jpg", "川A888888", "宝时捷911"));
        cars.add(new Car("http://pic99.huitu.com/res/20170912/251420_20170912142322096060_1.jpg", "川A99999", "宝马x5"));
        cars.add(new Car("http://pic89.nipic.com/file/20160219/21675084_223534934001_2.jpg", "川A00000", "法拉利XT"));
        cars.add(new Car("http://pic89.nipic.com/file/20160219/21675084_223534934001_2.jpg", "川A11111111111111", "法拉利XT"));
        for (int i = 0; i < cars.size(); i++) {
            mFragments.add(new CarFragments(cars.get(i)));
        }
        mAdapter = new MyPagerAdapter(mFragments, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);
        mAdapter.registerDataSetObserver(mIndicator.getDataSetObserver());

    }

    @Override
    public void setListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initView() {
        initToobar("和车宝");
    }

    @OnClick({R.id.up, R.id.next, R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.up:
                Log.e("TAG++", index + "___" + cars.size());
                if (index >= 0) {
                    mViewPager.setCurrentItem(index - 1);
                }
                break;
            case R.id.next:
                Log.e("TAG++", index + "___" + cars.size());
                if (index <= cars.size() - 1) {
                    mViewPager.setCurrentItem(index + 1);
                }
                break;
            case R.id.commit:
                break;
        }
    }
}
