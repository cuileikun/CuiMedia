package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FourthFileActivity extends AppCompatActivity {
    private ImageView img1, img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_file);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
    }

    //从网络获取图片直接保存
    public void SaveHttp(View view) {

        new SaveHttpImg().execute("http://p2.so.qhmsg.com/t0165453974dc3b9af7.jpg");
    }

    //从网络获取图片
    public void GetUrlImg(View view) {
        new GetImg().execute("http://p1.so.qhmsg.com/dm/365_365_/t01df531d143d2554d7.jpg");
    }

    //保存网络图片
    public void SaveUrlImg(View view) {
        new Get2Img().execute("http://p1.so.qhmsg.com/dm/365_365_/t01df531d143d2554d7.jpg");


    }
    public  class Get2Img extends AsyncTask<String, Void, Bitmap> {

        //onPreExecute在主线程中执行命令
        //进度条的初始化
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //doInBackground在子线程中执行名命令
        @Override
        protected Bitmap doInBackground(String... strings) {
            HttpURLConnection con = null;
            InputStream is = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(strings[0]);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(5 * 1000);
                /*
                *http相应200：成功
                * 404未找到
                * 500发生错误
                 */
                if (con.getResponseCode() == 200) {
                    is = con.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    return bitmap;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (con != null) {
                        con.disconnect();  //断开连接
                    }
                }
            }

            return null;
        }

        //onPostExecute在UI线程中执行命令  主线程
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img2.setImageBitmap(bitmap);
            save2Img();

        }

    }
    //从SD卡读取图片
    public void rawImg(View view) {
        String path = Environment.getExternalStorageDirectory() + "/1.png";

        //方法一  根据URI加载数据图片
//        img2.setImageURI(Uri.parse(path));
//    方法二：通过BitmapFactory的静态方法decodeFile（）
        //  参数图片路径
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        img2.setImageBitmap(bitmap);
        /*方法三：通过BitmapFactory的静态方法 decodeStream（）
       // 参数为  输入流InputStream
        try {
            BitmapFactory.decodeStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    */


    }


    //将图片保存到SD卡
    //布局监听
    public void saveImg(View view) {
        //获取ImageView中的图片
        BitmapDrawable bitmapDrawable = (BitmapDrawable)
                img1.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        String statu = Environment.getExternalStorageState();

        if (!statu.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD卡未就绪", Toast.LENGTH_SHORT).show();
            return;
        }


       /*
        通过 Bitmap(位图）压缩的方法（compress）保存图片到SD卡
        参数1：图片格式（PNG,JPEG WEBP）
        参数2：图片质量（0-100）
        参数3：输出流
         */
        File root = Environment.getExternalStorageDirectory();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(root + "/1.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    //网络存储图片
    public void save2Img() {
        //获取ImageView中的图片
        BitmapDrawable bitmapDrawable = (BitmapDrawable)
                img2.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        String statu = Environment.getExternalStorageState();

        if (!statu.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD卡未就绪", Toast.LENGTH_SHORT).show();
            return;
        }


       /*
        通过 Bitmap(位图）压缩的方法（compress）保存图片到SD卡
        参数1：图片格式（PNG,JPEG WEBP）
        参数2：图片质量（0-100）
        参数3：输出流
         */
        File root = Environment.getExternalStorageDirectory();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(root + "/1.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public class GetImg extends AsyncTask<String, Void, Bitmap> {

        //onPreExecute在主线程中执行命令
        //进度条的初始化
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //doInBackground在子线程中执行名命令
        @Override
        protected Bitmap doInBackground(String... strings) {
            HttpURLConnection con = null;
            InputStream is = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(strings[0]);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(5 * 1000);
                /*
                *http相应200：成功
                * 404未找到
                * 500发生错误
                 */
                if (con.getResponseCode() == 200) {
                    is = con.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                    return bitmap;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (con != null) {
                        con.disconnect();  //断开连接
                    }
                }
            }

            return null;
        }

        //onPostExecute在UI线程中执行命令  主线程
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img2.setImageBitmap(bitmap);


        }

    }



    public class SaveHttpImg extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection con = null;
            InputStream is = null;
            try {
                URL url = new URL(strings[0]);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5*1000);
                con.setReadTimeout(5*1000);
                File root = Environment.getExternalStorageDirectory();
                FileOutputStream fos = new FileOutputStream(root+"/http.jpg");
                if(con.getResponseCode()==200){
                    is = con.getInputStream();
                    int next=0;
                    byte[] bytes = new byte[1024];
                    while ( (next = is.read(bytes))>0){
                        fos.write(bytes,0,next);
                    }
                    fos.flush();
                    fos.close();
                    return  root+"/http.jpg";
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(is!=null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(con!=null){
                    con.disconnect();
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.equals("")){
                Toast.makeText(FourthFileActivity.this,"保存路径:"+s,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(FourthFileActivity.this,"保存失败:",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
