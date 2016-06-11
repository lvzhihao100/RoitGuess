package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/18.
 */
public class RockManSprite extends BaseSprite {
    @Override
    public void init() {
        sprite = CCSprite.sprite("elephants.jpg");
    }
    public void initRockMan(int chapter){
        sprite= CCSprite.sprite("rockman_"+chapter+".png");
        sprite.setAnchorPoint(0, 0);
        sprite.setScaleX(winsize.getWidth()/3/sprite.getTextureRect().size.width);
        sprite.setScaleY(winsize.getHeight() / 3 / sprite.getTextureRect().size.height);
        sprite.setPosition(winsize.getWidth()/3,winsize.getHeight()/20*11);
    }
}
