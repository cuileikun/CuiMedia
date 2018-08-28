package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.originandcustomvediorecoder.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.originandcustomvediorecoder.activity.CustomRecordActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Android (系统+自定义)短视频录制（含暂停继续录制功能） 总结
 * 原文地址：https://blog.csdn.net/android_technology/article/details/69388902
 */
public class SmallVedioActivity extends AppCompatActivity {
    public static final int RECORD_SYSTEM_VIDEO = 1;
    //    public static final int RECORD_CUSTOM_VIDEO = 2;
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_vedio);
        mVideoView = (VideoView) findViewById(R.id.videoView);
    }
    /**
     * 启用系统相机录制
     *
     * @param view
     */
    public void reconverIntent(View view) {
        File outputMediaFile = getOutputMediaFile();//=: /storage/emulated/0/DCIM/MyCameraApp/VID_20180524_142912.mp4
        Uri fileUri = Uri.fromFile(getOutputMediaFile());//=:  file:///storage/emulated/0/DCIM/MyCameraApp/VID_20180524_142942.mp4
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);     //限制的录制时长 以秒为单位
//        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024);        //限制视频文件大小 以字节为单位
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);      //设置拍摄的质量0~1
//        intent.putExtra(MediaStore.EXTRA_FULL_SCREEN, false);        // 全屏设置
        startActivityForResult(intent, RECORD_SYSTEM_VIDEO);

    }

    /**
     * 启动自定义相机录制
     *
     * @param view
     */
    public void customVideo(View view) {
        Intent intent = new Intent(this, CustomRecordActivity.class);
        startActivity(intent);
    }


    /**
     * Create a File for saving an video
     */
    private File getOutputMediaFile() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(this, "请检查SDCard！", Toast.LENGTH_SHORT).show();
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case RECORD_SYSTEM_VIDEO:
                Uri data1 = data.getData();//=:  file:///storage/emulated/0/DCIM/MyCameraApp/VID_20180524_142942.mp4
                mVideoView.setVideoURI(data.getData());
                mVideoView.start();
                break;

        }
    }
}
