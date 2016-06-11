package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by vzhihao on 2016/3/11.
 */
public class LuckyBgSprite extends BaseSprite {
    @Override
    public void init() {
        sprite= CCSprite.sprite("bg_main.png");
        sprite.setScaleX(winsize.width / sprite.getTextureRect().size.width);
        sprite.setScaleY(winsize.height / sprite.getTextureRect().size.height);
    }
}
