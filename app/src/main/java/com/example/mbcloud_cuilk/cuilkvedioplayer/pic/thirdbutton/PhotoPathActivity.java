package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.thirdbutton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.ListDataSave;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class PhotoPathActivity extends AppCompatActivity {

    private ListView mListView;
    ListDataSave carmedataSave;
    ListDataSave gallarydataSave;
    private List<String> mPathList = new ArrayList<>();
    private LayoutInflater inflater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_path);
        initView();
        initData();

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.path_list);
    }

    private void initData() {

        carmedataSave = new ListDataSave(PhotoPathActivity.this, "carme");
        gallarydataSave = new ListDataSave(PhotoPathActivity.this, "gallary");
        List<String> carmephotoList = carmedataSave.getDataList("carmephotoList");
        List<String> gallaryList = gallarydataSave.getDataList("gallaryphotoList");
        if (mPathList.size() > 0) {
            mPathList.clear();
        }
        if (null != carmephotoList && carmephotoList.size() > 0) {
            mPathList.addAll(carmephotoList);
        }
        if (null != gallaryList && gallaryList.size() > 0) {
            for (int i = 0; i < gallaryList.size(); i++) {
                mPathList.add(gallaryList.get(i));
            }
        }
        if (mPathList.size() > 0) {
            PathAdapter adapter = new PathAdapter(mPathList);
            mListView.setAdapter(adapter);
        } else {
            ToastUtils.showShort(PhotoPathActivity.this, "暂无数据");
        }


    }

    public class PathAdapter extends BaseAdapter {
        private List<String> mPathList = new ArrayList<>();

        public PathAdapter(List<String> mPathList) {
            this.mPathList = mPathList;
        }

        @Override
        public int getCount() {
            return mPathList.size();
        }

        @Override
        public Object getItem(int position) {
            return mPathList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(PhotoPathActivity.this, R.layout.path_item, null);
            TextView mTvPath = (TextView) view.findViewById(R.id.tv_path);
            ImageView image = (ImageView) view.findViewById(R.id.image);

            mTvPath.setText(mPathList.get(position));

            Bitmap bitmap = compressBitmap(mPathList.get(position), 80, 80);
            image.setImageBitmap(bitmap);
            return view;
        }

    }

    /**
     * @param path 图片地址
     * @param targetWidth 目标图片的宽，单位px
     * @param targetHeight 目标图片的高，单位px
     * @return 返回压缩后的图片的Bitmap
     */
    public Bitmap compressBitmap(String path, int targetWidth, int targetHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设为true，节约内存
        BitmapFactory.decodeFile(path, options);//返回null
        int height = options.outHeight;//得到源图片height，单位px
        int width = options.outWidth;//得到源图片的width，单位px
        //计算inSampleSize
        options.inSampleSize = calculateInSampleSize(width,height,targetWidth,targetHeight);
        options.inJustDecodeBounds = false;//设为false，可以返回Bitmap
        return BitmapFactory.decodeFile(path,options);
    }

    /**
     * 计算压缩比例
     * @param width  源图片的宽
     * @param height 源图片的高
     * @param targetWidth  目标图片的宽
     * @param targetHeight 目标图片的高
     * @return inSampleSize 压缩比例
     */
    public int calculateInSampleSize(int width,int height, int targetWidth, int targetHeight) {
        int inSampleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            //计算图片实际的宽高和目标图片宽高的比率
            final int heightRate = Math.round((float) height / (float) targetHeight);
            final int widthRate = Math.round((float) width / (float) targetWidth);
            //选取最小的比率作为inSampleSize
            inSampleSize = heightRate < widthRate ? heightRate : widthRate;
        }
        return inSampleSize;
    }

}
