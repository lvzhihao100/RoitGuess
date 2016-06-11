package com.lvzhihao.roitguess;

import android.view.animation.Animation;

/**
 * Created by vzhihao on 2016/4/10.
 */
public abstract class MyAnimationListener implements Animation.AnimationListener {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        onEnd(animation);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    abstract void onEnd(Animation animation);
}
