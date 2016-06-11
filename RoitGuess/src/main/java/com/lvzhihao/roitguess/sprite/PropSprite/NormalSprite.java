package com.lvzhihao.roitguess.sprite.PropSprite;

import com.lvzhihao.roitguess.sprite.BaseSprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/16.
 */
public class NormalSprite extends BaseSprite {
    @Override
    public void init() {
        sprite= CCSprite.sprite("desk/normal.png");
        sprite.setScaleX((winsize.width-20)/5/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.height)/8/sprite.getTextureRect().size.height);
    }
}
