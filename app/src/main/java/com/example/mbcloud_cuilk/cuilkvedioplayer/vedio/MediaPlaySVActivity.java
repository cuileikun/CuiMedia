package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.TimeUtils;
import com.githang.statusbar.StatusBarCompat;

import java.lang.ref.WeakReference;

import static android.media.MediaPlayer.OnCompletionListener;
import static android.media.MediaPlayer.OnPreparedListener;


public class MediaPlaySVActivity extends FragmentActivity implements View.OnClickListener {

    private MediaPlayer mMediaPlayer;
    private int position = 1;
    private String url;
    private int maxTime;
    private int recLen = 0;
    private Thread mThread;
    private boolean isPause = false;//是否暂停
    private String time;//录制时长
    private boolean isNet;
    private long startTime;
    private int mRealTime;
    private SurfaceView mSurfaceView;
    private SeekBar mSeekbar;
    private TextView mTvPregressTime;
    private TextView mTvTotalTime;
    private ImageView mIvPlayButton;
    private String mLPurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_media_play_sv);
        initView();
        initData();
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSeekbar = (SeekBar) findViewById(R.id.sbr);
        mTvPregressTime = (TextView) findViewById(R.id.tv_progress_time);
        mTvTotalTime = (TextView) findViewById(R.id.tv_total_time);
        mIvPlayButton = (ImageView) findViewById(R.id.iv_play_button);
        ImageView mLeftBtn = (ImageView) findViewById(R.id.left_btn);
        mLeftBtn.setOnClickListener(this);
        ImageView mPlayBtn = (ImageView) findViewById(R.id.iv_play_button);
        mPlayBtn.setOnClickListener(this);
    }

    private void initData() {
        //设置状态栏的颜色
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bar_red));
//        url = getIntent().getStringExtra("url");
        ((TextView) findViewById(R.id.title)).setText("播放网络视频");
        isNet = false;
        url = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
        mLPurl = getIntent().getStringExtra("url");
        if (null != mLPurl && !TextUtils.isEmpty(mLPurl)) {
            url = mLPurl;
        }
        mMediaPlayer = new MediaPlayer();
        // 设置SurfaceView自己不管理的缓冲区
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (position > 0) {
                    try {
                        // 开始播放
                        play();
                        // 并直接从指定位置开始播放
                        mMediaPlayer.seekTo(position);
                        position = 0;
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }


    /**
     * 暂停与播放切换
     */
    private void changPlayState() {
        if (mMediaPlayer.isPlaying() && !isPause) {
            if (mMediaPlayer != null) {
                int currentPosition = mMediaPlayer.getCurrentPosition() / 1000;
                recLen = currentPosition + 1;
            }
            mMediaPlayer.pause();//暂停播放
            isPause = true;
            mIvPlayButton.setBackgroundResource(R.drawable.vedio_play);
            handler.removeCallbacks(runnable);
        } else {
            mMediaPlayer.start();//继续播放
            if (mMediaPlayer != null) {
                int currentPosition = mMediaPlayer.getCurrentPosition() / 1000;
                recLen = currentPosition + 1;
            }
            isPause = false;
            mIvPlayButton.setBackgroundResource(R.drawable.vedio_pause);
            handler.postDelayed(runnable, 1);
        }
    }

    /**
     * 退出当前页面
     */
    private void exit() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer = null;
        }
        try {
            mThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }


    private void play() {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //是否重复播放
            mMediaPlayer.setLooping(true);
            // 设置需要播放的视频
            Uri uri = Uri.parse(url);
            mMediaPlayer.setDataSource(getApplicationContext(), uri);
            // 把视频画面输出到SurfaceView
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            mMediaPlayer.prepare();


            mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mMediaPlayer.start();
                    maxTime = mMediaPlayer.getDuration() / 1000 + 1;
                    handler.postDelayed(runnable, 1);
                }
            });

            time = TimeUtils.getTime(mMediaPlayer.getDuration() / 1000);
            mTvTotalTime.setText(time);
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (mMediaPlayer != null) {
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.stop();
                        }
                        mMediaPlayer = null;
                    }
                    try {
                        mThread.interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mTvPregressTime.setText(time);
                    finish();
                }
            });
            //设置seekbar的最大值
            mSeekbar.setMax(mMediaPlayer.getDuration());
            //创建一个线程
            mThread = new Thread(new SeekBarThread(this));
            //启动线程
            mThread.start();

        } catch (Exception e) {
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            recLen++;
            if (recLen > maxTime) {
                return;
            } else {
                String time = TimeUtils.getTime(recLen - 1);
                mTvPregressTime.setText(time);
                handler.postDelayed(this, 1000);

            }

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_btn:
                exit();
                break;
            case R.id.iv_play_button:
                changPlayState();
                break;
        }
    }

    // 自定义的线程
    private static class SeekBarThread implements Runnable {
        private WeakReference<MediaPlaySVActivity> activity;

        public SeekBarThread(MediaPlaySVActivity activity) {
            this.activity = new WeakReference<MediaPlaySVActivity>(activity);
        }

        @Override
        public void run() {
            if (activity.get() == null) {
                return;
            }
            while (activity.get().mMediaPlayer != null) {
//             将SeekBar位置设置到当前播放位置
                try {
                    activity.get().mSeekbar.setProgress(activity.get().mMediaPlayer.getCurrentPosition());
                    // 每100毫秒更新一次位置
                    Thread.sleep(100);
                    if (activity.get() == null) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //来电处理
    protected void onDestroy() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                try {
                    mThread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mMediaPlayer.release();
        }
        super.onDestroy();
    }


    protected void onPause() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                try {
                    mThread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onPause();
    }

    protected void onResume() {
        if (mMediaPlayer != null) {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();

            }
        }
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer = null;
            }
            try {
                mThread.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }

            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
