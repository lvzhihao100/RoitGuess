package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/9.
 */
public class AreaSprite extends BaseSprite {
    @Override
    public void init() {
        sprite= CCSprite.sprite("bg_plrArea.png");
        sprite.setPosition(10,10);
        sprite.setScaleX((winsize.width-20)/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.height/2+winsize.height/12)/sprite.getTextureRect().size.height);
    }
}
