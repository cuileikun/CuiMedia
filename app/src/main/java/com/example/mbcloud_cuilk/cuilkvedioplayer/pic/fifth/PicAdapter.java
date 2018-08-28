package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.fifth;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mbcloud-cuilk on 2018/5/15.
 */

public class PicAdapter extends BaseAdapter {
    private Context mContext;
    private List<PicBean> mPicBeanList;

    public PicAdapter(Context mContext, List<PicBean> mPicBeanList) {
        this.mContext = mContext;
        this.mPicBeanList = mPicBeanList;
    }

    @Override
    public int getCount() {
        return mPicBeanList == null ? 0 : mPicBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPicBeanList == null ? null : mPicBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PicBean bean = mPicBeanList.get(position);
        View view = View.inflate(mContext, R.layout.pic_item, null);
        TextView mTvTime = (TextView) view.findViewById(R.id.tv_time);
        TextView mTvPath = (TextView) view.findViewById(R.id.tv_path);
        String newtime = String.valueOf(bean.getTime());
        if (!TextUtils.isEmpty(newtime) && !newtime.equals("0")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = formatter.format(new Date(bean.getTime()));
            mTvTime.setText("拍摄时间是：" + format);
        }else {
            mTvTime.setText("拍摄时间是：" );
        }
        mTvPath.setText("存储图片的路径是：" + bean.getPath());

        return view;
    }

}
