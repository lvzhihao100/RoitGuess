package com.lvzhihao.roitguess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.lvzhihao.roitguess.manager.ThreadManager;
import com.lvzhihao.roitguess.util.ApplicationUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends AppCompatActivity {

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
    @InjectView(R.id.rl_splash)
    RelativeLayout rlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        intData();
        initView();
    }

    private void initView() {

    }

    private void intData() {

        AnimationSet animationSet=new AnimationSet(true);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        RotateAnimation rotateAnimation=new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(1000);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                progressBar.setVisibility(View.VISIBLE);
                checkVersion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlSplash.setAnimation(animationSet);

    }


    private void checkVersion() {

        ThreadManager.getInstance().createLongPool().excute(new Runnable() {
            @Override
            public void run() {
                VersionInfo versionInfo = loadVersionInfo();
                if (versionInfo == null || ApplicationUtils.getVersionName().equals(versionInfo.versionName)) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    chooseDownload();
                }
            }
        });
    }

    private void chooseDownload() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.create();
        builder.setTitle("新版本").setMessage("又出新版本了，是否下载新版本？")
                .setPositiveButton("现在下载", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadNewVersion();
                    }
                })
                .setNegativeButton("下次再说", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();
                    }
                });
    }

    private void downloadNewVersion() {

    }

    private VersionInfo loadVersionInfo() {
        return null;
    }

    class VersionInfo{
        String url;
        String versionName;
    }
}
