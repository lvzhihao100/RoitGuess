package com.lvzhihao.roitguess;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvzhihao.roitguess.DataBase.Dao;
import com.lvzhihao.roitguess.DataBase.UserDaoImpl;
import com.lvzhihao.roitguess.manager.ThreadManager;
import com.lvzhihao.roitguess.util.HttpUtils;
import com.lvzhihao.roitguess.view.DrawableTextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vzhihao on 2016/3/22.
 */
public class HomeActivity extends Activity {

    private RelativeLayout root;
    private int laywidth;
    private int layheight;
    int cacheExp;
    private ProgressBar progress;
    private PopupWindow myimgpopupwindow;
    private PopShop popShop;
    private PopWhareHouse popWhareHouse;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    updataData();
                    break;
                case 1:
                    changeExp();
                    break;
            }

        }
    };
    private DrawableTextView nameText;
    private DrawableTextView coinText;
    private TextView levelText;
    private MediaPlayer mediaPlayer;

    private void changeExp() {
        if (cacheExp > 0) {
           cacheExp--;
            if (progress.getProgress() < progress.getMax()) {
                progress.setProgress(progress.getProgress() + 1);
            } else if (progress.getProgress() == progress.getMax()) {
                BaseApplication.user.setLevel(BaseApplication.user.getLevel() + 1);
                progress.setMax(BaseApplication.user.getLevel() * 100);
                progress.setProgress(1);
                levelText.setText("等级"+BaseApplication.user.getLevel());
            }
        }else if (timer!=null&&timerTask!=null){
            timer.cancel();
            timer=null;
            timerTask.cancel();
            timerTask=null;
        }
    }

    ;
    private Timer timer;
    private TimerTask timerTask;

    private void updataData() {

        coinText.setText(BaseApplication.user.getMoney()+"");
        if (popWhareHouse.isShowing()) {
            popWhareHouse.updataView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        root = (RelativeLayout) findViewById(R.id.root);
        root.post(new Runnable() {
            @Override
            public void run() {
                laywidth = root.getMeasuredWidth();
                layheight = root.getMeasuredHeight();
                initImage();
                popShop = new PopShop(HomeActivity.this, root, layheight * 19 / 84);
                popWhareHouse = new PopWhareHouse(HomeActivity.this, root, layheight * 13 / 42);
            }
        });

    }

    private void initImage() {
        progress = (ProgressBar) root.findViewById(R.id.progressBar);
        nameText = (DrawableTextView) root.findViewById(R.id.name);
        coinText = (DrawableTextView)root. findViewById(R.id.coin);
        levelText = (TextView) root.findViewById(R.id.level);
        chessEnter();
        chessOnlineEnter();
        luckyEnter();
        wharehouseEnter();
        shopEnter();
        mediaPlayer = MediaPlayer.create(this, R.raw.state_login);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        initData();
    }
    public  void initData(){
        progress.setMax(BaseApplication.user.getLevel() * 100);
        int level=BaseApplication.user.getLevel()-1;
        progress.setProgress(BaseApplication.user.getExp() - level * (level + 1) / 2 * 100);
        nameText.setText(BaseApplication.user.getName());
        coinText.setText(BaseApplication.user.getMoney() + "");
        levelText.setText("等级" + BaseApplication.user.getLevel());
    }

    private void wharehouseEnter() {
        ImageView whareInto = new ImageView(this);
        initPosition(whareInto, R.mipmap.box, laywidth / 50, layheight / 7);
        whareInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWhareHouse.toggle();
            }
        });
        root.addView(whareInto);
    }

    public void shopEnter() {
        ImageView shopInto = new ImageView(this);
        initPosition(shopInto, R.mipmap.shophome, laywidth * 3 / 4, layheight / 7);
        shopInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popShop.toggle();
            }
        });
        root.addView(shopInto);

    }

    private void luckyEnter() {
        ImageView luckyInto = new ImageView(this);
        int left = 0;
        int top = 0;
        initPosition(luckyInto, R.mipmap.luckyhome, laywidth * 18 / 50, layheight * 7 / 20);
        luckyInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(HomeActivity.this, LuckyActivity.class), 0);
            }
        });
        root.addView(luckyInto);

    }

    private void chessOnlineEnter() {
        ImageView chessonlineInto = new ImageView(this);
        initPosition(chessonlineInto, R.mipmap.onlinehome, laywidth * 17 / 30, layheight * 11 / 20);
        chessonlineInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChessActivity.class);
                intent.putExtra("online", true);
                startActivityForResult(intent, 2);
            }
        });
        root.addView(chessonlineInto);
    }

    private void chessEnter() {
        ImageView chessInto = new ImageView(this);
        int left = 0;
        int top = 0;
        initPosition(chessInto, R.mipmap.chesshome, laywidth / 10, layheight * 11 / 20);
        chessInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChessActivity.class);
                intent.putExtra("online", false);
                startActivityForResult(intent, 1);
            }
        });
        root.addView(chessInto);
    }

    private void initPosition(ImageView imageView, int resourceId, int left, int top) {
        imageView.setImageResource(resourceId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams layoutParams = null;
        if (resourceId == R.mipmap.box) {
            layoutParams = new RelativeLayout.LayoutParams(laywidth / 4, layheight / 6);
        } else if (resourceId == R.mipmap.shophome) {
            layoutParams = new RelativeLayout.LayoutParams(laywidth / 4, layheight / 12);
        } else
            layoutParams = new RelativeLayout.LayoutParams(laywidth / 3, layheight / 4);

        layoutParams.setMargins(left, top, 0, 0);
        imageView.setLayoutParams(layoutParams);
    }

    public void showExp(View v) {

    }

    public void exit(View v) {
        mediaPlayer.stop();
        mediaPlayer.release();
        System.exit(0);
       // finish();

    }
    public void help(View v) {
startActivity(new Intent(this,HelpActivity.class));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HttpUtils.updataLocal();
        switch (resultCode){
            case 1:
                updataData();
            cacheExp=data.getIntExtra("cache",0);
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            };
            timer.schedule(timerTask, 1000, 20);
                break;
            case 2:
                coinText.setText(BaseApplication.user.getMoney()+"");
                break;
        }

    }
}
