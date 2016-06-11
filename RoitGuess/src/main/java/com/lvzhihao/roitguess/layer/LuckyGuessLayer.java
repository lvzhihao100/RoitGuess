package com.lvzhihao.roitguess.layer;

import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;

import com.lvzhihao.roitguess.BaseApplication;
import com.lvzhihao.roitguess.LuckyActivity;
import com.lvzhihao.roitguess.bean.LuckyData;
import com.lvzhihao.roitguess.bean.LuckyResult;
import com.lvzhihao.roitguess.sprite.AreaSprite;
import com.lvzhihao.roitguess.sprite.BaseSprite;
import com.lvzhihao.roitguess.sprite.BlackDeskSprite;
import com.lvzhihao.roitguess.sprite.BloodSprite;
import com.lvzhihao.roitguess.sprite.BoPaperSprite;
import com.lvzhihao.roitguess.sprite.BoRockSprite;
import com.lvzhihao.roitguess.sprite.BoScissorsSprite;
import com.lvzhihao.roitguess.sprite.ButtonGreenSprite;
import com.lvzhihao.roitguess.sprite.ErrorSprite;
import com.lvzhihao.roitguess.sprite.ExitSprite;
import com.lvzhihao.roitguess.sprite.FightSprite;
import com.lvzhihao.roitguess.sprite.LuckyBgSprite;
import com.lvzhihao.roitguess.sprite.PaperSprite;
import com.lvzhihao.roitguess.sprite.PropBgSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.BasePropSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.BowSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.HeartSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.NormalSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.ShieldSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.SmallBowSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.SmallHeartSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.StarSprite;
import com.lvzhihao.roitguess.sprite.PropSprite.SwordSprite;
import com.lvzhihao.roitguess.sprite.RockManSprite;
import com.lvzhihao.roitguess.sprite.ScissorsSprite;
import com.lvzhihao.roitguess.sprite.SpriteBg;
import com.lvzhihao.roitguess.sprite.SpriteBloodBlank;
import com.lvzhihao.roitguess.sprite.RockSprite;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzhihao on 2016/3/9.
 */
public class LuckyGuessLayer extends BaseLayer {

    public static final int CLICK_ROCK = 1;
    public static final int CLICK_SCISSORS = 2;
    public static final int CLICK_PAPER = 3;
    public static final int CLICK_BOW = 4;
    public static final int CLICK_SMALL_BOW = 5;
    public static final int CLICK_HEART = 6;
    public static final int CLICK_SMALL_HEART = 7;
    public static final int CLICK_SWORD = 8;
    public static final int CLICK_STAR= 9;
    public static final int CLICK_SHIELD= 10;

    public static final int STATE_ROCK = 1;
    public static final int STATE_SCISSORS = 2;
    public static final int STATE_PAPER = 3;

    public static final int PROP_NORMAL= 0;
    public static final int PROP_SHIELD = 1;
    public static final int PROP_SWORD = 2;
    public static final int PROP_STAR = 3;
    public int propState=0;
    public int error1State = 0;
    public int error2State = 0;
    public int error3State = 0;

    private int clickstatus = 0;
    private BaseSprite clickedSprite;
    private CGPoint clickedPoint;

    List<BaseSprite> sprites;
    private RockSprite rockSpritefly;
    private PaperSprite paperSpritefly;
    private ScissorsSprite scissorsSpritefly;
    private PaperSprite paperSprite;
    private RockSprite rockSprite;
    private ScissorsSprite scissorsSprite;
    private ErrorSprite errorSprite1;
    private ErrorSprite errorSprite2;
    private ErrorSprite errorSprite3;
    public SpriteBloodBlank bloodBlankEmy;
    public SpriteBloodBlank bloodBlankme;
    private SpriteBg spriteBg;
    public NormalSprite normalSprite;
    public LuckyData luckyData;
    private RockManSprite rockManSprite;

