package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord.dialog.ActionSheetDialog;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord.dialog.OnOperItemClickL;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord.tool.Code;

public class VedioRecordActivity extends AppCompatActivity {

    private ImageView ivVideo;
    private String filPaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_record);
        ivVideo = (ImageView) findViewById(R.id.iv_video);
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showVideoDialog();
            }
        });
    }
    private void showVideoDialog() {
        final String[] stringItems = {"录制视频", "本地视频",};
        final ActionSheetDialog sheetDialog = new ActionSheetDialog(VedioRecordActivity.this,
                stringItems, null);
        sheetDialog.title("上传视频")//
                .titleTextSize_SP(14.5f)//
                .show();

        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            private Intent intent;

            @Override
            public void onOperItemClick(AdapterView<?> parent, View view,int position, long id) {
                if (position == 0) {
                    intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);// 创建一个请求视频的意图
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);// 设置视频的质量，值为0-1，
//                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);// 设置视频的录制长度，s为单位
                    intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20 * 1024 * 1024L);// 设置视频文件大小，字节为单位
                    startActivityForResult(intent, Code.VIDEO_RECORD_REQUEST);// 设置请求码，在onActivityResult()方法中接收结果
                } else if (position == 1) {
                    intent = new Intent(VedioRecordActivity.this, VideoListActivity.class);
                    startActivityForResult(intent, Code.LOCAL_VIDEO_REQUEST);
                }
                sheetDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Code.VIDEO_RECORD_REQUEST:
                if (null != data) {
                    Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    } else {
                        Cursor c = getContentResolver().query(uri,
                                new String[]{MediaStore.MediaColumns.DATA},
                                null, null, null);
                        if (c != null && c.moveToFirst()) {
                            //系统录制视频默认路径：/storage/emulated/0/DCIM/Camera/VID_20180517_112651.mp4
                            filPaths = c.getString(0);
                            showUploadVideoDialog();
                        }
                    }
                }
                break;
            case Code.LOCAL_VIDEO_REQUEST:
                if (resultCode == Code.LOCAL_VIDEO_RESULT && data != null) {
                    //打开本地视频默认路径：/storage/emulated/0/DCIM/Camera/VID_20180517_112853.mp4
                    filPaths = data.getStringExtra("path");
                    showUploadVideoDialog();
                }
                break;
            default:
                break;
        }
    }

    private void showUploadVideoDialog() {
        final String[] stringItems = {"直接上传"};
        final ActionSheetDialog sheetDialog = new ActionSheetDialog(VedioRecordActivity.this,
                stringItems, null);
        sheetDialog.title("上传视频")//
                .titleTextSize_SP(14.5f)//
                .show();

        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {

            @Override
            public void onOperItemClick(
                    AdapterView<?> parent, View view,
                    int position, long id) {
                if (position == 0) {
                    /**
                     *   这里可以添加上传视频的方法
                     *
                     */
                    Toast.makeText(VedioRecordActivity.this, "=====上传视频====", Toast.LENGTH_LONG).show();
                }
                sheetDialog.dismiss();
            }
        });
    }


}
