package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.ListDataSave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * SharedPreferences保存List和对象序列化数据
 * https://blog.csdn.net/jxf_access/article/details/61199519
 */
public class SPSaveListActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    ListDataSave dataSave;
    private ArrayList<Userbean> listBean;
    private ArrayList<String> listString;
    private ArrayList<Map<String, Object>> listMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spsave_list);
        initView();
    }
    private void initView() {
        findViewById(R.id.btn_addString).setOnClickListener(this);
        findViewById(R.id.btn_addJavaBean).setOnClickListener(this);
        findViewById(R.id.btn_addMap).setOnClickListener(this);
        findViewById(R.id.btn_getString).setOnClickListener(this);
        findViewById(R.id.btn_getJavaBean).setOnClickListener(this);
        findViewById(R.id.btn_getMap).setOnClickListener(this);
        mContext = getApplicationContext();
        dataSave = new ListDataSave(mContext, "baiyu");
        listString = new ArrayList<String>();
        listBean = new ArrayList<Userbean>();
        listMap = new ArrayList<Map<String, Object>>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addString:
                String s = "小名";
                listString.add(s);
                dataSave.setDataList("string", listString);

                break;
            case R.id.btn_getString:
                Toast.makeText(mContext, dataSave.getDataList("string").toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_addJavaBean:
                Userbean user = new Userbean();
                user.setName("小白");
                user.setAge(16);
                listBean.add(user);
                dataSave.setDataList("javaBean", listBean);
                break;
            case R.id.btn_getJavaBean:

                Toast.makeText(mContext, dataSave.getDataList("javaBean").toString(), Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_addMap:
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("name","大白");
                map.put("age", 18);
                listMap.add(map);
                dataSave.setDataList("listMap", listMap);
                break;
            case R.id.btn_getMap:
                Toast.makeText(mContext, dataSave.getDataList("listMap").toString(), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
