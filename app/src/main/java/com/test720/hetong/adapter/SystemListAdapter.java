package com.test720.hetong.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test720.hetong.R;
import com.test720.hetong.base.MBaseAdapter;
import com.test720.hetong.bean.SystemListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jie on 2017/2/21.
 */

public class SystemListAdapter extends MBaseAdapter<SystemListBean> {
    public SystemListAdapter(List<SystemListBean> list, Activity mActivity) {
        super(list, mActivity);
    }

    @Override
    public View getXView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_system_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(list.get(position).getTitle());
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
