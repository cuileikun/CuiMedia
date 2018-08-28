package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	/*
	 * 参数1:上下文
	 * 参数2:数据库的名称
	 * 参数3:null 游标工厂
	 * 参数4:版本号 大于0的整数
	 * 
	 */
	public MySQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	/*
	 * SQLiteDatabase : SQLite的Java封装
	 * db.execuSQL(sql);
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql= "create table if not exists t_user (uid integer primary key,c_name varchar(20),c_age integer,c_phone varchar(20))";
		db.execSQL(sql);

	}
	/*
	 * 当new MySQLiteOpenHelper()的时候,如果传入的Version值比上一次的大,那么系统就回调该方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql= "alter table t_user add c_money float";
		db.execSQL(sql);
		Log.d("tag", "数据库升级了oldVersion="+oldVersion+"//newVersion="+newVersion);
	}

}
