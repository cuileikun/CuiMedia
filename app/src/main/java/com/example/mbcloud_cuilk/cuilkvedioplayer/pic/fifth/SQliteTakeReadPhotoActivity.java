package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.fifth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.PhotoUtils;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.AndroidUtil;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SQliteTakeReadPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.photo)
    private CircleImageView photo;
    @ViewInject(R.id.tv_pic_num)
    private TextView mTvPicNum;
    @ViewInject(R.id.tv_pic_time)
    private TextView mTvPicTime;
    @ViewInject(R.id.lv_pic_msg)
    private ListView mListView;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri;
    private Uri imageUri;
    private Uri cropImageUri;
    private String path;
    private Context mContext;
    private int mGridViewItmeWith;//GridView控件中每个itme图片的大小
    private static final int VERSION = 1;
    private static final String DB_NAME = "my_pic.db";
    private long time;
    private String sqlpath;
    private List<PicBean> mPicBeanList;
    private PicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdtake_read_photo);
        mContext = SQliteTakeReadPhotoActivity.this;
        createDatabase();
        ViewUtils.inject(this);
        initData();
    }

    /**
     * 创建数据库
     */
    private void createDatabase() {
        MyPicSQLiteOpenHelper openHelper = new MyPicSQLiteOpenHelper(this, DB_NAME, null, VERSION);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.close();
        openHelper.close();
    }

    private void initData() {
        mGridViewItmeWith = AndroidUtil.getScreenWidth(mContext) / 4;
        if (null == mPicBeanList) {
            mPicBeanList = new ArrayList<>();
        }
        ArrayList<PicBean> picList = new ArrayList<PicBean>();
        if (picList.size() > 0) {
            picList.clear();
        }
        // 获取SQLiteDatabase
        MyPicSQLiteOpenHelper openHelper = new MyPicSQLiteOpenHelper(this, DB_NAME, null, VERSION);
        // 获取db
        SQLiteDatabase database = openHelper.getWritableDatabase();
        // 执行纯sql查询
//        String sql = "select c_time from cpic_path where c_age>=?";
        String sql = "select pic_time,pic_last_path from pic_data where uid>=?";
        Cursor cursor = database.rawQuery(sql, new String[]{"0"});
        while (cursor.moveToNext()) {
            time = cursor.getLong(0);
            sqlpath = cursor.getString(1);
            PicBean bean = new PicBean();
            bean.setName("图片名字");
            bean.setTime(time);
            bean.setPath(sqlpath);
            picList.add(bean);
        }
        cursor.close();
        database.close();
        if (!TextUtils.isEmpty(sqlpath)) {
            photo.setImageBitmap(BitmapFactory.decodeFile(sqlpath));
        } else {
            ToastUtils.showShort(SQliteTakeReadPhotoActivity.this, "用户还没有选择头像图片，展示默认的哦");
        }
        if (null != picList && picList.size() > 0) {
            mTvPicNum.setText("图片数量是：" + picList.size() + "张");
        } else {
            mTvPicNum.setText("默认图片");
        }
        String newtime = String.valueOf(time);
        if (!TextUtils.isEmpty(newtime) && !newtime.equals("0")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = formatter.format(new Date(time));
            mTvPicTime.setText("最后一次图片拍摄时间是：" + format + "====最后一次图片路径是 ：" + sqlpath);
        }
        if (null != picList && picList.size() > 0) {
            mPicBeanList.addAll(picList);
        }
        adapter = new PicAdapter(mContext, mPicBeanList);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void querySqliteData() {
        ArrayList<PicBean> picList = new ArrayList<PicBean>();
        if (picList.size() > 0) {
            picList.clear();
        }
        // 获取SQLiteDatabase
        MyPicSQLiteOpenHelper openHelper = new MyPicSQLiteOpenHelper(this, DB_NAME, null, VERSION);
        // 获取db
        SQLiteDatabase database = openHelper.getWritableDatabase();
        // 执行纯sql查询
//        String sql = "select c_time from cpic_path where c_age>=?";
        String sql = "select pic_time,pic_last_path from pic_data where uid>=?";
        Cursor cursor = database.rawQuery(sql, new String[]{"0"});
        while (cursor.moveToNext()) {
            time = cursor.getLong(0);
            sqlpath = cursor.getString(1);
            PicBean bean = new PicBean();
            bean.setName("图片名字");
            bean.setTime(time);
            bean.setPath(sqlpath);
            picList.add(bean);
        }
        cursor.close();
        database.close();
        ToastUtils.showShort(SQliteTakeReadPhotoActivity.this, "图片张数是：" + picList.size());
        if (null != picList && picList.size() > 0) {
            mTvPicNum.setText("图片数量是：" + picList.size() + "张");
        } else {
            mTvPicNum.setText("默认图片");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(new Date(time));
        mTvPicTime.setText("最后一次图片拍摄时间是：" + format + "====最后一次图片路径是 ：" + sqlpath);
        if (mPicBeanList.size() > 0) {
            mPicBeanList.clear();
        }
        if (null != picList && picList.size() > 0) {
            mPicBeanList.addAll(picList);
        }
//        adapter = new PicAdapter(mContext, mPicBeanList);
//        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.takePic, R.id.takeGallery})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePic:
                try {
                    fileUri = createImageFile();//创建临时图片文件
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                autoObtainCameraPermission();
                break;
            case R.id.takeGallery:
                try {
                    fileUri = createImageFile();//创建临时图片文件
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                autoObtainStoragePermission();
                break;
            default:
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        //:storageDir=/storage/emulated/0/Android/data/com.example.mbcloud_cuilk.cuilkvedioplayer/files/Pictures
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //创建临时文件,文件前缀不能少于三个字符,后缀如果为空默认未".tmp"
        // image=/storage/emulated/0/Android/data/com.example.mbcloud_cuilk.cuilkvedioplayer/files/Pictures/JPEG_20180511_140957_-2030147177.jpg
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                storageDir      /* 文件夹 */
        );

        return image;
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(SQliteTakeReadPhotoActivity.this, "com.example.mbcloud_cuilk.cuilkvedioplayer.fileprovider", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(this, "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(SQliteTakeReadPhotoActivity.this, "com.example.mbcloud_cuilk.cuilkvedioplayer.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(this, "请允许打开相机！！");
                }
                break;
            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(this, "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileUri);
                    //add by clk 已经选择的图片的路径
                    path = cropImageUri.getPath();
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.example.mbcloud_cuilk.cuilkvedioplayer.fileprovider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                    //add by clk 已经选择的图片的路径
                    path = cropImageUri.getPath();
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap);
                    }
                    //把图片地址存储到数据库
                    insertData(new Date().getTime(), path);
                    querySqliteData();
                    break;
                default:
            }
        }
    }

    private void insertData(long time, String path) {
        // 获取SQLiteDatabase
        MyPicSQLiteOpenHelper openHelper = new MyPicSQLiteOpenHelper(this, DB_NAME, null, VERSION);
        // 获取db
        SQLiteDatabase database = openHelper.getWritableDatabase();
        // 执行sql语句
        String sql = "insert into pic_data(pic_time,pic_last_path)values(?,?)";
        database.execSQL(sql, new Object[]{time, path});
        // 释放资源
        database.close();
        Toast.makeText(this, "执行完毕", Toast.LENGTH_LONG).show();
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    private void showImages(Bitmap bitmap) {
        photo.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
