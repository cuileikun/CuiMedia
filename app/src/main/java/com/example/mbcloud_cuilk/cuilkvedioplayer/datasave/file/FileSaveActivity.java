package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.file;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.FourthFileActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.ThirdFileActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class FileSaveActivity extends AppCompatActivity implements View.OnClickListener{
    private Button output_loacl;
    private Button input_loacl;
    private Button output_sdcard;
    private Button input_sdcard;
    private Button output_sdcard_list;
    private Button input_sdcard_list;
    private TestClass bean;
    private List<TestClass> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_save);
        findViewById(R.id.btn_inner_save_string).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FileSaveActivity.this,InnerFileStringActivity.class));
            }
        });
        findViewById(R.id.btn_sd_save_string).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FileSaveActivity.this,SDFileStringActivity.class));
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FileSaveActivity.this,ThirdFileActivity.class));
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FileSaveActivity.this,FourthFileActivity.class));
            }
        });
        init();
    }
    /**
     * 初始化
     */
    private void init() {
        output_loacl = (Button) findViewById(R.id.output_loacl);
        input_loacl = (Button) findViewById(R.id.input_loacl);
        output_sdcard = (Button) findViewById(R.id.output_sdcard);
        input_sdcard = (Button) findViewById(R.id.input_sdcard);
        output_sdcard_list = (Button) findViewById(R.id.output_sdcard_list);
        input_sdcard_list = (Button) findViewById(R.id.input_sdcard_list);

        output_loacl.setOnClickListener(this);
        input_loacl.setOnClickListener(this);
        output_sdcard.setOnClickListener(this);
        input_sdcard.setOnClickListener(this);
        output_sdcard_list.setOnClickListener(this);
        input_sdcard_list.setOnClickListener(this);

        bean = new TestClass("华少", "男");

        list = new ArrayList<TestClass>();
        list.add(new TestClass("小米", "女"));
        list.add(bean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.output_loacl:
                writeIntoLocal(bean);
                break;
            case R.id.input_loacl:
                readFormLoacl();
                break;
            case R.id.output_sdcard:
                writeIntoSDcard(bean);
                break;
            case R.id.input_sdcard:
                readFromSDcard();
                break;
            case R.id.output_sdcard_list:
                writeListIntoSDcard(list);
                break;
            case R.id.input_sdcard_list:
                readListFromSDcard();
                break;
            default:
                break;
        }
    }
    /**
     * write into local
     * @param bean
     */
    private void writeIntoLocal(TestClass bean){
        if (new OutputUtil<TestClass>().writeObjectIntoLocal(FileSaveActivity.this, "test.out", bean)) {
            Toast.makeText(FileSaveActivity.this, "写入本地成功", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(FileSaveActivity.this, "写入本地失败", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * rean from loacl
     */
    private void readFormLoacl(){
        TestClass bean1 = new InputUtil<TestClass>().readObjectFromLocal(FileSaveActivity.this, "test.out");
        if (bean1 == null) {
            Toast.makeText(FileSaveActivity.this, "本地读取失败", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(FileSaveActivity.this, "本地读取成功"+bean1.getName()+"-"+bean1.getGender(), Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * write into sdcard
     * @param bean
     */
    private void writeIntoSDcard(TestClass bean){
        if (new OutputUtil<TestClass>().writObjectIntoSDcard("test.out", bean)) {
            Toast.makeText(FileSaveActivity.this, "写入SD卡成功", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(FileSaveActivity.this, "写入SD卡失败", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * rean from sdcard
     */
    private void readFromSDcard(){
        TestClass bean1 = new InputUtil<TestClass>().readObjectFromSdCard("test.out");
        if (bean1 == null) {
            Toast.makeText(FileSaveActivity.this, "SD卡读取失败", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(FileSaveActivity.this, "SD卡读取成功"+bean1.getName()+"-"+bean1.getGender(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * write into sdcard (object)
     * @param list
     */
    private void writeListIntoSDcard(List<TestClass> list){
        if (new OutputUtil<TestClass>().writeListIntoSDcard("testlist.out", list)) {
            Toast.makeText(FileSaveActivity.this, "写入SD卡成功", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(FileSaveActivity.this, "写入SD卡失败", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * rean from sdcard (集合)
     */
    private void readListFromSDcard(){
        List<TestClass> list = new InputUtil<TestClass>().readListFromSdCard("testlist.out");
        if (list == null) {
            ToastUtils.showShort(FileSaveActivity.this,"SD卡读取失败");
        }
        else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).getName());
                sb.append(list.get(i).getGender());
            }
            ToastUtils.showShort(FileSaveActivity.this,"集合大小是"+list.size()+"===="+"SD卡读取成功"+sb);
        }
    }

}