    private BasePropSprite selectedProp;
    public void init() {
        clickedSprite=null;
        clickedPoint=null;
        luckyData = new LuckyData();
        props=new ArrayList<BaseSprite>();
        sprites = new ArrayList<BaseSprite>();
        LuckyBgSprite luckyBg = new LuckyBgSprite();
        sprites.add(luckyBg);
        initRockMan();
        AreaSprite areaSprite = new AreaSprite();
        sprites.add(areaSprite);
        bloodBlankEmy = new SpriteBloodBlank();
        sprites.add(bloodBlankEmy);

        PropBgSprite propBgSprite = new PropBgSprite();
        sprites.add(propBgSprite);

        ButtonGreenSprite buttonGreenSpriterock = new ButtonGreenSprite();
        ButtonGreenSprite buttonGreenSpritepaper = new ButtonGreenSprite();
        ButtonGreenSprite buttonGreenSpritescissors = new ButtonGreenSprite();
        buttonGreenSpriterock.setPosition(10, propBgSprite.getSprite().getBoundingBox().size.height + 10);
        float btGreenWidth = buttonGreenSpritepaper.getSprite().getBoundingBox().size.width;
        buttonGreenSpritescissors.setPosition(10 + btGreenWidth, propBgSprite.getSprite().getBoundingBox().size.height + 10);
        buttonGreenSpritepaper.setPosition(10 + btGreenWidth * 2, propBgSprite.getSprite().getBoundingBox().size.height + 10);
        sprites.add(buttonGreenSpritepaper);
        sprites.add(buttonGreenSpriterock);
        sprites.add(buttonGreenSpritescissors);

        FightSprite fightSprite = new FightSprite();
        fightSprite.setPosition(10 + btGreenWidth * 3, propBgSprite.getSprite().getBoundingBox().size.height + 10);
        sprites.add(fightSprite);

        BlackDeskSprite blackDeskSprite = new BlackDeskSprite();
        blackDeskSprite.setPosition(10, 10 + btGreenWidth + propBgSprite.getSprite().getBoundingBox().size.height);
        sprites.add(blackDeskSprite);

        ExitSprite exitSprite = new ExitSprite();
        exitSprite.setPosition(10 + btGreenWidth * 3, 10 + btGreenWidth + propBgSprite.getSprite().getBoundingBox().size.height);
        sprites.add(exitSprite);

        bloodBlankme = new SpriteBloodBlank();
        bloodBlankme.setPosition(20, winsize.height / 2 - winsize.height / 50);
        sprites.add(bloodBlankme);

        paperSprite = new PaperSprite();
        rockSprite = new RockSprite();
        scissorsSprite = new ScissorsSprite();
        paperSpritefly = new PaperSprite();
        rockSpritefly = new RockSprite();
        scissorsSpritefly = new ScissorsSprite();
        rockSprite.setPosition(10, propBgSprite.getSprite().getBoundingBox().size.height + 10);
        rockSpritefly.setPosition(rockSprite.getSprite().getPosition());
        scissorsSprite.setPosition(10 + btGreenWidth, propBgSprite.getSprite().getBoundingBox().size.height + 10);
        scissorsSpritefly.setPosition(scissorsSprite.getSprite().getPosition());
        paperSprite.setPosition(10 + btGreenWidth * 2, propBgSprite.getSprite().getBoundingBox().size.height + 10);
        paperSpritefly.setPosition(paperSprite.getSprite().getPosition());
        sprites.add(paperSprite);
        sprites.add(rockSprite);
        sprites.add(scissorsSprite);
        errorSprite1 = new ErrorSprite();
        errorSprite1.setPosition(blackDeskSprite.getSprite().getPosition().x + 10, blackDeskSprite.getSprite().getPosition().y);
        errorSprite2 = new ErrorSprite();
        errorSprite2.setPosition(20 + btGreenWidth, errorSprite1.getSprite().getPosition().y);
        errorSprite3 = new ErrorSprite();
        errorSprite3.setPosition(errorSprite2.getSprite().getPosition().x + btGreenWidth, errorSprite1.getSprite().getPosition().y);

        normalSprite = new NormalSprite();
        normalSprite.setPosition(exitSprite.getSprite().getPosition().x + 18, exitSprite.getSprite().getPosition().y +
                exitSprite.getSprite().getBoundingBox().size.height + 3);
        sprites.add(errorSprite1);
        sprites.add(errorSprite2);
        sprites.add(errorSprite3);


        setBlood(luckyData.getBloodemy(), bloodBlankEmy);
        setBlood(luckyData.getBloodme(),bloodBlankme);
        addToLayer();
        setIsTouchEnabled(true);
    }

    private void initRockMan() {
        spriteBg = new SpriteBg();
        spriteBg.initBg(luckyData.getChapter());
        rockManSprite = new RockManSprite();
        rockManSprite.initRockMan(luckyData.getChapter());
        sprites.add(spriteBg);
        sprites.add(rockManSprite);
        this.addChild(spriteBg.getSprite());
        this.addChild(rockManSprite.getSprite());
        this.reorderChild(spriteBg.getSprite(), -2);
        this.reorderChild(rockManSprite.getSprite(), -2);

    }

