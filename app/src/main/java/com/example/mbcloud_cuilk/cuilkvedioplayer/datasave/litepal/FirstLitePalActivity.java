package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.litepal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

public class FirstLitePalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_lite_pal);
        TextView mTv1 = (TextView) findViewById(R.id.tv1);
        TextView mTv2 = (TextView) findViewById(R.id.tv2);
        TextView mTv3 = (TextView) findViewById(R.id.tv3);
        TextView mTvCompare = (TextView) findViewById(R.id.tv_compare);
        mTv1.setText("1-->Android Studio,在build.gradle中添加如下配置：compile 'org.litepal.android:core:1.6.0'。" +
                "Eclipse用户的话直接把最新jar包放到libs目录下即可");
        mTv2.setText("2-->配置litepal.xml");
        mTv3.setText("3-->配置LitePalApplication");
        mTvCompare.setText("LitePal相比传统MySqlte的优点："+"A：配置简单\n" +
                "LitePal  1. AS直接gradle添加。Eclipse引入Jar包或源码 2. 在项目的assets目录下面新建一个litepal.xml文件 3. 配置LitePalApplication\n" +
                "MySqlte  需要自己建一个MySQLiteHelper类并让它继承SQLiteOpenHelper，需要手写建表语句 容易出错"+"B：数据库易于维护（表增加表跟字段，删除无用的列）\n" +
                "LitePal  不需要去编写任何与升级相关的逻辑，也不需要关心程序是从哪个版本升级过来的，唯一要做的就是确定好最新的Model结构是什么样的，\n" +
                "然后将litepal.xml中的版本号加1，所有的升级逻辑就都会自动完成了。\n" +
                "\n" +
                "其实LitePal并没有删除任何一列，它只是先将comment表重命名成一个临时表，然后根据最新的Comment类的结构生成一个新的comment表，再把临时表中\n" +
                "除了publishdate之外的数据复制到新的表中，最后把临时表删掉。因此，看上去的效果好像是做到了删除列的功能。\n" +
                "\n" +
                "MySqlte  \n" +
                "需要在MySQLiteHelper 的onUpgrade方法中，为了兼容之前的版本，需要根据老的数据库版本oldVersion进行一些处理，保证如果用户是从旧版本\n" +
                "升级过来的，就会新增一个comment表，而如果用户是直接安装的新版本，就会在onCreate()方法中把两个表一起创建了\n" +
                "\n" +
                "SQLite并不支持删除列的功能"+"C：数据库表的关联\n" +
                "MySqlte：即一对一关联的实现方式是用外键，多对一关联的实现方式也是用外键，多对多关联的实现方式是用中间表。\n" +
                "SQLite：接着Comment和News是多对一的关系，因此News中应该包含多个Comment，而Comment中应该只有一个News，所以就可以这样写：\n" +
                "public class News {  \n" +
                "    ...  \n" +
                "    private Introduction introduction;  \n" +
                "      \n" +
                "    private List<Comment> commentList = new ArrayList<Comment>();  \n" +
                "      \n" +
                "    // 自动生成get、set方法  \n" +
                "}  \n" +
                "先使用一个泛型为Comment的List集合来表示News中包含多个Comment，然后修改Comment类的代码，如下所示：\n" +
                "public class Comment {  \n" +
                "    ...  \n" +
                "    private News news;  \n" +
                "      \n" +
                "    // 自动生成get、set方法   \n" +
                "}  \n" +
                "在Comment类中声明了一个News的实例，这样就清楚地表示出了News中可以包含多个Comment，而Comment中只能有一个News，也就是多对一的关系了。");

    }
}
