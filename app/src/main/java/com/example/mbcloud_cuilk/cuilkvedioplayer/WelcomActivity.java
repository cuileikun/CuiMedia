package com.example.mbcloud_cuilk.cuilkvedioplayer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.audio.AudioTestActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.DataSaveActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.PicActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.scrollpulldown.ScrollViewPullActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.PerUtil;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.VedioPlayActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.xrecycle.XRecyclerViewActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class WelcomActivity extends AppCompatActivity {
    //需要申请的权限
    private String[] mPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    public static int PERMISSION_CODE = 1;//读写sd卡及相机权限申请code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        findViewById(R.id.btn_vedio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerUtil.checkPermission(WelcomActivity.this, mPermissions, PERMISSION_CODE, new PerUtil.permissionInterface() {
                    @Override
                    public void success() {
                        //拍照权限申请之后的操作
                        startActivity(new Intent(WelcomActivity.this, VedioPlayActivity.class));
                        ToastUtils.showShort(WelcomActivity.this, "拍照权限申请之后的操作");
                    }
                });

            }
        });
        findViewById(R.id.btn_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomActivity.this, PicActivity.class));
            }
        });
        findViewById(R.id.btn_save_read_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomActivity.this, DataSaveActivity.class));
            }
        });
        findViewById(R.id.btn_voice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomActivity.this,AudioTestActivity.class));
            }
        });
        findViewById(R.id.btn_scroll_pull).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomActivity.this,ScrollViewPullActivity.class));
            }
        });
        findViewById(R.id.btn_recycle_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomActivity.this, XRecyclerViewActivity.class));
            }
        });
    }

    private static long firstTime;

    /**
     * 连续按两次返回键退出程序
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(WelcomActivity.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
        }
        firstTime = System.currentTimeMillis();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (PerUtil.verifyPermissions(grantResults)) {
                ToastUtils.showShort(WelcomActivity.this, "拍照权限申请之后的操作");
//                startCamera();
                startActivity(new Intent(WelcomActivity.this, VedioPlayActivity.class));
            } else {
                ToastUtils.showShort(WelcomActivity.this, "拍照视频权限未开，请去设置页面打开!");
            }
        }
    }
}
