package com.lvzhihao.roitguess.layer;

import android.view.MotionEvent;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.protocols.CCBlendProtocol;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccBlendFunc;

/**
 * Created by vzhihao on 2016/3/8.
 */
public class HomeLayer extends BaseLayer {

    private CCSprite spring;
    private CCHide hide;
    private CCDelayTime delayTime;
    private CCSprite map;

    @Override
    public void init() {
        super.init();
        initLayer();
        //logo();
    }

    private void logo() {
        CCSprite scissors=CCSprite.sprite("bo_scissors.png");
        scissors.setPosition(winsize.width / 2, winsize.height / 2);
        this.addChild(scissors);
        hide = CCHide.action();
        delayTime = CCDelayTime.action(1);
        CCSequence scisequence=CCSequence.actions(delayTime, hide, CCCallFunc.action(this, "addrock"));
        scissors.runAction(scisequence);

    }

    public void addrock(){
        CCSprite rock=CCSprite.sprite("bo_rock.png");
        rock.setPosition(winsize.width / 2, winsize.height / 2);
        this.addChild(rock);
        CCSequence rocksequence=CCSequence.actions(delayTime, hide, CCCallFunc.action(this, "addpaper"));
        rock.runAction(rocksequence);
    }


    public void addpaper(){
        CCSprite paper=CCSprite.sprite("bo_paper.png");
        paper.setPosition(winsize.width / 2, winsize.height / 2);
        this.addChild(paper);
        CCSequence papersequence=CCSequence.actions(delayTime, hide, CCCallFunc.action(this, "initLayer"));
        paper.runAction(papersequence);
    }
    public void initLayer() {
        CCScene scene=CCScene.node();
        scene.addChild(new LuckyGuessLayer());
        CCDirector.sharedDirector().replaceScene(scene);
    }
}
