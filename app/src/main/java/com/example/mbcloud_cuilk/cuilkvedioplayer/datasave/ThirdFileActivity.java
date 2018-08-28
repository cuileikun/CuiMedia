package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ThirdFileActivity extends AppCompatActivity {
    private Button raw, assets;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_file);
        raw = (Button) findViewById(R.id.raw);
        assets = (Button) findViewById(R.id.assets);
        show = (TextView) findViewById(R.id.show);

        raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setText(readRaw());
            }
        });
        assets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setText(readAssets());
            }
        });

    }

    //读取assets目录
    public String readAssets() {
        StringBuilder sbd = new StringBuilder();
        BufferedReader reader = null;
        InputStream is = null;
        try {
            is = getResources().getAssets().open("cityinfo");
            reader = new BufferedReader(new InputStreamReader(is));
            String row = "";
            while ((row = reader.readLine()) != null) {
                sbd.append(row);
                sbd.append("\n");


            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return sbd.toString();
    }


    //读取raw文件夹
    public String readRaw() {
        StringBuilder sbd = new StringBuilder();
        BufferedReader reader = null;
        InputStream is = null;
        is = getResources().openRawResource(R.raw.settings);
        reader = new BufferedReader(new InputStreamReader(is));
        String row = "";
        try {
            while ((row = reader.readLine()) != null) {
                sbd.append(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return sbd.toString();

    }
}
