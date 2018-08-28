package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.cameraview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import java.util.List;

/**
 * Created by mbcloud-cuilk on 2018/5/18.
 */

public class MediaDbAdapter extends BaseAdapter{
    private List<MediaDbBean> mMediaDbBeanList;
    private LayoutInflater inflater;
    private Context mContext;

    public MediaDbAdapter(Context context, List<MediaDbBean> mMediaDbBeanList) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mMediaDbBeanList = mMediaDbBeanList;
    }

    @Override
    public int getCount() {
        return mMediaDbBeanList == null ? 0 : mMediaDbBeanList.size();
    }

    @Override
    public Object getItem(int p) {
        return mMediaDbBeanList == null ? null : mMediaDbBeanList.get(p);
    }

    @Override
    public long getItemId(int p) {
        return p;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.media_list_item, null);
            holder.vImage = (ImageView) convertView.findViewById(R.id.iv);
            holder.mIvPlayVedio = (ImageView) convertView.findViewById(R.id.iv_play_vedio);
            holder.vTvPath = (TextView) convertView.findViewById(R.id.tv_path);
            holder.vTvType = (TextView) convertView.findViewById(R.id.tv_type);
            holder.vTvTime = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MediaDbBean mediaDbBean = mMediaDbBeanList.get(position);
        holder.vImage.setImageBitmap(BitmapFactory.decodeFile(mediaDbBean.fileUrl));
        holder.vTvPath.setText(mediaDbBean.fileUrl); // + "." + (videoList.get(position).getMimeType()).substring(6))
        holder.vTvType.setText(mediaDbBean.type);
        holder.vTvTime.setText(mediaDbBean.time);
        if (mediaDbBean.type != null) {
            if (mediaDbBean.type.equals("vedio")) {
                holder.mIvPlayVedio.setVisibility(View.VISIBLE);
            } else {
                holder.mIvPlayVedio.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.mIvPlayVedio.setVisibility(View.INVISIBLE);
        }

//        holder.vImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                //要播放的视频的路径:file:///storage/emulated/0/DCIM/Camera/VID_20171004_102321.mp4
//                String bpath = "file://" + vList.get(position).getFilePath();
//                intent.setDataAndType(Uri.parse(bpath), "video/*");
//                startActivity(intent);
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        ImageView vImage, mIvPlayVedio;
        TextView vTvPath, vTvType, vTvTime;

    }
}
