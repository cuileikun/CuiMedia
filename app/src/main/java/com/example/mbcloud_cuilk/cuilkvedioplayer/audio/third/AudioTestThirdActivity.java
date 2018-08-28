package com.example.mbcloud_cuilk.cuilkvedioplayer.audio.third;

import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.audio.AudioTestActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.audio.first.AudioTestFirstActivity;

import java.io.File;
import java.io.IOException;

public class AudioTestThirdActivity extends AppCompatActivity implements View.OnClickListener {
    // 开始录音
    private Button start;
    // 停止按钮
    private Button stop;
    // 播放按钮
    private Button paly;
    // 暂停播放
    private Button pause_paly;
    // 停止播放
    private Button stop_paly;

    // 录音类
    private MediaRecorder mediaRecorder;
    // 以文件的形式保存
    private File recordFile;

    private RecordPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_test_third);
        recordFile = new File("/mnt/sdcard", "kk.amr");

        initView();
        Listener();
    }
    private void initView() {
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        paly = (Button) findViewById(R.id.paly);
        pause_paly = (Button) findViewById(R.id.pause_paly);
        stop_paly = (Button) findViewById(R.id.stop_paly);
    }

    private void Listener() {
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        paly.setOnClickListener(this);
        pause_paly.setOnClickListener(this);
        stop_paly.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        player = new RecordPlayer(AudioTestThirdActivity.this);
        int Id = v.getId();

        switch (Id) {
            case R.id.start:
                startRecording();
                break;
            case R.id.stop:
                stopRecording();
                break;
            case R.id.paly:
                playRecording();
                break;
            case R.id.pause_paly:
                pauseplayer();
                break;
            case R.id.stop_paly:
                stopplayer();
                break;
        }
    }


    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        // 判断，若当前文件已存在，则删除
        if (recordFile.exists()) {
            recordFile.delete();
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(recordFile.getAbsolutePath());

        try {
            // 准备好开始录音
            mediaRecorder.prepare();

            mediaRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void stopRecording() {
        if (recordFile != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
        }
    }

    private void playRecording() {
        player.playRecordFile(recordFile);
    }


    private void pauseplayer() {
        player.pausePalyer();
    }

    private void stopplayer() {
        // TODO Auto-generated method stub
        player.stopPalyer();
    }
}
