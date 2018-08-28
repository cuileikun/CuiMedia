package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.file.FileSaveActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sp.SPActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sqlite.SqliteActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.db.GreenDaoActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.litepal.LitePalActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.PerUtil;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;

import java.io.File;

public class DataSaveActivity extends AppCompatActivity {
    //需要申请的权限
    private String[] mSDPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    public static int PERMISSION_CODE = 1;//读写sd卡及相机权限申请code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataSaveActivity.this, SPActivity.class));
            }
        });
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerUtil.checkPermission(DataSaveActivity.this, mSDPermissions, PERMISSION_CODE, new PerUtil.permissionInterface() {
                    @Override
                    public void success() {
                        //sd卡权限申请成功之后的操作
                        ToastUtils.showShort(DataSaveActivity.this, "sd卡权限申请成功之后的操作");
                        startActivity(new Intent(DataSaveActivity.this, FileSaveActivity.class));
                    }
                });

            }
        });
        findViewById(R.id.btn_database).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataSaveActivity.this, SqliteActivity.class));
            }
        });
        findViewById(R.id.btn_greendao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataSaveActivity.this,GreenDaoActivity.class));
            }
        });
        findViewById(R.id.btn_litepal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataSaveActivity.this,LitePalActivity.class));
            }
        });
        TextView mTvInnerPath = (TextView) findViewById(R.id.tv_inner_path);
        TextView mTvInnerPath2 = (TextView) findViewById(R.id.tv_inner_path2);

        TextView mTvsdPath = (TextView) findViewById(R.id.tv_sd_path);
        TextView mTvsdPath2 = (TextView) findViewById(R.id.tv_sd_path2);

        //getCacheDir 内存储用于获取/data/data/cache目录，缓存目录，当存储空间不足，系统会自动将之清除。
        File file = new File(getCacheDir(), "info.txt");
        //:  /data/user/0/com.example.mbcloud_cuilk.cuilkvedioplayer/cache/info.txt
        String absolutePath = file.getAbsolutePath();
        mTvInnerPath.setText("getCacheDir():" + "/data/user/0/com.example.mbcloud_cuilk.cuilkvedioplayer/cache/info.txt");

        //getFilesDir 内存储用于获取/data/data/files目录,保存重要的数据信息
        File file2 = new File(getFilesDir(), "info.txt");
        //: /data/user/0/com.example.mbcloud_cuilk.cuilkvedioplayer/files/info.txt
        String absolutePath2 = file2.getAbsolutePath();
        mTvInnerPath2.setText("getFilesDir:" + "/data/user/0/com.example.mbcloud_cuilk.cuilkvedioplayer/files/info.txt");

        //存储在sd卡 mnt目录或者storage目录，具体路径跟手机厂商有关 mnt/sdcard/info.txt
        //sd 如果 API 版本大于或等于8 时
        File file3 = new File(getExternalFilesDir(null), "fanrunqi.jpg");
        //:  /storage/emulated/0/Android/data/com.example.mbcloud_cuilk.cuilkvedioplayer/files/fanrunqi.jpg
        String absolutePath3 = file2.getAbsolutePath();

        mTvsdPath.setText("如果 API 版本大于或等于8 时 getExternalFilesDir：" + "/storage/emulated/0/Android/data/com.example.mbcloud_cuilk.cuilkvedioplayer/files/fanrunqi.jpg");
        //sd  如果API 版本小于 8 （7或者更低）
        File file4 = new File(Environment.getExternalStorageDirectory(), "a.txt");
        //:  /storage/emulated/0/a.txt
        String absolutePath4 = file4.getAbsolutePath();
        mTvsdPath2.setText(" 如果API 版本小于 8 （7或者更低）时 Environment.getExternalStorageDirectory()" + "/storage/emulated/0/a.txt");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (PerUtil.verifyPermissions(grantResults)) {
                ToastUtils.showShort(DataSaveActivity.this, "sd卡权限申请成功之后的操作");
                startActivity(new Intent(DataSaveActivity.this, FileSaveActivity.class));
            } else {
                ToastUtils.showShort(DataSaveActivity.this, "读写SD卡或者相机权限未开，请去设置页面打开!");
            }
        }
    }
}
