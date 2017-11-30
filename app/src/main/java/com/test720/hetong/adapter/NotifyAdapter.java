package com.test720.hetong.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test720.hetong.R;
import com.test720.hetong.bean.notify;
import com.test720.hetong.utils.Constants;
import com.test720.hetong.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author LuoPan on 2017/11/27 15:25.
 */

public class NotifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnItemClickListener listener;//点击事件监听器
    private List<notify.DataBean.ListBean> mDatalists;
    private Context mContext;
    private RecyclerView recyclerView;


    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }


    public NotifyAdapter(List<notify.DataBean.ListBean> datalists, Context context) {
        mDatalists = datalists;
        mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        default:
                            return 1;
                    }
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("TAG+", viewType + "viewType");
        switch (viewType) {
            case 1:
                return new CarshHolder(LayoutInflater.from(mContext).inflate(R.layout.item_crash_item, null));
            case 2:
                return new WEiZhangHolder(LayoutInflater.from(mContext).inflate(R.layout.item_weizhang_item, null));
            case 3:
                return new DriveReportHolder(LayoutInflater.from(mContext).inflate(R.layout.item_drive_report, null));
            case 4:
                return new DZWeilanHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dzwl_item, null));
            default:
                Log.d("error", "viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null && view != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    listener.onItemClick(recyclerView, view, position);
                }
            }
        });


        if (holder instanceof CarshHolder) {
            setCrashData((CarshHolder) holder, position);
        } else if (holder instanceof WEiZhangHolder) {
            setWeiZhangData((WEiZhangHolder) holder, position);
        } else if (holder instanceof DriveReportHolder) {
            setDriveReportData((DriveReportHolder) holder, position);
        } else if (holder instanceof DZWeilanHolder) {
            setDZWweiLan((DZWeilanHolder) holder, position);
        }

    }

    private void setDZWweiLan(DZWeilanHolder holder, int position) {
        Glide.with(mContext).load(R.mipmap.ic_launcher).asBitmap().into(holder.mIcon);
        holder.mTime.setText(mDatalists.get(position).getTime());
        holder.mBaojingshijian.setText("报警时间：" + mDatalists.get(position).getPz_time());
    }


    private void setDriveReportData(DriveReportHolder holder, int position) {
        Glide.with(mContext).load(R.mipmap.ic_launcher).asBitmap().into(holder.mIcon);
        holder.mTime.setText(mDatalists.get(position).getTime());
        holder.mDangrixingshilicheng.setText("当日行驶里程:" + mDatalists.get(position).getXslc() + "Km");
        holder.mXingcheshijian.setText("行车时间：" + mDatalists.get(position).getXssj() + "分钟");
        holder.mPingjunsudu.setText("平均车速:" + mDatalists.get(position).getPjss() + "m/s");

    }

    private void setWeiZhangData(WEiZhangHolder holder, int position) {
        Glide.with(mContext).load(R.mipmap.ic_launcher).asBitmap().into(holder.mIcon);
        holder.mTime.setText(mDatalists.get(position).getTime());
    }

    private void setCrashData(CarshHolder holder, int position) {
        Log.e("TAG++++", Constants.getCashImage(mDatalists.get(position).getLongX(), mDatalists.get(position).getLat()));
        Glide.with(mContext).load(Constants.getCashImage(mDatalists.get(position).getLongX(), mDatalists.get(position).getLat())).asBitmap().into(holder.mMapView);
        Glide.with(mContext).load(R.mipmap.ic_launcher).asBitmap().into(holder.mIcon);
        holder.mTime.setText(mDatalists.get(position).getTime());
        if (mDatalists.get(position).getCollision_type().equals("0")) {
            holder.mCashKind.setText("驻车碰撞警告                             ");
        } else {
            holder.mCashKind.setText("行车碰撞警告                             ");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mDatalists.get(position).getMeun_type().equals("2")) {
            return 1;
        } else if (mDatalists.get(position).getMeun_type().equals("4")) {
            return 2;
        } else if (mDatalists.get(position).getMeun_type().equals("1")) {
            return 3;
        } else if (mDatalists.get(position).getMeun_type().equals("3")) {
            return 4;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return mDatalists.size();
    }

    class CarshHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        CircleImageView mIcon;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.mapImage)
        ImageView mMapView;
        @BindView(R.id.itemView)
        LinearLayout mItemView;
        @BindView(R.id.cashKind)
        TextView mCashKind;

        public CarshHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class WEiZhangHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        CircleImageView mIcon;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.itemView)
        LinearLayout mItemView;

        public WEiZhangHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DriveReportHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.icon)
        CircleImageView mIcon;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.itemView)
        LinearLayout mItemView;
        @BindView(R.id.dangrixingshilicheng)
        TextView mDangrixingshilicheng;
        @BindView(R.id.xingcheshijian)
        TextView mXingcheshijian;
        @BindView(R.id.pingjunsudu)
        TextView mPingjunsudu;
        @BindView(R.id.mingrixianxingweihao)
        TextView mMingrixianxingweihao;
        @BindView(R.id.mingritianqi)
        TextView mMingritianqi;

        public DriveReportHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DZWeilanHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        CircleImageView mIcon;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.baojingshijian)
        TextView mBaojingshijian;


        public DZWeilanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
