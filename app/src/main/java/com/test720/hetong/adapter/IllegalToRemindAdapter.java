package com.test720.hetong.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test720.hetong.R;
import com.test720.hetong.base.MBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jie on 2017/2/21.
 */

public class IllegalToRemindAdapter extends MBaseAdapter<String> {
    public IllegalToRemindAdapter(List<String> list, Activity mActivity) {
        super(list, mActivity);
    }

    @Override
    public View getXView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_illegal_to_remind, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    class ViewHolder {

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
