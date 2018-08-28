package com.example.mbcloud_cuilk.cuilkvedioplayer.audio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.audio.first.AudioTestFirstActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.audio.second.AudioTestSecondActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.audio.third.AudioTestThirdActivity;

public class AudioTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_test);
        findViewById(R.id.btn_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AudioTestActivity.this,AudioTestFirstActivity.class));
            }
        });
        findViewById(R.id.btn_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AudioTestActivity.this,AudioTestSecondActivity.class));
            }
        });
        findViewById(R.id.btn_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AudioTestActivity.this,AudioTestThirdActivity.class));
            }
        });
    }
}
