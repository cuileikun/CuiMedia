package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

import java.util.ArrayList;

public class SqliteActivity extends Activity {
    private static final int VERSION = 1;
    private static final String DB_NAME = "my_user.db";
    private TextView tv_users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        tv_users = (TextView) findViewById(R.id.tv_users);
    }
    /**
     * 创建数据库
     *
     * @param view
     */
    public void createDatabase(View view) {
        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.close();
        openHelper.close();
    }

    /*
     * 1. 获取SQLiteDatabase对象db 2. db.execSQL(sql,args)
     */
    public void insert1(View view) {
        // 获取SQLiteDatabase
        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
        // 获取db
        SQLiteDatabase database = openHelper.getWritableDatabase();
        // 执行sql语句
        String sql = "insert into t_user(c_name,c_age,c_phone)values(?,?,?)";
        for (int i = 0; i < 50; i++) {
            database.execSQL(sql, new Object[] { "zhangsan" + i, 20+i, "" + (5550 + i) });
        }
        // 释放资源
        database.close();
        Toast.makeText(this, "执行完毕", Toast.LENGTH_LONG).show();
    }

    /*
     * 删除数据
     */
    public void delete1(View view) {
        // 获取SQLiteDatabase
        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
        // 获取db
        SQLiteDatabase database = openHelper.getWritableDatabase();
        // 执行sql语句
        String sql = "delete from t_user where c_age<?";
        database.execSQL(sql, new Object[] { 40 });
        // 释放资源
        database.close();
        Toast.makeText(this, "执行完毕", Toast.LENGTH_LONG).show();
    }

    public void update1(View view) {
        // 获取SQLiteDatabase
        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
        // 获取db
        SQLiteDatabase database = openHelper.getWritableDatabase();
        // 执行sql语句
        String sql = "update t_user set c_name=? where c_age>?";
        database.execSQL(sql, new Object[] { "lisi", 50 });
        // 释放资源
        database.close();
        Toast.makeText(this, "执行完毕", Toast.LENGTH_LONG).show();
    }

    /*
     * 查询数据
     */
    public void query1(View view) {
        ArrayList<User> users = new ArrayList<User>();
        if (users.size()>0){
            users.clear();
        }
        // 获取SQLiteDatabase
        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
        // 获取db
        SQLiteDatabase database = openHelper.getWritableDatabase();
        // 执行纯sql查询
        String sql = "select c_name,c_age,c_phone from t_user where c_age>=?";
        Cursor cursor = database.rawQuery(sql, new String[] { "30" });
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int age = cursor.getInt(1);
            String phone = cursor.getString(2);
            users.add(new User(name, age, phone));
        }
        cursor.close();
        database.close();
        showData(users);
    }

    /*
     * 第二种方式插入数据
     */
    public void insert2(View view) {
//        // 获取SQLiteDatabase
//        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
//        // 获取db
//        SQLiteDatabase database = openHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("c_name", "wangwu");
//        values.put("c_age", 60);
//        values.put("c_phone", 6500 + "");
//        // 新插入数据的uid
//        long insert = database.insert("t_user", null, values);
//        database.close();
//        Toast.makeText(this, "insert=" + insert, Toast.LENGTH_SHORT).show();

    }

    /*
     * 第二种方式删除数据
     */
    public void delete2(View view) {
//        // 获取SQLiteDatabase
//        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
//        // 获取db
//        SQLiteDatabase database = openHelper.getWritableDatabase();
//        // String sql = "delete from t_user where c_age<?";
//        int deleted = database.delete("t_user", "c_age<?", new String[] { "50" });
//        database.close();
//        Toast.makeText(this, "deleted=" + deleted, Toast.LENGTH_SHORT).show();
    }

    /*
     * 第二种方式修改数据
     */
    public void update2(View view) {
//        // 获取SQLiteDatabase
//        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
//        // 获取db
//        SQLiteDatabase database = openHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        // String sql = "update t_user set c_name=? where c_age>?";
//        values.put("c_name", "zhaoliu");
//        int updated = database.update("t_user", values, "c_age>?", new String[] { "55" });
//        database.close();
//        Toast.makeText(this, "修改了=" + updated, Toast.LENGTH_SHORT).show();
    }

    /*
     * 第二种方式查询数据
     */
    public void query2(View view) {
//        ArrayList<User> users = new ArrayList<User>();
//        // 获取SQLiteDatabase
//        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
//        // 获取db
//        SQLiteDatabase database = openHelper.getWritableDatabase();
////		String sql = "select c_name,c_age,c_phone from t_user where c_age>? order by c_age desc";
//		/*
//		 * 参数1:表名
//		 * 参数2:要查询的字段
//		 * 参数3:where表达式
//		 * 参数4:替换?号的真实值
//		 * 参数5:分组 null
//		 * 参数6:having表达式null
//		 * 参数7:排序规则 c_age desc
//		 */
//        Cursor cursor = database.query("t_user", new String[]{"c_name","c_age","c_phone"}, "c_age>?", new String[]{"51"}, null, null, "c_age desc");
//        while(cursor.moveToNext()){
//            String name = cursor.getString(0);
//            int age = cursor.getInt(1);
//            String phone = cursor.getString(2);
//            users.add(new User(name,age,phone));
//        }
//        cursor.close();
//        database.close();
//        showData(users);
    }
    /*
     * 每人分1000元
     */
    public void fenqian(View view){
//        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
//        SQLiteDatabase database = openHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("c_money", 1000);
//        database.update("t_user", values , null, null);
//        openHelper.close();
//        Toast.makeText(this, "每人分了1000元", Toast.LENGTH_SHORT).show();
    }
    /*
     * 模拟转账
     */
    public void exchange(View view){
//        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
//        SQLiteDatabase database = openHelper.getWritableDatabase();
//        //开启事务
//        database.beginTransaction();
//        String sql= "update t_user set c_money = c_money-500 where c_name=?";
//        //扣除lisi 500元
//        database.execSQL(sql,new String[]{"lisi"});
//
//        //遇到异常了
//        int a = 1/0;
//
//        //给zhaoliu 添加500元
//        String sql2= "update t_user set c_money = c_money+500 where c_name=?";
//        database.execSQL(sql2, new String[]{"zhaoliu"});
//        //提交事务
//        database.setTransactionSuccessful();
//        //结束事务
//        database.endTransaction();
//        //释放资源
//        database.close();
    }
    /*
     * 对比执行效率
     */
    public void compare(View view){
        MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(this, DB_NAME, null, VERSION);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        String sql = "insert into t_user(c_name,c_age,c_phone)values(?,?,?)";
        //获取开始时间的毫秒值
        long startTime = SystemClock.currentThreadTimeMillis();
        for(int i=0;i<100;i++){
            database.execSQL(sql,new Object[]{"wangwei"+i,10+i,""+(1000+i)});
        }
        Log.d("tag", "普通方法耗时:"+(SystemClock.currentThreadTimeMillis()-startTime));

        startTime = SystemClock.currentThreadTimeMillis();
        database.beginTransaction();
        for(int i=0;i<100;i++){
            database.execSQL(sql,new Object[]{"wangwei"+i,10+i,""+(1000+i)});
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        Log.d("tag", "使用事务方法耗时:"+(SystemClock.currentThreadTimeMillis()-startTime));
        database.close();
    }

    // 显示数据到界面
    private void showData(ArrayList<User> users) {
        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            sb.append(u.toString() + "\n\n");
        }
        tv_users.setText(sb.toString()+"集合长度:"+users.size());
    }
}
