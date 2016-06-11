package com.lvzhihao.roitguess.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lvzhihao.roitguess.BaseApplication;
import com.lvzhihao.roitguess.DataBase.Dao;
import com.lvzhihao.roitguess.DataBase.UserDaoImpl;
import com.lvzhihao.roitguess.manager.ThreadManager;

/**
 * Created by vzhihao on 2016/3/19.
 */
public class HttpUtils {
   /// 192.168.191.4
          //  100.67.169.253
    public final static String DOMAIN="http://100.66.18.78:8080";
    public final static String LOGIN_PATH="/GoitGuess/LoginServlet";
    public final static String REGISTER_PATH="/GoitGuess/RegisterServlet";
    public final static String CHESS_PATH="/GoitGuess/DeliverServlet";
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mWiFiNetworkInfo != null&&mWiFiNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void updataLocal(){
        ThreadManager.getInstance().createLongPool().excute(new Runnable() {
            @Override
            public void run() {
                Dao dao=new UserDaoImpl(BaseApplication.mhelper);
                dao.updata(BaseApplication.user);
            }
        });
    };

}
