package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.litepal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

/**
 * LitePal系列文章地址：https://blog.csdn.net/guolin_blog/article/details/38461239
 */
public class LitePalActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);
        mContext = LitePalActivity.this;
        findViewById(R.id.btn_peizhi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, FirstLitePalActivity.class));
            }
        });
        findViewById(R.id.btn_use).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, LitePalTestActivity.class));
            }
        });
    }
}
