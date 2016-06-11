package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

/**
 * Created by vzhihao on 2016/3/9.
 */
public class SpriteBloodBlank extends BaseSprite{
     public void init() {
        sprite = CCSprite.sprite("box_live.png");
        sprite.setPosition(15,winsize.getHeight()-winsize.height/14);
    }

}
