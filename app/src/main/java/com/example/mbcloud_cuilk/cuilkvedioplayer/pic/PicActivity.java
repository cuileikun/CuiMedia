package com.example.mbcloud_cuilk.cuilkvedioplayer.pic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.fifth.SDTakeReadPhotoActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.fifth.SQliteTakeReadPhotoActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.firstbutton.CapturingFhotoActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.fourthbutton.SavePhotoSDActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.secondbutton.GetSDPicPathActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.thirdbutton.TakePhotoNMActivity;

public class PicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
      findViewById(R.id.btn_pic_m).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(PicActivity.this,CapturingFhotoActivity.class));
          }
      });
      findViewById(R.id.btn_get_picpath).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(PicActivity.this,GetSDPicPathActivity.class));
          }
      });
        findViewById(R.id.btn_get_pic_nm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PicActivity.this,TakePhotoNMActivity.class));
            }
        });
        findViewById(R.id.btn_get_pic_nm2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PicActivity.this,SavePhotoSDActivity.class));
            }
        });
        findViewById(R.id.btn_preview_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PicActivity.this, SDTakeReadPhotoActivity.class));
            }
        });
        findViewById(R.id.btn_sqlite_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PicActivity.this, SQliteTakeReadPhotoActivity.class));
            }
        });
    }


}