    private void addToLayer() {
        for (BaseSprite sprite : sprites) {
            this.addChild(sprite.getSprite());
        }
        this.addChild(paperSpritefly.getSprite());
        this.addChild(scissorsSpritefly.getSprite());
        this.addChild(rockSpritefly.getSprite());
        this.addChild(normalSprite.getSprite());
        this.reorderChild(normalSprite.getSprite(), -1);
        int n=BaseApplication.user.getHeartNum()+
                BaseApplication.user.getSamllheartNum()+
                BaseApplication.user.getBowNum()+
                BaseApplication.user.getSmallbowNum()
                +BaseApplication.user.getShieldNum()+
                BaseApplication.user.getStarNum()+
                BaseApplication.user.getSwordNum();
        for (int i=0;i<(n<=5?n:5);i++){
            getProp();
        }


    }


    @Override
    public boolean ccTouchesBegan(MotionEvent event) {

        CGPoint point = convertTouchToNodeSpace(event);
        for (BaseSprite sprite : sprites) {
            CGRect rect = sprite.getSprite().getBoundingBox();
            if (CGRect.containsPoint(rect, point) && clickstatus == 0) {
                if (sprite instanceof RockSprite) {
                    clickstatus = CLICK_ROCK;
                    break;
                }
                if (sprite instanceof PaperSprite) {
                    clickstatus = CLICK_PAPER;
                    break;
                }
                if (sprite instanceof ScissorsSprite) {
                    clickstatus = CLICK_SCISSORS;
                    break;
                }
                if (sprite instanceof ExitSprite){
                    Message msg=new Message();
                    msg.what=6;
                    Bundle bundle=new Bundle();
                    bundle.putInt("chapter",luckyData.getChapter());
                    msg.setData(bundle);
                    ((LuckyActivity) CCDirector.sharedDirector().getActivity()).handler.sendMessage(msg);
                }
                if (sprite instanceof FightSprite){
                    if (error1State!=0&&error2State!=0&&error3State!=0){
                        LuckyResult luckyResult = new LuckyResult();
                        int score=0;
                        luckyResult.setChapter(luckyData.getChapter());

                        int firstemy= rand()%3+1;
                        score +=comption(error1State,firstemy);
                        luckyResult.setFirstme(error1State);
                        luckyResult.setFirstemy(firstemy);

                        int secondemy= rand()%3+1;
                        score += comption(error2State,secondemy);
                        luckyResult.setSecondme(error2State);
                        luckyResult.setSecondemy(secondemy);

                        int thirdemy= rand()%3+1;
                        score +=comption(error3State,thirdemy);
                        luckyResult.setThirdme(error3State);
                        luckyResult.setThirdemy(thirdemy);

                        if (score>0){
                            luckyResult.setGrade(0);
                            if (propState==PROP_STAR||propState==PROP_SWORD){
                                luckyData.setBloodemy(luckyData.getBloodemy() - score*2);
                            }else{
                                luckyData.setBloodemy(luckyData.getBloodemy() - score);
                            }
                            if (luckyData.getBloodemy()<=0){
                                nextChapter();
                            }
                        }else if (score<0){
                            luckyResult.setGrade(1);
                            if (propState==PROP_STAR||propState==PROP_SHIELD){

                            }else {
                                luckyData.setBloodme(luckyData.getBloodme() + score);
                            }
                        }else {
                            luckyResult.setGrade(2);
                        }
                        clean();
                        if (luckyData.getBloodme()<=0||(luckyData.getBloodemy()<=0&&luckyData.getChapter()>=8)){
                            Message msg=new Message();
                            msg.what=4;
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("result",luckyResult);
                            msg.setData(bundle);
                            ((LuckyActivity) CCDirector.sharedDirector().getActivity()).handler.sendMessage(msg);
                        }else {
                            Message msg = new Message();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("result", luckyResult);
                            msg.setData(bundle);
                            ((LuckyActivity) CCDirector.sharedDirector().getActivity()).handler.sendMessage(msg);
                        }
                    }else{
                        Message msg=new Message();
                        msg.what=0;
                        ((LuckyActivity) CCDirector.sharedDirector().getActivity()).handler.sendMessage(msg);
                    }
                    break;
                }
                if (sprite instanceof BasePropSprite) {
                    if (sprite instanceof BowSprite) {
                        clickedSprite = sprite;
                        clickstatus = CLICK_BOW;
                    }
                    if (sprite instanceof HeartSprite) {
                        clickedSprite = sprite;
                        clickstatus = CLICK_HEART;
                    }
                    if (sprite instanceof SmallBowSprite) {
                        clickedSprite = sprite;
                        clickstatus = CLICK_SMALL_BOW;
                    }
                    if (sprite instanceof SmallHeartSprite) {
                        clickedSprite = sprite;
                        clickstatus = CLICK_SMALL_HEART;
                    }
                    if (sprite instanceof StarSprite) {
                        clickedSprite = sprite;
                        clickstatus = CLICK_STAR;
                    }
                    if (sprite instanceof ShieldSprite) {
                        clickedSprite = sprite;
                        clickstatus = CLICK_SHIELD;
                    }
                    if (sprite instanceof SwordSprite) {
                        clickedSprite = sprite;
                        clickstatus = CLICK_SWORD;
                    }
                    clickedPoint=clickedSprite.getSprite().getPosition();
                    break;
                }
            }
        }
        return super.ccTouchesBegan(event);
    }


