package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord.entity.VideoInfo;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord.tool.Code;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord.tool.CommTools;

import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {
    private ListView mListView;
    private TextView noData;
    private Button btnBack;
    ArrayList<VideoInfo> vList;
    private Intent lastIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mListView = (ListView) findViewById(R.id.lv_video);
        noData = (TextView) findViewById(R.id.tv_nodata);
        btnBack = (Button) findViewById(R.id.btn_back);

        lastIntent = getIntent();
        initData();
        mListView.setOnItemClickListener(new ItemClick());
    }

    private void initData() {
        vList = new ArrayList<VideoInfo>();
        String[] mediaColumns = new String[]{MediaStore.MediaColumns.DATA,
                BaseColumns._ID, MediaStore.MediaColumns.TITLE, MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.Video.VideoColumns.DURATION, MediaStore.MediaColumns.SIZE};
        Cursor cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                VideoInfo info = new VideoInfo();
                info.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
                info.setMimeType(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)));
                info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE)));
                info.setTime(CommTools.LongToHms(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION))));
                info.setSize(CommTools.LongToPoint(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                info.setB(MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id,
                        MediaStore.Images.Thumbnails.MICRO_KIND, options));
                vList.add(info);

            } while (cursor.moveToNext());
        }

        if (vList.size() == 0) {
            mListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            VideoListAdapter adapter = new VideoListAdapter(VideoListActivity.this);
            mListView.setAdapter(adapter);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private class ItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mListView.getItemAtPosition(position);
            //30M 30*1024*1024
            String size = vList.get(position).getSize();// 41.34 M
            String m1 = size.substring(0, size.indexOf("M") - 1);
            int mSize = (int) (Double.parseDouble(m1) * 100);
            if (mSize > 30 * 100) {
                Toast.makeText(VideoListActivity.this, "图片大于30M", Toast.LENGTH_SHORT).show();
                return;
            }
            String filePath = vList.get(position).getFilePath();
            lastIntent.putExtra("path", filePath);
            setResult(Code.LOCAL_VIDEO_RESULT, lastIntent);
            finish();
        }

    }

    class VideoListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public VideoListAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return vList.size();
        }

        @Override
        public Object getItem(int p) {
            return vList.get(p);
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
                convertView = inflater.inflate(R.layout.item_video_list, null);
                holder.vImage = (ImageView) convertView.findViewById(R.id.video_img);
                holder.vTitle = (TextView) convertView.findViewById(R.id.video_title);
                holder.vSize = (TextView) convertView.findViewById(R.id.video_size);
                holder.vTime = (TextView) convertView.findViewById(R.id.video_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.vImage.setImageBitmap(vList.get(position).getB());
            holder.vTitle.setText(vList.get(position).getTitle()); // + "." + (videoList.get(position).getMimeType()).substring(6))
            holder.vSize.setText(vList.get(position).getSize());
            holder.vTime.setText(vList.get(position).getTime());

            holder.vImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //要播放的视频的路径:file:///storage/emulated/0/DCIM/Camera/VID_20171004_102321.mp4
                    String bpath = "file://" + vList.get(position).getFilePath();
                    intent.setDataAndType(Uri.parse(bpath), "video/*");
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView vImage;
            TextView vTitle;
            TextView vSize;
            TextView vTime;
        }
    }
}
