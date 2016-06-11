package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/9.
 */
public class PropBgSprite extends  BaseSprite {
    @Override
    public void init() {
        sprite= CCSprite.sprite("desk.png");
        sprite.setScaleX((winsize.getWidth()-20)/sprite.getTextureRect().size.width);
        sprite.setScaleY(winsize.height/6/sprite.getTextureRect().size.height);
        sprite.setPosition(10,10);
    }
}