    private int comption(int errorState, int emy) {
        if (errorState==emy){
            return 0;
        }
        if (errorState-emy==1||emy-errorState==2){
            return -1;
        }
        if (emy-errorState==1||errorState-emy==2){
            return 1;
        }
        return 0;
    }


    public List<BaseSprite> props;
    public void getProp(){
        if (props.size()<5) {
            BaseSprite propSprite = null;
            while (true) {
                int flag=0;
                switch (rand() % 7) {
                    case 0:
                        if (BaseApplication.user.getSmallbowNum() > 0) {
                            propSprite = new SmallBowSprite();
                            BaseApplication.user.setSmallbowNum(BaseApplication.user.getSmallbowNum() - 1);
                            flag=1;
                        }
                        break;
                    case 1:
                        if (BaseApplication.user.getSamllheartNum() > 0) {
                            propSprite = new SmallHeartSprite();
                            BaseApplication.user.setSamllheartNum(BaseApplication.user.getSamllheartNum() - 1);
                            flag=1;
                        }
                        break;
                    case 2:
                        if (BaseApplication.user.getBowNum() > 0) {
                            propSprite = new BowSprite();
                            BaseApplication.user.setBowNum(BaseApplication.user.getBowNum()-1);
                            flag=1;
                        }
                        break;
                    case 3:
                        if (BaseApplication.user.getHeartNum() > 0) {
                            propSprite = new HeartSprite();
                            BaseApplication.user.setHeartNum(BaseApplication.user.getHeartNum() - 1);
                            flag=1;
                        }
                        break;
                    case 4:
                        if (BaseApplication.user.getSwordNum() > 0) {
                            propSprite = new SwordSprite();
                            BaseApplication.user.setSwordNum(BaseApplication.user.getSwordNum()-1);
                            flag=1;
                        }
                        break;
                    case 5:
                        if (BaseApplication.user.getShieldNum() > 0) {
                            propSprite = new ShieldSprite();
                            BaseApplication.user.setShieldNum(BaseApplication.user.getShieldNum() - 1);
                            flag=1;
                        }
                        break;
                    case 6:
                        if (BaseApplication.user.getStarNum() > 0) {
                            propSprite = new StarSprite();
                            BaseApplication.user.setStarNum(BaseApplication.user.getStarNum() - 1);
                            flag=1;
                        }
                        break;
                    default:
                        System.out.println("mei");
                }
                if (flag==1)
                break;
            }
            propSprite.getSprite().setPosition(winsize.width / 2, winsize.height / 7 * 6);
            sprites.add(propSprite);
            this.addChild(propSprite.getSprite());
            CCMoveTo moveTo = CCMoveTo.action(1, CGPoint.ccp(10 + props.size() * (propSprite.getSprite().getBoundingBox().size.width), 30));
            propSprite.getSprite().runAction(moveTo);
            props.add(propSprite);
        }
    }


    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        CGPoint point = convertTouchToNodeSpace(event);
        switch (clickstatus) {
            case CLICK_ROCK:
                moveAndCheck(rockSpritefly, point);
                break;
            case CLICK_SCISSORS:
                moveAndCheck(scissorsSpritefly, point);
                break;
            case CLICK_PAPER:
                moveAndCheck(paperSpritefly, point);
                break;
            case CLICK_BOW:
            case CLICK_HEART:
            case CLICK_SMALL_BOW:
            case CLICK_SMALL_HEART:
            case CLICK_SHIELD:
            case CLICK_STAR:
            case CLICK_SWORD:
                clickedSprite.getSprite().setPosition(point.x - clickedSprite.getSprite().getBoundingBox().size.width / 2,
                        point.y - clickedSprite.getSprite().getBoundingBox().size.height / 2);
                break;


        }

