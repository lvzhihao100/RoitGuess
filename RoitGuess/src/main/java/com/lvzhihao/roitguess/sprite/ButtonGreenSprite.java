package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/9.
 */
public class ButtonGreenSprite extends BaseSprite {
    @Override
    public void init() {
        sprite= CCSprite.sprite("btn_props_b.png");
        sprite.setScaleX((winsize.width-20)/4/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.height)/7/sprite.getTextureRect().size.height);
    }
}