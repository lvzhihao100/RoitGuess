package com.lvzhihao.roitguess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lvzhihao.roitguess.bean.LuckyResult;
import com.lvzhihao.roitguess.layer.HomeLayer;
import com.lvzhihao.roitguess.layer.LuckyGuessLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class LuckyActivity extends Activity {

    private CCDirector director;
    public CCGLSurfaceView surfaceView;
    private PopupWindow mPopupWindow;
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    new AlertDialog.Builder(LuckyActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("别急")
                            .setMessage(
                                    "你还没出拳呢")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                    break;
                case 4:isfinish=true;
                case 1:
                    result = (LuckyResult)msg.getData().getSerializable("result");
                    popwindowShow(result);
                    break;
                case 2:
                    fightImg.setVisibility(View.GONE);
                    gameOver.setVisibility(View.GONE);
                    reword.setVisibility(View.GONE);
                    resultll.setVisibility(View.VISIBLE);
                    Message msg1=new Message();
                    msg1.what=3;
                    handler.sendMessageDelayed(msg1,5000);
                    break;
                case 3:
                    if (isfinish){
                        fightImg.setVisibility(View.GONE);
                        gameOver.setVisibility(View.VISIBLE);
                        int chapter=result.getChapter()-1;
                        BaseApplication.user.setExp(BaseApplication.user.getExp()+chapter*chapter*3);
                        reword.setText("你通关"+chapter+"层，获得"+BaseApplication.user.getExp()+"查克拉");
                        reword.setVisibility(View.VISIBLE);
                        resultll.setVisibility(View.GONE);
                        Message msg2=new Message();
                        msg2.what=5;
                        handler.sendMessageDelayed(msg2, 3000);
                    }else {
                        mPopupWindow.dismiss();
                        mPopupWindow = null;
                    }
                    break;
                case 5:
                    LuckyActivity.this.setResult(1,intent);
                    finish();
                    break;
                case 6://退出
                    int chapter = msg.getData().getInt("chapter");
                    fightImg.setVisibility(View.GONE);
                    gameOver.setVisibility(View.VISIBLE);
                    chapter--;
                    BaseApplication.user.setExp(BaseApplication.user.getExp() + chapter * chapter * 3);
                    intent.putExtra("cache",chapter*chapter*3);
                    reword.setText("你通关" + chapter + "层，获得" + chapter * chapter * 3 + "查克拉");
                    reword.setVisibility(View.VISIBLE);
                    resultll.setVisibility(View.GONE);
                    mPopupWindow = new PopupWindow(load_popunwindwow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    mPopupWindow.showAtLocation(surfaceView, Gravity.CENTER, 0, 0);
                    Message msg2=new Message();
                    msg2.what=5;
                    handler.sendMessageDelayed(msg2, 3000);
                    break;
            }
        }
    };
    private boolean isfinish=false;
    private ImageView fightImg;
    private LinearLayout resultll;
    private ImageView emyImg;
    private ImageView firstemyImg;
    private ImageView secondemyImg;
    private ImageView thirdemyImg;
    private ImageView firstmeImg;
    private ImageView secondmeImg;
    private ImageView thirdmeImg;
    private ImageView gradeImg;
    private ImageView gameOver;
    private View load_popunwindwow;
    private LuckyResult result;
    private TextView reword;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPopUp();
        intent = new Intent();
        surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);
        director = CCDirector.sharedDirector();
        director.setDisplayFPS(true);
        director.attachInView(surfaceView);
        director.setScreenSize(480,800);
        CCScene scene=CCScene.node();
        scene.addChild(new LuckyGuessLayer());
        director.runWithScene(scene);
    }

    private void initPopUp() {
        LayoutInflater mLayoutInflater = (LayoutInflater) LuckyActivity.this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        load_popunwindwow = mLayoutInflater.inflate(
                R.layout.popupwindow_fight, null);
        fightImg = (ImageView) load_popunwindwow.findViewById(R.id.fight);
        resultll = (LinearLayout) load_popunwindwow.findViewById(R.id.result);
        emyImg = (ImageView) load_popunwindwow.findViewById(R.id.emy);
        firstemyImg = (ImageView) load_popunwindwow.findViewById(R.id.first_emy);
        secondemyImg = (ImageView) load_popunwindwow.findViewById(R.id.second_emy);
        thirdemyImg = (ImageView) load_popunwindwow.findViewById(R.id.third_emy);
        firstmeImg = (ImageView) load_popunwindwow.findViewById(R.id.first_me);
        secondmeImg = (ImageView) load_popunwindwow.findViewById(R.id.second_me);
        thirdmeImg = (ImageView) load_popunwindwow.findViewById(R.id.third_me);
        gradeImg = (ImageView) load_popunwindwow.findViewById(R.id.grade);
        gameOver = (ImageView) load_popunwindwow.findViewById(R.id.gameover);
        reword = (TextView) load_popunwindwow.findViewById(R.id.reword);
    }

    private void popwindowShow(LuckyResult result) {
        if (null == mPopupWindow || !mPopupWindow.isShowing()) {

            fightImg.setVisibility(View.VISIBLE);
            resultll.setVisibility(View.GONE);
            gameOver.setVisibility(View.GONE);
            emyImg.setImageResource(result.getPic());
            firstemyImg.setImageResource(result.getFirstemy());
            secondemyImg.setImageResource(result.getSecondemy());
            thirdemyImg.setImageResource(result.getThirdemy());
            firstmeImg.setImageResource(result.getFirstme());
            secondmeImg.setImageResource(result.getSecondme());
            thirdmeImg.setImageResource(result.getThirdme());
            gradeImg.setImageResource(result.getGrade());
            mPopupWindow = new PopupWindow(load_popunwindwow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            mPopupWindow.showAtLocation(surfaceView, Gravity.CENTER, 0, 0);
            Message msg=new Message();
            msg.what=2;
            handler.sendMessageDelayed(msg, 2000);

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        director.resume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        director.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();
    }
}
