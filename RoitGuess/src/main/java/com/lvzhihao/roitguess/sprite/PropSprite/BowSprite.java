package com.lvzhihao.roitguess.sprite.PropSprite;

import com.lvzhihao.roitguess.R;
import com.lvzhihao.roitguess.layer.LuckyGuessLayer;
import com.lvzhihao.roitguess.sprite.BaseSprite;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

/**
 * Created by vzhihao on 2016/3/12.
 */
public class BowSprite extends BasePropSprite {
    @Override
    public void init() {
        sprite = CCSprite.sprite("desk/bow.png");
        sprite.setScaleX((winsize.width-20)/5/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.height)/8/sprite.getTextureRect().size.height);
    }

    @Override
    public void showPower(LuckyGuessLayer layer) {
        sprite.setPosition(winsize.width / 2 - sprite.getBoundingBox().size.width / 2,
                winsize.height / 8 * 7);
        CCMoveTo moveTo=CCMoveTo.action(0.5f, CGPoint.ccp(winsize.width / 2 - sprite.getBoundingBox().size.width / 2,
                winsize.height / 8 * 5));
        CCSequence sequence=CCSequence.actions(moveTo, CCCallFunc.action(this, "playSound"));
        layer.luckyData.setBloodemy((layer.luckyData.getBloodemy()-3)<0?0:layer.luckyData.getBloodemy()-3);
        layer.setBlood(layer.luckyData.getBloodemy(), layer.bloodBlankEmy);
        sprite.runAction(sequence);
        if (layer.luckyData.getBloodemy()<=0){
            layer.nextChapter();
            layer.clean();
        }
    }

    @Override
    public void clear() {

    }

    public void playSound(){
        SoundEngine soundEngine= SoundEngine.sharedEngine();
        soundEngine.playSound(CCDirector.theApp, R.raw.bow,false);
        sprite.removeFromParentAndCleanup(true);
    }
}
