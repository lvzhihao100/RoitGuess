package com.lvzhihao.roitguess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvzhihao.roitguess.bean.ChessArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vzhihao on 2016/4/10.
 */
public class PopFight {
    ChessArray chessMe;
    ChessArray chessEmy;
    Context context;

    private RelativeLayout fight_popupwindow;
    private ImageView emyCharacter;
    private ImageView meCharacter;
    private ImageView emySkill;
    private ImageView meSkill;
    private PopupWindow mPopupWindowFight;
    private Animation animEmyChara;
    private Animation animMeChara;
    private Animation animEmySkill;
    private Animation animMeSkill;
    private Animation animEmySkillFaild;
    private Animation animMeSkillFaild;
    private Animation animEmySkillWin;
    private Animation animMeSkillWin;
    private ChessArray chessDown;
    private ChessArray chessUp;
    int result;
    private ImageView lostImage;
    private ImageView winImage;
    private Timer timer;
    private TimerTask timerTask;
    private ImageView pingImage;
    private TextView rewordText;
    private int flag;

    public PopFight(Context context) {
        this.context = context;
    }


    public void init() {
        LayoutInflater mLayoutInflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        fight_popupwindow = (RelativeLayout) mLayoutInflater.inflate(
                R.layout.popupwindow_chess, null);
        winImage = (ImageView) fight_popupwindow.findViewById(R.id.win);
        lostImage = (ImageView) fight_popupwindow.findViewById(R.id.lost);
        pingImage = (ImageView) fight_popupwindow.findViewById(R.id.ping);
        rewordText = (TextView) fight_popupwindow.findViewById(R.id.reword);
        winImage.setVisibility(ImageView.GONE);
        lostImage.setVisibility(ImageView.GONE);
        pingImage.setVisibility(ImageView.GONE);

        emyCharacter = new ImageView(context);
        meCharacter = new ImageView(context);
        fight_popupwindow.addView(emyCharacter);
        fight_popupwindow.addView(meCharacter);
        emySkill = new ImageView(context);
        meSkill = new ImageView(context);
        fight_popupwindow.addView(emySkill);
        fight_popupwindow.addView(meSkill);
        mPopupWindowFight = new PopupWindow(fight_popupwindow, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        animEmyChara = loadAnimationById(R.anim.character_fight_emy);
        animMeChara = loadAnimationById(R.anim.character_fight_me);
        animMeChara.setFillAfter(true);
        animEmyChara.setFillAfter(true);
        animEmySkill = loadAnimationById(R.anim.skill_fight_emy);
        animMeSkill = loadAnimationById(R.anim.skill_fight_me);
        animEmySkillFaild = loadAnimationById(R.anim.skill_faild_fight_emy);
        animMeSkillFaild = loadAnimationById(R.anim.skill_faild_fight_me);
        animEmySkillWin = loadAnimationById(R.anim.skill_win_fight_emy);
        animEmySkillWin.setFillAfter(false);
        animMeSkillWin = loadAnimationById(R.anim.skill_win_fight_me);
        animMeSkillWin.setFillAfter(false);

    }


    public int doFight(ChessArray chessMe, ChessArray chessEmy) {
        chessDown=chessMe;
        chessUp=chessEmy;
        chessDown.getImageView().setBackgroundColor(Color.YELLOW);
        chessUp.getImageView().setBackgroundColor(Color.YELLOW);
        result=fight(chessMe.getWeapon(), chessEmy.getWeapon());
        emyCharacter.setImageBitmap(getBitmap(chessEmy, 0));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(((ChessActivity) context).laywidth / 5, ((ChessActivity) context).layheight / 6);
        RelativeLayout.LayoutParams layoutParamsSkill = new RelativeLayout.LayoutParams(((ChessActivity) context).laywidth / 20, ((ChessActivity) context).layheight / 10);
        emyCharacter.setLayoutParams(layoutParams);
        emySkill.setImageBitmap(getBitmap(chessEmy, 1));
        emySkill.setLayoutParams(layoutParamsSkill);
        meCharacter.setImageBitmap(getBitmap(chessMe, 0));
        meCharacter.setLayoutParams(layoutParams);
        meSkill.setImageBitmap(getBitmap(chessMe, 1));
        meSkill.setLayoutParams(layoutParamsSkill);
        animShow(emyCharacter, emySkill, meCharacter, meSkill, result);
        return result;
    }

    private void animShow(ImageView emyCharacter, final ImageView emySkill, ImageView meCharacter, final ImageView meSkill, int fight) {

        emyCharacter.startAnimation(animEmyChara);
        meCharacter.startAnimation(animMeChara);
        animEmyChara.setAnimationListener(new MyAnimationListener() {
            @Override
            void onEnd(Animation animation) {
                emySkill.startAnimation(animEmySkill);
            }
        });
        animMeChara.setAnimationListener(new MyAnimationListener() {
            @Override
            void onEnd(Animation animation) {
                meSkill.startAnimation(animMeSkill);
            }
        });
        switch (fight) {
            case 0:
                animEmySkill.setAnimationListener(new MyAnimationListener() {
                    @Override
                    void onEnd(Animation animation) {
                        emySkill.startAnimation(animEmySkillWin);
                        meSkill.startAnimation(animMeSkillWin);
                        animEmySkillWin.setAnimationListener(new MyAnimationListener() {
                            @Override
                            void onEnd(Animation animation) {
                                onFightOver();

                            }
                        });
                    }
                });
                break;
            case 1:
                animEmySkill.setAnimationListener(new MyAnimationListener() {
                    @Override
                    void onEnd(Animation animation) {
                        emySkill.startAnimation(animEmySkillFaild);
                        meSkill.startAnimation(animMeSkillWin);
                        animMeSkillWin.setAnimationListener(new MyAnimationListener() {
                            @Override
                            void onEnd(Animation animation) {
                                onFightOver();
                            }
                        });
                    }
                });
                break;
            case 2:
                animEmySkill.setAnimationListener(new MyAnimationListener() {
                    @Override
                    void onEnd(Animation animation) {
                        emySkill.startAnimation(animEmySkillWin);
                        meSkill.startAnimation(animMeSkillFaild);
                        animEmySkillWin.setAnimationListener(new MyAnimationListener() {
                            @Override
                            void onEnd(Animation animation) {
                                onFightOver();
                            }
                        });
                    }
                });
                break;
        }

        mPopupWindowFight.showAtLocation(((ChessActivity) context).root, Gravity.CENTER, 0, 0);
    }

    private void onFightOver() {
        if (result==0){
            pingImage.setVisibility(View.VISIBLE);
        }
        if (result==1){
            winImage.setVisibility(View.VISIBLE);
            ((ChessActivity)context).emyChesses.remove(chessUp);

        }if (result==2){
            lostImage.setVisibility(View.VISIBLE);
            ((ChessActivity)context).meChesses.remove(chessDown);

        }
        emySkill.setVisibility(View.GONE);
        meSkill.setVisibility(View.GONE);
        timer = new Timer();
        flag = 0;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (flag == 0 && (((ChessActivity) context).meChesses.size() == 0 || ((ChessActivity) context).emyChesses.size() == 0)) {
                    flag++;
                    ((ChessActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            winImage.setVisibility(View.GONE);
                            lostImage.setVisibility(View.GONE);
                            pingImage.setVisibility(View.GONE);
                            emySkill.setVisibility(View.GONE);
                            meSkill.setVisibility(View.GONE);
                            if (((ChessActivity) context).meChesses.size() == 0) {
                                rewordText.setText("输了，没得到奖励！下次加油");
                            } else {
                                BaseApplication.user.setMoney(BaseApplication.user.getMoney() + 500);
                                rewordText.setText("赢了，获得500金币");
                            }
                            rewordText.setVisibility(View.VISIBLE);
                            chessUp.getImageView().setBackgroundColor(Color.TRANSPARENT);
                            chessDown.getImageView().setBackgroundColor(Color.TRANSPARENT);


                        }
                    });
                    SystemClock.sleep(1000);
                }
                    timer.cancel();
                    timer = null;
                    timerTask.cancel();
                    timerTask = null;
                    ((ChessActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            winImage.setVisibility(View.GONE);
                            lostImage.setVisibility(View.GONE);
                            pingImage.setVisibility(View.GONE);
                            emySkill.setVisibility(View.VISIBLE);
                            meSkill.setVisibility(View.VISIBLE);
                            chessUp.getImageView().setBackgroundColor(Color.TRANSPARENT);
                            chessDown.getImageView().setBackgroundColor(Color.TRANSPARENT);
                            mPopupWindowFight.dismiss();
                        }
                    });
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("downRow", ((ChessActivity) context).getImageRow(chessDown.getImageView()));
                    bundle.putInt("downColumn", ((ChessActivity) context).getImageColumn(chessDown.getImageView()));
                    bundle.putInt("upRow", ((ChessActivity) context).getImageRow(chessUp.getImageView()));
                    bundle.putInt("upColumn", ((ChessActivity) context).getImageColumn(chessUp.getImageView()));
                    bundle.putInt("result", result);
                    message.setData(bundle);
                    if (flag != 0) {
                        message.what = 7;
                    } else {
                        message.what = 1;
                    }
                    ((ChessActivity) context).handler.sendMessage(message);
                }

        };
        timer.schedule(timerTask, 1000);

    }

    private Animation loadAnimationById(int animId) {
        Animation animation = AnimationUtils.loadAnimation(context, animId);
        animation.setDuration(1000);
        return animation;
    }


    private int fight(int weaponAtack, int weaponDefend) {
        if (weaponAtack == weaponDefend) {
            return 0;
        } else if (weaponAtack - weaponDefend == 1 || weaponAtack - weaponDefend == -2) {
            return 1;
        }
        return 2;
    }


    private Bitmap getBitmap(ChessArray chess, int i) {
        InputStream open = null;
        try {

            if (i == 0) {

                open = context.getAssets().open(chess.getCharacter());
                return BitmapFactory.decodeStream(open);
            } else {
                open = context.getAssets().open(chess.getSkill());
                return BitmapFactory.decodeStream(open);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != open) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
