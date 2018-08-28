package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.Constant;
import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;
import com.google.gson.Gson;

public class SPActivity extends AppCompatActivity {
    byte[] bytes = new byte[]{-83, 5, 74, 5, 12, 104, 88, -99, 118};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        findViewById(R.id.btn_save_list_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SPActivity.this, SPSaveListActivity.class));
            }
        });

        findViewById(R.id.btn_save_narmal_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SPActivity.this, SPSaveNormalDataActivity.class));
            }
        });
        findViewById(R.id.btn_save_javabean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建sp对象,如果有key为"SP_PEOPLE"的sp就取出，否则就创建一个此key的sp对象
                SharedPreferences sp = getSharedPreferences("SP_JAVA_BEAN", Activity.MODE_PRIVATE);
                Userbean people = new Userbean();//创建javabean对象
                people.setAge(1);
                people.setName("小邵");
                Gson gson = new Gson();
                String jsonStr = gson.toJson(people); //将对象转换成Json
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("KEY_PEOPLE_DATA", jsonStr); //存入json串
                editor.commit(); //提交
                ToastUtils.showShort(SPActivity.this, "您已经保存成功");
            }
        });
        findViewById(R.id.btn_get_javabean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("SP_JAVA_BEAN", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
                String peopleJson = sp.getString("KEY_PEOPLE_DATA", "");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
                if (peopleJson != "")  //防空判断
                {
                    Gson gson = new Gson();
                    Userbean people = gson.fromJson(peopleJson, Userbean.class); //将json字符串转换成 people对象
                    ToastUtils.showShort(SPActivity.this, "获取保存的javabean对象是：" + people.getName() + people.getAge());
                }

            }
        });
        findViewById(R.id.btn_save_bytearray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("demo", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String imageString = new String(Base64.encode(bytes, Base64.DEFAULT));
                editor.putString(Constant.BYTE_ARRAY, imageString);
                editor.commit();
            }
        });
        findViewById(R.id.btn_get_bytearray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("demo", Activity.MODE_PRIVATE);
                String string = sharedPreferences.getString(Constant.BYTE_ARRAY, "");
                byte[] bytes = Base64.decode(string.getBytes(), Base64.DEFAULT);
                ToastUtils.showShort(SPActivity.this,"获取到的byte数组是："+bytes.toString());
            }
        });

    }
}
