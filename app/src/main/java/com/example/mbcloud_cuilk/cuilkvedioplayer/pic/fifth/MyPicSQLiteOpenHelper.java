package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.fifth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mbcloud-cuilk on 2018/5/14.
 */

public class MyPicSQLiteOpenHelper extends SQLiteOpenHelper {
    /*
     * 参数1:上下文
	 * 参数2:数据库的名称
	 * 参数3:null 游标工厂
	 * 参数4:版本号 大于0的整数
	 *
	 */
    public MyPicSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /*
     * SQLiteDatabase : SQLite的Java封装
     * db.execuSQL(sql);
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists pic_data (uid integer primary key,pic_time number,pic_last_path varchar(90))";
        db.execSQL(sql);

    }

    /*
     * 当new MySQLiteOpenHelper()的时候,如果传入的Version值比上一次的大,那么系统就回调该方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "alter table pic_data add c_name varchar(20)";
        db.execSQL(sql);
        Log.d("tag", "数据库升级了oldVersion=" + oldVersion + "//newVersion=" + newVersion);
    }
}
