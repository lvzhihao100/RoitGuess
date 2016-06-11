package com.lvzhihao.roitguess.sprite.PropSprite;

import com.lvzhihao.roitguess.layer.LuckyGuessLayer;
import com.lvzhihao.roitguess.sprite.BaseSprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/12.
 */
public class SmallHeartSprite extends BasePropSprite {
    @Override
    public void init() {
        sprite = CCSprite.sprite("desk/s_heart.png");
        sprite.setScaleX((winsize.width-20)/5/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.height)/8/sprite.getTextureRect().size.height);
    }


    @Override
    public void showPower(LuckyGuessLayer layer) {

        layer.luckyData.setBloodme((layer.luckyData.getBloodme()+1)>7?7:layer.luckyData.getBloodme()+1);
        layer.setBlood(layer.luckyData.getBloodme(),layer.bloodBlankme);
        sprite.removeFromParentAndCleanup(true);
    }

    @Override
    public void clear() {

    }
}
