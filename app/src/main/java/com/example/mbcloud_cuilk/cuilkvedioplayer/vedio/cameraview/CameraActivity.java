package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.cameraview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.DeviceUtil;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.AndroidUtil;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {
    private JCameraView jCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_camera);
        jCameraView = (JCameraView) findViewById(R.id.jcameraview);
        //设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_BOTH);
        jCameraView.setTip("JCameraView Tip");
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.i("CJT", "camera error");
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(CameraActivity.this, "给点录音权限可以?", Toast.LENGTH_SHORT).show();
            }
        });
        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                if (null != bitmap) {
                    ArrayList<String> strs = new ArrayList<>();
                    strs.add("cuileikun/666666");
                    strs.add("浦东新区");
                    //获取图片bitmap
//                Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
                    //:  /storage/emulated/0/JCamera/picture_1526613364888.jpg

                    bitmap = ImageUtils.drawTextToCenter2(CameraActivity.this, bitmap, strs, 23, 0x8f999999);
                    MediaDbBean mediaDbBean = new MediaDbBean();
                    //设置时间戳
                    long nowTime = System.currentTimeMillis();
                    long cTime = nowTime - TimeUtils.mEndTime;
                    if (cTime > TimeUtils.TIME) {
                        mediaDbBean.setTimestamp(String.valueOf(nowTime));
                        TimeUtils.mEndTime = nowTime;
                    } else {
                        mediaDbBean.setTimestamp(String.valueOf(TimeUtils.mEndTime));
                    }
                    //=:2018-05-22 09:28:03
//                String time = AndroidUtil.getTime();
                    String time = AndroidUtil.secondToNowTime(nowTime);
                    //=:/storage/emulated/0/JCamera/picture_1526952522704.jpg
                    String path = FileUtil.saveBitmap("JCamera", bitmap);
                    mediaDbBean.type = "image";
                    mediaDbBean.fileUrl = path;
                    if (!TextUtils.isEmpty(time)) {
                        mediaDbBean.time = time;
                    }
                    mediaDbBean.save();

                    MediaDbOuterBean mediaDbOuterBean = new MediaDbOuterBean();
                    //设置时间戳
                    if (cTime > TimeUtils.TIME) {
                        mediaDbOuterBean.setTimestamp(String.valueOf(nowTime));
                        TimeUtils.mEndTime = nowTime;
                        mediaDbOuterBean.getMediaDbBeanList().add(mediaDbBean);
                        mediaDbOuterBean.save();
                    } else {
                        mediaDbOuterBean.setTimestamp(String.valueOf(TimeUtils.mEndTime));
                    }
//                mediaDbOuterBean.getMediaDbBeanList().add(mediaDbBean);
//                mediaDbOuterBean.save();

                    Intent intent = new Intent();
                    intent.putExtra("path", path);
                    setResult(101, intent);
                    finish();
                }
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                if (!TextUtils.isEmpty(url)) {
                    ArrayList<String> strs = new ArrayList<>();
                    strs.add("2018-10-10 12:30:30");
                    strs.add("cuileikun/666666");
                    strs.add("浦东新区陆家嘴环路1088号");
                    Bitmap frame = ImageUtils.createWaterMaskCenter(CameraActivity.this, firstFrame, BitmapFactory.decodeResource(getResources(), R.drawable.vedio));
//                 frame = ImageUtils.drawTextToCenter(CameraActivity.this, frame, "cuileikunmedia", 23, 0x8f999999);
                    frame = ImageUtils.drawTextToCenter3(CameraActivity.this, frame, strs, 23, 0x8f999999);
                    // url="/storage/emulated/0/JCamera/video_1526613447483.mp4"
                    //获取视频路径  /storage/emulated/0/JCamera/picture_1526613484671.jpg
                    MediaDbBean mediaDbBean = new MediaDbBean();
                    long nowTime = System.currentTimeMillis();
                    long cTime = nowTime - TimeUtils.mEndTime;
                    if (cTime > TimeUtils.TIME) {
                        mediaDbBean.setTimestamp(String.valueOf(nowTime));
                        TimeUtils.mEndTime = nowTime;
                    } else {
                        mediaDbBean.setTimestamp(String.valueOf(TimeUtils.mEndTime));
                    }
                    String time = AndroidUtil.getTime();
                    String path = FileUtil.saveBitmap("JCamera", frame);
                    Log.i("CJT", "url = " + url + ", Bitmap = " + path);
                    mediaDbBean.type = "vedio";
                    mediaDbBean.fileUrl = path;
                    mediaDbBean.vedioPath = url;
                    if (!TextUtils.isEmpty(time)) {
                        mediaDbBean.time = time;
                    }
                    mediaDbBean.save();

                    MediaDbOuterBean mediaDbOuterBean = new MediaDbOuterBean();
                    if (cTime > TimeUtils.TIME) {
                        mediaDbOuterBean.setTimestamp(String.valueOf(nowTime));
                        TimeUtils.mEndTime = nowTime;
                        mediaDbOuterBean.getMediaDbBeanList().add(mediaDbBean);
                        mediaDbOuterBean.save();
                    } else {
                        mediaDbOuterBean.setTimestamp(String.valueOf(TimeUtils.mEndTime));
                    }
//                mediaDbOuterBean.getMediaDbBeanList().add(mediaDbBean);
//                mediaDbOuterBean.save();
                    Intent intent = new Intent();
                    intent.putExtra("path", path);
                    setResult(101, intent);
                    finish();
                }
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                CameraActivity.this.finish();
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(CameraActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });

        Log.i("CJT", DeviceUtil.getDeviceModel());
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }
}
