package com.lvzhihao.roitguess.util;

import android.content.Context;
import android.os.Message;

import com.lvzhihao.roitguess.BaseApplication;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by vzhihao on 2016/3/3.
 */
public class JPushUtils {
    public  static int setNickname(String nickname){
        final int[] result = {-1};
        JPushInterface.setAlias(BaseApplication.getApplication(),nickname, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                result[0] =i;
            }
        });
        return result[0];
    }
}
