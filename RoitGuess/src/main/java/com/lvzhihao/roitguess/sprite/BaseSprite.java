package com.lvzhihao.roitguess.sprite;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

/**
 * Created by vzhihao on 2016/3/9.
 */
public abstract  class BaseSprite  {
    protected CCSprite sprite;
    protected CGSize winsize;
    public BaseSprite(){
        winsize= CCDirector.sharedDirector().getWinSize();
        init();
        sprite.setAnchorPoint(0, 0);
    }
    public CCSprite getSprite(){
        return sprite;
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);

    }
    public void setPosition(CGPoint position) {
        sprite.setPosition(position);

    }

    /**
     * 绑定图片，设置锚点
     */
    public abstract void init();
}
