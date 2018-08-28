package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHeloer extends SQLiteOpenHelper {

	/**
	 * 
	 * @param context
	 * name 数据库的名字
	 * factory  游标工厂  cursor
	 * version 版本号 版本号必须大于等于1 
	 */
	
	
	public MyOpenHeloer(Context context) {
		super(context, "Account.db", null, 1);
		
		
	}

	/**
	 * Called when the database is created for the first time
	 * 当数据库第一次创建的时候 调用  
	 * 那么这个方法特别适合做表结构的初始化 
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),money varchar(20))");
		db.execSQL("insert into info(name,money) values(?,?)", new String[]{"张三","5000"});
		db.execSQL("insert into info(name,money) values(?,?)", new String[]{"李四","3000"});
		
	}

	//当数据库版本升级的时候调用  适合做表结构的更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
