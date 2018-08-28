package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.file;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SDFileStringActivity extends AppCompatActivity {
    private Button save,read,delete;
    private EditText content;
    private TextView show;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_file);
        verifyStoragePermissions(this);
        save= (Button) findViewById(R.id.save);
        read= (Button) findViewById(R.id.read);
        delete= (Button) findViewById(R.id.delete);
        content= (EditText) findViewById(R.id.content);
        show= (TextView) findViewById(R.id.show);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile();
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setText(readFile());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFile();
            }
        });
    }
    //保存文件到SD卡
    public  void saveFile(){
        FileOutputStream fos=null;
        //获取SD卡状态
        String state= Environment.getExternalStorageState();
        //判断SD卡是否就绪
        if(!state.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this,"请检查SD卡",Toast.LENGTH_SHORT).show();
            return;
        }
        //取得SD卡根目录
        File file= Environment.getExternalStorageDirectory();

        try {
            Log.d("=====SD卡根目录：",file.getCanonicalPath().toString());
//            File myFile=new File(file.getCanonicalPath()+"/sd.txt");
//            fos=new FileOutputStream(myFile);
            //输出流的构造参数1可以是 File对象  也可以是文件路径
            //输出流的构造参数2：默认为False=>覆盖内容；ture=》追加内容
            //追加  ，ture
            fos=new FileOutputStream(file.getCanonicalPath()+"/sd.txt",true);
            String  str=content.getText().toString();
            fos.write(str.getBytes());
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //从SD卡读取文件
    public String  readFile(){

        BufferedReader reader=null;

        FileInputStream fis=null;
        StringBuilder sbd=new StringBuilder();

        String statu=Environment.getExternalStorageState();
        if (!statu.equals(Environment.MEDIA_MOUNTED)){

            Toast.makeText(this,"SD卡未就绪",Toast.LENGTH_SHORT).show();
            return  "";
        }
        File root=Environment.getExternalStorageDirectory();
        try {
            fis=new FileInputStream(root+"/sd.txt");
            reader= new BufferedReader(new InputStreamReader(fis));
            String row="";
            try {
                while ((row=reader.readLine())!=null){

                    sbd.append(row);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this,"文件不存在",Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  sbd.toString();
    }

    //删除SD卡文件
    public void removeFile(){
        String statu= Environment.getExternalStorageState();

        if (!statu.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this,"SD卡未就绪",Toast.LENGTH_SHORT).show();
            return;
        }
        File root=Environment.getExternalStorageDirectory();
        // File sd=new File(root,"sd.txt");
        File sd=new File(root+"/sd.txt");
        if(sd.exists()){

            sd.delete();
            Toast.makeText(this,"文件删除成功",Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(this,"文件不存在",Toast.LENGTH_SHORT).show();
        }

    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
