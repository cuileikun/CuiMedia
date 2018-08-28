package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.secondbutton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetSDPicPathActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ListView mShowPathLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sdpic_pathctivity);
        mShowPathLv = (ListView) findViewById(R.id.lv_show_path);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        List<String> imagePathFromSD = getImagePathFromSD();
        adapter.addAll(imagePathFromSD);
        mShowPathLv.setAdapter(adapter);
    }

    /**
     * 从sd卡获取图片资源
     *
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)

        String filePath = Environment.getExternalStorageDirectory() + File.separator + "image" + File.separator;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String s = storageDir.toString() + File.separator;
//        String filePath = Environment.getExternalStorageDirectory().toString() ;
        // 得到该路径文件夹下所有的文件 /storage/emulated/0/image/
        File fileAll = new File(s);
        File[] files = fileAll.listFiles();
        if (null != files && files.length > 0) {
            // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (checkIsImageFile(file.getPath())) {
                    imagePathList.add(file.getPath());
                }
            }
        }

        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     *
     * @param fName 文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }
}
