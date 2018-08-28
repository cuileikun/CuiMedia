package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.thirdbutton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Button;

import com.example.mbcloud_cuilk.cuilkvedioplayer.Constant;
import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.ListDataSave;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sp.SPUtil;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.PhotoUtils;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;
import com.example.mbcloud_cuilk.cuilkvedioplayer.pic.preview.NewPreviewActivity;
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

/**
 * @author zhengzhong on 2016/8/6 16:16
 *         Email zheng_zhong@163.com
 *         博客地址：https://blog.csdn.net/zz110753/article/details/60877594
 */
public class TakePhotoNMActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.photo)
    private CircleImageView photo;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    //        private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
//    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private File fileUri;
    //    private File fileCropUri;
    private Uri imageUri;
    private Uri cropImageUri;
    private String path;
    //SP方式保存拍照或者从相册选择的图片地址集合
    private List<String> carmePhotoListPaths = new ArrayList<>();
    private List<String> gallaryPhotoListPaths = new ArrayList<>();
    ListDataSave carmedataSave;
    ListDataSave gallarydataSave;
    private List<String> carmephotoList;//获取已保存的图片地址
    private List<String> gallaryList;//获取已保存的图片地址
    //文件方式保存

    //sd卡方式保存

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_nm);
        ViewUtils.inject(this);
        initData();
    }

    private void initData() {
        carmedataSave = new ListDataSave(TakePhotoNMActivity.this, "carme");
        gallarydataSave = new ListDataSave(TakePhotoNMActivity.this, "gallary");
        carmephotoList = carmedataSave.getDataList("carmephotoList");
        gallaryList = gallarydataSave.getDataList("gallaryphotoList");

        //获取最后一次保存的图片地址 无论是拍照获取的还是图库选择的
        String path = SPUtil.getString(TakePhotoNMActivity.this, Constant.LAST_SAVED_PHOTO_PARH, "");
        if (!TextUtils.isEmpty(path)) {
            photo.setImageBitmap(BitmapFactory.decodeFile(path));
        } else {
            ToastUtils.showShort(TakePhotoNMActivity.this, "用户还没有选择头像图片，展示默认的哦");
        }
    }

    @OnClick({R.id.takePic, R.id.takeGallery, R.id.btn_get_carme_gallary_path,R.id.btn_preview_pic})
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
            case R.id.btn_get_carme_gallary_path:
                startActivity(new Intent(TakePhotoNMActivity.this, PhotoPathActivity.class));
                break;
                case R.id.btn_preview_pic:
                    startActivity(new Intent(TakePhotoNMActivity.this,NewPreviewActivity.class));
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
                    imageUri = FileProvider.getUriForFile(TakePhotoNMActivity.this, "com.example.mbcloud_cuilk.cuilkvedioplayer.fileprovider", fileUri);
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
                            imageUri = FileProvider.getUriForFile(TakePhotoNMActivity.this, "com.example.mbcloud_cuilk.cuilkvedioplayer.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
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
                    SPUtil.putString(TakePhotoNMActivity.this, Constant.LAST_SAVED_PHOTO_PARH, path);
                    carmePhotoListPaths.add(path);
                    if (null != carmephotoList && carmephotoList.size() > 0) {
                        carmePhotoListPaths.addAll(carmephotoList);
                    }
                    carmedataSave.setDataList("carmephotoList", carmePhotoListPaths);
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
                    gallaryPhotoListPaths.add(path);
                    if (null!=gallaryList&&gallaryList.size()>0){
                        gallaryPhotoListPaths.addAll(gallaryList);
                    }
                    gallarydataSave.setDataList("gallaryphotoList", gallaryPhotoListPaths);
                    SPUtil.putString(TakePhotoNMActivity.this, Constant.LAST_SAVED_PHOTO_PARH, path);
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap);
                    }
                    break;
                default:
            }
        }
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
