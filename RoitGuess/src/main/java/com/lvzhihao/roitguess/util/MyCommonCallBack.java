package com.lvzhihao.roitguess.util;

import org.xutils.common.Callback;

/**
 * Created by vzhihao on 2016/4/16.
 */
public abstract class MyCommonCallBack implements  Callback.CommonCallback<String> {

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
