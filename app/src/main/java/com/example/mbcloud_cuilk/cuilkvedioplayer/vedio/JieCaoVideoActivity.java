package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * jiecaovideoplayer视频播放
 * 这个播放器本身就具有自动记忆播放进度的功能,全屏状态下具有手势快进和快退的功能,以及左边控制亮度,右边控制音量
 * 点击全屏后视频还是竖屏,需要旋转手机才能变成横屏播放
 */
public class JieCaoVideoActivity extends FragmentActivity {
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    String s1="http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    String s2="http://player.youku.com/player.php/sid/XMjUyODI2NDc2MA==/v.swf";//不支持swf格式的视频播放
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jie_cao_video);
        ((TextView) findViewById(R.id.title)).setText("jiecaovideoplayer的使用");
        jcVideoPlayerStandard= (JCVideoPlayerStandard) findViewById(R.id.jiecao_Player);
        jcVideoPlayerStandard.setUp(s1,jcVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"视频标题");

    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
