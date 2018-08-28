package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.file;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InnerFileStringActivity extends AppCompatActivity {
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_file);
        Button save = (Button) findViewById(R.id.save);
        Button read = (Button) findViewById(R.id.read);
        Button delete = (Button) findViewById(R.id.delete);

        final TextView show = (TextView) findViewById(R.id.show);
        content = (EditText) findViewById(R.id.content);
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

    //保存文件内存储
    public void saveFile() {
//    当文件被保存在内部存储中时，默认情况下，文件是应用程序私有的，其他应用不能访问。当用户卸载应用程序时这些文件也跟着被删除。
//　　文件默认存储位置：/data/data/包名/files/文件名。
        FileOutputStream fos = null;
        /*MODE_APPEND 追加 MODE_PRIVATE  覆盖
        OpenFileoutput返回一个 输出字节流
        指向的路径为data/data/包名/file/
        参数1.文件名称（如果不存在则自动创建）
        参数2.模式MODE_APPEND 文件内容追加
        MODE_PRIVATE  文件内容被覆盖
        */
        try {
            fos = openFileOutput("text.txt", MODE_APPEND);

            String str = content.getText().toString();

            try {
                fos.write(str.getBytes());
                Toast.makeText(InnerFileStringActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //从内存读文件
    public String readFile() {
        StringBuilder sbd = new StringBuilder();
        BufferedReader reader = null;

        FileInputStream fis = null;
        try {
            fis = openFileInput("text.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
            try {
                sbd.append(getFilesDir().getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String row = "";


            try {
                while ((row = reader.readLine()) != null) {
                    sbd.append(row);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(getBaseContext(), "文件不存在", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return sbd.toString();
    }

    //删除文件
    public void removeFile() {
        String[] files = fileList();
        for (String str : files) {
            // Log.d("=====",str);

            if (str.equals("text.txt")) {
                deleteFile("text.txt");
            }

        }

    }

}
