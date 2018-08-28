package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.Constant;
import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;

public class SPSaveNormalDataActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spsave_normal_data);
        mContext = SPSaveNormalDataActivity.this;
    }

    public void saveString(View view) {
        SPUtil.putString(mContext, Constant.SP_STRING, "字符串");
    }

    public void getString(View view) {
        String string = SPUtil.getString(mContext, Constant.SP_STRING, "");
        ToastUtils.showShort(mContext, "我是获取到的：" + string);
    }

    public void saveFloat(View view) {
        SPUtil.putFloat(mContext, Constant.SP_FLOAT, 5.2f);
    }

    public void getFloat(View view) {
        float aFloat = SPUtil.getFloat(mContext, Constant.SP_FLOAT, 0.0f);
        ToastUtils.showShort(mContext, "我是获取到的：" + aFloat);
    }

    public void saveInt(View view) {
        SPUtil.putInt(mContext, Constant.SP_INT, 121);
    }

    public void getInt(View view) {
        int anInt = SPUtil.getInt(mContext, Constant.SP_INT, 0);
        ToastUtils.showShort(mContext, "我是获取到的：" + anInt);
    }

    public void saveLong(View view) {
        SPUtil.putLong(mContext, Constant.SP_LONG, 473947535);
    }

    public void getLong(View view) {
        long aLong = SPUtil.getLong(mContext, Constant.SP_LONG, 0);
        ToastUtils.showShort(mContext, "我是获取到的：" + aLong);
    }

    public void saveBoolean(View view) {
        SPUtil.putBool(mContext, Constant.SP_BOOLEAN, true);
    }

    public void getBoolean(View view) {
        boolean bool = SPUtil.getBool(mContext, Constant.SP_BOOLEAN, false);
        ToastUtils.showShort(mContext, "我是获取到的：" + bool);
    }
}
