package com.lvzhihao.roitguess;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lvzhihao.roitguess.DataBase.DatabaseHelper;
import com.lvzhihao.roitguess.bean.User;

import org.cocos2d.nodes.CCDirector;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by vzhihao on 2016/1/2.
 */
public class BaseApplication extends Application {

    static BaseApplication application;
    public  static User user;
    public  static DatabaseHelper mhelper;
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);

        x.Ext.setDebug(true);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        application=this;
        DatabaseHelper database = new DatabaseHelper(this);
        mhelper = database;

    }
    public static Context getApplication(){
        return application;
    }
}
