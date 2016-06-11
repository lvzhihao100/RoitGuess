package com.lvzhihao.roitguess.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lvzhihao.roitguess.ChessActivity;
import com.lvzhihao.roitguess.util.ApplicationUtils;

import cn.jpush.android.api.JPushInterface;

public class JMessageReceiver extends BroadcastReceiver {
    private Handler handler;
    private Message message;
    private Bundle bundleSend;

    public JMessageReceiver(Handler handler) {
        this.handler=handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundleGet = intent.getExtras();
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())){
            int title = Integer.parseInt(bundleGet.getString(JPushInterface.EXTRA_TITLE));
            String content = bundleGet.getString(JPushInterface.EXTRA_MESSAGE);
            System.out.println(content);
            switch (title){
                case 0:
                    message=new Message();
                    bundleSend = new Bundle();
                    bundleSend.putString("content", content);
                    message.what=0;
                    message.setData(bundleSend);
                    handler.sendMessage(message);
                    break;
                case 1:
                    handler.sendEmptyMessage(2);
                    break;
                case 2:
                    message = new Message();
                    bundleSend = new Bundle();
                    bundleSend.putString("content", content);
                    message.what=4;
                    message.setData(bundleSend);
                    handler.sendMessage(message);
                    break;
                case 3:
                    message = new Message();
                    bundleSend = new Bundle();
                    bundleSend.putString("content", content);
                    message.what=4;
                    message.setData(bundleSend);
                    handler.sendMessage(message);
                    break;
                case 4:
                    message = new Message();
                    bundleSend = new Bundle();
                    bundleSend.putString("content", content);
                    message.what=5;
                    message.setData(bundleSend);
                    handler.sendMessage(message);
                    break;
            }

        }


    }
}