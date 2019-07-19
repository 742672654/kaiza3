package com.cz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseSQL_DB extends SQLiteOpenHelper {



    private static final String TAG = "SQLiteOpenHelper";


    // TODO: 下面是默认构造方法
    public BaseSQL_DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override   // TODO: 创建数据库
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG,"数据库");
        sqLiteDatabase.execSQL(static_SQL.CREATE_TEBLE1);
        sqLiteDatabase.execSQL(static_SQL.CREATE_TEBLE2);
        sqLiteDatabase.execSQL(static_SQL.CREATE_TEBLE3);
        sqLiteDatabase.execSQL(static_SQL.CREATE_TEBLE4);
    }

    @Override   // TODO: 数据库升级时调用
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        onCreate(sqLiteDatabase);
    }

}


class static_SQL{

     static final String CREATE_TEBLE1 = "CREATE TABLE loginuser(account text,password text,ip text,login_time text,out_time text)";

     static final String CREATE_TEBLE2 = "CREATE TABLE loginlogs( id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, account TEXT, login_time TEXT, login_type integer, record TEXT)";

     static final String CREATE_TEBLE3 = "create table user( account text,password text,logontime text,qr text, role text,iscancel text,notemsg text,mobile text,cname text,nfc text," +
             "token text,authflag text, etc text,swipe text,name text,isshowepay text,comid text,state text,info text,uptime text)";

    static final String CREATE_TEBLE4 = "CREATE TABLE localorder(id text NOT NULL,uuid text,uuid2 TEXT,account text,in_time TEXT,out_time TEXT,carnumber text, PRIMARY KEY (id))";
}