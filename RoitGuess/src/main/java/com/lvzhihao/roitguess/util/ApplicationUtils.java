package com.lvzhihao.roitguess.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.lvzhihao.roitguess.BaseApplication;

/**
 * Created by vzhihao on 2016/1/2.
 */
public class ApplicationUtils {
    public static String getVersionName(){
        PackageManager manager=getContext().getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getContext().getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static Context getContext(){
        return  BaseApplication.getApplication();
    }
}
