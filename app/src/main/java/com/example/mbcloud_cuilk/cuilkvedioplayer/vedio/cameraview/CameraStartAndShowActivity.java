package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.cameraview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cjt2325.cameralibrary.util.DeviceUtil;
import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.preview.NewPreviewActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.AndroidUtil;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CameraStartAndShowActivity extends AppCompatActivity {
    private Context mContext;
    private TextView device;
    private List<MediaDbBean> mMediaDbBeanList;
    private List<MediaDbOuterBean> mMediaDbOuterBeanList;
    private ListView mListView;
    private MediaDbAdapter adapter;
    private int mGridViewItmeWith;//GridView控件中每个itme图片的大小
    private MyGridViewAdpter gridAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_start_and_show);
        initData();
    }

    private void initData() {
        mContext = CameraStartAndShowActivity.this;
        mGridViewItmeWith = AndroidUtil.getScreenWidth(mContext) / 4;
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(mContext, CameraActivity.class), 100);
            }
        });
        mListView = (ListView) findViewById(R.id.lv);
        device = (TextView) findViewById(R.id.device);
        device.setText(DeviceUtil.getDeviceInfo());


        if (null == mMediaDbOuterBeanList) {
            mMediaDbOuterBeanList = new ArrayList<>();
        }
        if (mMediaDbOuterBeanList.size() > 0) {
            mMediaDbOuterBeanList.clear();
        }
        List<MediaDbOuterBean> mCacheMediaOuterLists = DataSupport.findAll(MediaDbOuterBean.class);
        if (null != mCacheMediaOuterLists && mCacheMediaOuterLists.size() > 0) {
            mMediaDbOuterBeanList.addAll(mCacheMediaOuterLists);
            Collections.reverse(mMediaDbOuterBeanList);
        }

        adapter = new MediaDbAdapter(mContext, mMediaDbOuterBeanList);
        mListView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            List<MediaDbOuterBean> mNewCacheMediaOuterLists = DataSupport.findAll(MediaDbOuterBean.class);
            if (mMediaDbOuterBeanList.size() > 0) {
                mMediaDbOuterBeanList.clear();
            }
            mMediaDbOuterBeanList.addAll(mNewCacheMediaOuterLists);
            Collections.reverse(mMediaDbOuterBeanList);
            adapter.notifyDataSetChanged();

            Log.i("CJT", "picture");
//            String path = data.getStringExtra("path");
//            photo.setImageBitmap(BitmapFactory.decodeFile(path));

        }
        if (resultCode == 102) {
            Log.i("CJT", "video");
            String path = data.getStringExtra("path");
        }
        if (resultCode == 103) {
            Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show();
        }
    }

    public class MediaDbAdapter extends BaseAdapter {
        private List<MediaDbOuterBean> mMediaDbOuterBeanList;
        private LayoutInflater inflater;
        private Context mContext;

        public MediaDbAdapter(Context context, List<MediaDbOuterBean> mMediaDbOuterBeanList) {
            this.mContext = context;
            this.inflater = LayoutInflater.from(context);
            this.mMediaDbOuterBeanList = mMediaDbOuterBeanList;
        }

        @Override
        public int getCount() {
            return mMediaDbOuterBeanList == null ? 0 : mMediaDbOuterBeanList.size();
        }

        @Override
        public Object getItem(int p) {
            return mMediaDbOuterBeanList == null ? null : mMediaDbOuterBeanList.get(p);
        }

        @Override
        public long getItemId(int p) {
            return p;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MediaDbOuterBean mediaDbOuterBean = mMediaDbOuterBeanList.get(position);
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.new_media_list_item, null);
                holder.mTime = (TextView) convertView.findViewById(R.id.time);
                holder.mNum = (TextView) convertView.findViewById(R.id.tv_num);
                holder.mGridView = (GridView) convertView.findViewById(R.id.gridview);
                holder.mGridView.getLayoutParams().height = mGridViewItmeWith;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //
            holder.mTime.setText(AndroidUtil.secondToNowTime(Long.parseLong(mediaDbOuterBean.timestamp)));
            final List<MediaDbBean> mediaDbBeanList = DataSupport.where("timestamp = ?", mediaDbOuterBean.timestamp).find(MediaDbBean.class);
            holder.mNum.setText(mediaDbBeanList.size() + "");
            Collections.reverse(mediaDbBeanList);
            gridAdpter = (MyGridViewAdpter) holder.mGridView.getAdapter();
            if (gridAdpter != null) {
                gridAdpter.mediaDbBeanList = mediaDbBeanList;
                gridAdpter.notifyDataSetChanged();
            } else {
                gridAdpter = new MyGridViewAdpter();
                gridAdpter.mediaDbBeanList = mediaDbBeanList;
                holder.mGridView.setAdapter(gridAdpter);
            }

            holder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(mContext, NewPreviewActivity.class);
                    intent.putExtra("bean", (Serializable) mediaDbBeanList);
                    startActivity(intent);
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView mTime;
            TextView mNum;
            GridView mGridView;
        }
    }

    //gridview的适配器 安卓规范写法
    private class MyGridViewAdpter extends BaseAdapter {

        public List<MediaDbBean> mediaDbBeanList;

        @Override
        public int getCount() {
            return mediaDbBeanList == null ? 0 : mediaDbBeanList.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return mediaDbBeanList == null ? null : mediaDbBeanList.get(i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final MediaDbBean bean = mediaDbBeanList.get(i);
            GridViewHolder gridViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.activity_camera_grid_itme, null);
                gridViewHolder = new GridViewHolder();
                gridViewHolder.imageView = (ImageView) view.findViewById(R.id.img);
                gridViewHolder.check_img = (ImageView) view.findViewById(R.id.check_img);
                gridViewHolder.imageView.getLayoutParams().width = mGridViewItmeWith;
                gridViewHolder.imageView.getLayoutParams().height = mGridViewItmeWith;
                gridViewHolder.checkBox = (CheckBox) view.findViewById(R.id.box);
                gridViewHolder.mVideoIcon = (ImageView) view.findViewById(R.id.video_icon);
                view.setTag(gridViewHolder);
            } else {
                gridViewHolder = (GridViewHolder) view.getTag();
            }
            gridViewHolder.check_img.setVisibility(View.GONE);
            gridViewHolder.mVideoIcon.setVisibility(View.GONE);
            if (bean.type.equals("image")) {
                Glide.with(mContext).load(bean.fileUrl).into(gridViewHolder.imageView);
            } else if (bean.type.equals("vedio")) {
                //   gridViewHolder.mVideoIcon.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(bean.fileUrl).into(gridViewHolder.imageView);
            } else if (bean.type.equals("audio")) {
                Glide.with(mContext).load(R.drawable.media_audio).into(gridViewHolder.imageView);
            }
            return view;
        }
    }

    private class GridViewHolder {
        public ImageView imageView;
        public CheckBox checkBox;
        public ImageView mVideoIcon;
        public ImageView check_img;
    }
}
