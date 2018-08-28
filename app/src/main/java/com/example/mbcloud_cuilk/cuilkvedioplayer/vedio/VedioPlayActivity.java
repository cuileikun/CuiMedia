package com.example.mbcloud_cuilk.cuilkvedioplayer.vedio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.cameraview.CameraStartAndShowActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.mediarecorder.MediaRecorderActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.originandcustomvediorecoder.activity.SmallVedioActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.vediorecord.VedioRecordActivity;

public class VedioPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_cameraview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VedioPlayActivity.this, CameraStartAndShowActivity.class));
            }
        });
        findViewById(R.id.btn_vedio_recorde).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VedioPlayActivity.this, VedioRecordActivity.class));
            }
        });
        findViewById(R.id.btn_media_recorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VedioPlayActivity.this, MediaRecorderActivity.class));
            }
        });
        findViewById(R.id.btn_vedio_recorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VedioPlayActivity.this,SmallVedioActivity.class));
            }
        });

        findViewById(R.id.btn_mediaplayer_sufferview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VedioPlayActivity.this, MediaPlaySVActivity.class));
            }
        });
        findViewById(R.id.github_kaiyuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VedioPlayActivity.this, JieCaoVideoActivity.class));
            }
        });
        findViewById(R.id.btn_first_methord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "video/*");
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_vedio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VedioPlayActivity.this, VedioViewActivity.class));
            }
        });
    }

}