        return super.ccTouchesMoved(event);
    }

    private void moveAndCheck(BaseSprite spritefly, CGPoint point) {
        spritefly.getSprite().setPosition(point.x - spritefly.getSprite().getBoundingBox().size.width / 2,
                point.y - spritefly.getSprite().getBoundingBox().size.height / 2);
        checkAndPut(errorSprite1, error1State, point);
        checkAndPut(errorSprite2, error2State, point);
        checkAndPut(errorSprite3, error3State, point);
    }

    public boolean checkAndPut(BaseSprite errorSprite, int errorState, CGPoint point) {
        switch (clickstatus) {
            case CLICK_ROCK:
                return checkAndPutRock(errorSprite, errorState, point);
            case CLICK_PAPER:
                return checkAndPutPaper(errorSprite, errorState, point);
            case CLICK_SCISSORS:
                return checkAndPutScissors(errorSprite, errorState, point);
        }
        return false;
    }

    public boolean checkAndPutRock(BaseSprite errorSprite, int errorState, CGPoint point) {
        if (CGRect.containsPoint(errorSprite.getSprite().getBoundingBox(), point) && errorState != STATE_ROCK) {
            errorSprite.getSprite().removeAllChildren(true);
            BoRockSprite boRockSprite = new BoRockSprite();
            boRockSprite.setPosition(0, 0);
            errorSprite.getSprite().addChild(boRockSprite.getSprite());
            if (errorSprite==errorSprite1){
                error1State = STATE_ROCK;
            }else if(errorSprite==errorSprite2){
                error2State=STATE_ROCK;
            }else if(errorSprite==errorSprite3){
                error3State=STATE_ROCK;
            }

            return true;
        }
        return false;
    }

    public boolean checkAndPutPaper(BaseSprite errorSprite, int errorState, CGPoint point) {
        if (CGRect.containsPoint(errorSprite.getSprite().getBoundingBox(), point) && errorState != STATE_PAPER) {
            errorSprite.getSprite().removeAllChildren(true);
            BoPaperSprite boPaperSprite = new BoPaperSprite();
            boPaperSprite.setPosition(0, 0);
            errorSprite.getSprite().addChild(boPaperSprite.getSprite());
            if (errorSprite==errorSprite1){
                error1State = STATE_PAPER;
            }else if(errorSprite==errorSprite2){
                error2State=STATE_PAPER;
            }else if(errorSprite==errorSprite3){
                error3State=STATE_PAPER;
            }
            return true;
        }
        return false;
    }

    public boolean checkAndPutScissors(BaseSprite errorSprite, int errorState, CGPoint point) {
        if (CGRect.containsPoint(errorSprite.getSprite().getBoundingBox(), point) && errorState != STATE_SCISSORS) {
            errorSprite.getSprite().removeAllChildren(true);
            BoScissorsSprite boScissorsSprite = new BoScissorsSprite();
            boScissorsSprite.setPosition(0, 0);
            errorSprite.getSprite().addChild(boScissorsSprite.getSprite());
            if (errorSprite==errorSprite1){
                error1State = STATE_SCISSORS;
            }else if(errorSprite==errorSprite2){
                error2State=STATE_SCISSORS;
            }else if(errorSprite==errorSprite3){
                error3State=STATE_SCISSORS;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        CGPoint point = convertTouchToNodeSpace(event);
        if (clickstatus == CLICK_ROCK) {
            goBack(rockSprite, rockSpritefly);
        }
        if (clickstatus == CLICK_PAPER) {
            goBack(paperSprite,paperSpritefly);
        }
        if (clickstatus == CLICK_SCISSORS) {
            goBack(scissorsSprite,scissorsSpritefly);
        }
        if (clickstatus==CLICK_BOW){
            showEffect(spriteBg, point);
        }
        if (clickstatus==CLICK_SMALL_HEART){
            showEffect(bloodBlankme,point);
        }
        if (clickstatus==CLICK_SWORD){
            showEffect(normalSprite,point);
        }
        if (clickstatus==CLICK_HEART){
            showEffect(bloodBlankme,point);
        }
        if (clickstatus==CLICK_SHIELD){
            showEffect(normalSprite,point);
        }
        if (clickstatus==CLICK_STAR){
            showEffect(normalSprite,point);
        }
        if (clickstatus== CLICK_SMALL_BOW) {
            showEffect(spriteBg, point);
        }
        return super.ccTouchesEnded(event);
    }

    private void showEffect(BaseSprite spriteRect,CGPoint point) {
        CGRect rect=spriteRect.getSprite().getBoundingBox();
        if (CGRect.containsPoint(rect,point)){
            if (spriteRect instanceof NormalSprite) {
                if (propState == PROP_NORMAL) {
                    selectedProp = (BasePropSprite) clickedSprite;
                    if (clickstatus == CLICK_STAR) {
                        propState = PROP_STAR;
                    }
                    if (clickstatus == CLICK_SWORD) {
                        propState = PROP_SWORD;
                    }
                    if (clickstatus == CLICK_SHIELD) {
                        propState = PROP_SHIELD;
                    }
                    removeAndShowPower();
                    changestates();
                } else {
                    CCMoveTo moveTo = CCMoveTo.action(1, clickedPoint);
                    CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "changestates"));
                    clickedSprite.getSprite().runAction(sequence);
                }
            }else {
                removeAndShowPower();
                changestates();
            }

        }else{
            CCMoveTo moveTo = CCMoveTo.action(1, clickedPoint);
            CCSequence sequence=CCSequence.actions(moveTo,CCCallFunc.action(this,"changestates"));
            clickedSprite.getSprite().runAction(sequence);

        }
    }

    private void removeAndShowPower() {
        props.remove(clickedSprite);
        sprites.remove(clickedSprite);
        for (int i=0;i<props.size();i++){
            props.get(i).setPosition(10 + i * (clickedSprite.getSprite().getBoundingBox().size.width), 30);
        }
       // clickedSprite.getSprite().removeFromParentAndCleanup(true);
        ((BasePropSprite)clickedSprite).showPower(LuckyGuessLayer.this);
    }

    public void nextChapter(){
        luckyData.setChapter(luckyData.getChapter() + 1);
        if (luckyData.getChapter()>=8){
            //战胜所有对手，本模式游戏结束，返回主游戏界面
        }
        else {//进入下一章节
            spriteBg.getSprite().removeFromParentAndCleanup(true);
            rockManSprite.getSprite().removeFromParentAndCleanup(true);
            setBlood(luckyData.getBloodemy(), bloodBlankEmy);//设置敌方血量
            setBlood(luckyData.getBloodme(),bloodBlankme);//设置我方血量
            initRockMan();//创建boss及背景
        }
    }
    public void clean(){
       // spriteBg.getSprite().removeFromParentAndCleanup(true);//移除boss背景
       // rockManSprite.getSprite().removeFromParentAndCleanup(true);//移除boss
        setBlood(luckyData.getBloodemy(), bloodBlankEmy);//设置敌方血量
        setBlood(luckyData.getBloodme(),bloodBlankme);//设置我方血量
        if (null!=selectedProp) {//清除增益道具
            selectedProp.clear();
            propState = PROP_NORMAL;
        }
        errorSprite1.getSprite().removeAllChildren(true);//清除已出的拳
        errorSprite2.getSprite().removeAllChildren(true);
        errorSprite3.getSprite().removeAllChildren(true);
        error1State=0;
        error2State=0;
        error3State=0;
    }
    private void goBack(BaseSprite sprite, BaseSprite spritefly) {
        CCMoveTo ccMoveTo = CCMoveTo.actionWithDuration(0.5f, sprite.getSprite().getPosition());
        CCSequence actions = CCSequence.actions(ccMoveTo, CCCallFunc.action(this, "changestates"));
        spritefly.getSprite().runAction(actions);
    }

    public void changestates() {
        System.out.println("goback");
        clickstatus = 0;
    }
    public void setBlood(int num,BaseSprite sprite){
        sprite.getSprite().removeAllChildren(true);
        for(int i=0;i<num;i++){
            BloodSprite bloodSprite = new BloodSprite();
            bloodSprite.getSprite().setPosition((1+bloodSprite.getSprite().getBoundingBox().size.width)*i,10);
            sprite.getSprite().addChild(bloodSprite.getSprite());
        }
    }
}
