package com.lvzhihao.roitguess.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vzhihao on 2016/4/18.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "user.db"; //数据库名称
    private static final int version = 1; //数据库版本

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table user(level int not null ," +
                "exp int not null,"+
                "blood int not null,"+
                "starNum int not null,"+
                "swordNum int not null,"+
                "bowNum int not null,"+
                "smallbowNum int not null,"+
                "heartNum int not null,"+
                "samllheartNum int not null,"+
                "shieldNum int not null,"+
                "name varchar(20) not null,"+
                "money int not null,"+
                "photo int not null)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
