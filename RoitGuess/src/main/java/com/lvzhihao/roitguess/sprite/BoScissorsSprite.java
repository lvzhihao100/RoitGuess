package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/12.
 */
public class BoScissorsSprite extends BaseSprite {
    @Override
    public void init() {
        sprite= CCSprite.sprite("bo_scissors.png");
        sprite.setScaleX((winsize.width-20)/5/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.height)/10/sprite.getTextureRect().size.height);
    }
}
