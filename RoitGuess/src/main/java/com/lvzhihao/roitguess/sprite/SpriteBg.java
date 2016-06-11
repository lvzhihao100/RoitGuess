package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/9.
 */
public class SpriteBg extends BaseSprite {
    @Override
    public void init() {
        sprite = CCSprite.sprite("elephants.jpg");
    }
    public void initBg(int chapter){
        sprite = CCSprite.sprite("bg_"+chapter+".jpg");
        sprite.setAnchorPoint(0, 0);
        sprite.setScaleX((winsize.getWidth()-20)/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.getHeight()/2-winsize.height/8)/sprite.getTextureRect().size.height);
        sprite.setPosition(10, winsize.getHeight()/2+winsize.height/20);
    }
}
