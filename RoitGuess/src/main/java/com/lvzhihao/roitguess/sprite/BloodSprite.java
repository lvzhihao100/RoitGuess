package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/12.
 */
public class BloodSprite extends BaseSprite {
    @Override
    public void init() {
        sprite= CCSprite.sprite("heart.png");
        sprite.setScaleX((winsize.width-20)/15/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.height) / 30 / sprite.getTextureRect().size.height);
    }
}
