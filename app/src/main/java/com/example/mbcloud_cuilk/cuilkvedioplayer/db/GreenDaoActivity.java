package com.example.mbcloud_cuilk.cuilkvedioplayer.db;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {
    private static final String TAG = "GreenDaoActivity";
    private MeiziDaoUtils mMeiziDaoUtils;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        mContext=GreenDaoActivity.this;
        mMeiziDaoUtils = new MeiziDaoUtils(this);
        //处理点击
        findViewById(R.id.insertsingle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeiziDaoUtils.insertMeizi(new Meizi(null, "Google",
                        "http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-05-18251898_1013302395468665_8734429858911748096_n.jpg"));
                ToastUtils.showShort(mContext,"插入单个数据成功");
            }
        });
        findViewById(R.id.multinsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Meizi> meiziList = new ArrayList<>();
                meiziList.add(new Meizi(null, "HuaWei",
                        "http://7xi8d648096_n.jpg"));
                meiziList.add(new Meizi(null, "Apple",
                        "http://7xi8d648096_n.jpg"));
                meiziList.add(new Meizi(null, "Apple",
                        "http://7xi8d648096_n.jpg"));
                meiziList.add(new Meizi(null, "Apple",
                        "http://7xi8d648096_n.jpg"));
                meiziList.add(new Meizi(null, "Apple",
                        "http://7xi8d648096_n.jpg"));

                meiziList.add(new Meizi(null, "MIUI",
                        "http://7xi8d648096_n.jpg"));
                mMeiziDaoUtils.insertMultMeizi(meiziList);
                ToastUtils.showShort(mContext,"批量插入数据 成功");
            }
        });
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meizi meizi = new Meizi();
                meizi.set_id(1002l);
                meizi.setUrl("http://baidu.jpg");
                mMeiziDaoUtils.updateMeizi(meizi);
                ToastUtils.showShort(mContext,"更改某条数据 成功");
            }
        });
        findViewById(R.id.deletesingle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meizi meizi1 = new Meizi();
                meizi1.set_id(1002l);
                mMeiziDaoUtils.deleteMeizi(meizi1);
                ToastUtils.showShort(mContext,"删除某条数据 成功");
            }
        });
        findViewById(R.id.deleteMult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeiziDaoUtils.deleteAll();
                ToastUtils.showShort(mContext,"批量删除所有 成功");
            }
        });
        findViewById(R.id.checksingle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meizi meizi = mMeiziDaoUtils.queryMeiziById(1003l);
                if (null != meizi) {
                    ToastUtils.showShort(mContext, mMeiziDaoUtils.queryMeiziById(1003l).toString());
                }
                ToastUtils.showShort(mContext,"查询某条数据 成功");
            }
        });
        findViewById(R.id.checkmult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Meizi> meiziList1 = mMeiziDaoUtils.queryAllMeizi();
                for (Meizi meizi2 : meiziList1) {
                    Log.i(TAG, meizi2.toString());
                }
                ToastUtils.showShort(mContext,"查询数据集合 成功");
            }
        });
        findViewById(R.id.querybuilder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String sql = "where _id > ?";
//                String[] condition = new String[]{"1008"};
//                List<Meizi> meiziList2 = mMeiziDaoUtils.queryMeiziByNativeSql(sql, condition);
//                for (Meizi meizi2 : meiziList2) {
//                    Log.i(TAG, meizi2.toString());
//                }
                List<Meizi> meiziList2 = mMeiziDaoUtils.queryMeiziByQueryBuilder(1003l);
                for (Meizi meizi2 : meiziList2) {
                    Log.i(TAG, meizi2.toString());
                }
                ToastUtils.showShort(mContext,"复合条件查询 成功,查询到的数据集合长度是:"+meiziList2.size());
            }
        });
    }
}
