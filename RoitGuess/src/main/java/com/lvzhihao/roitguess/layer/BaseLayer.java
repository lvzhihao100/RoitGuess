package com.lvzhihao.roitguess.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

/**
 * Created by vzhihao on 2016/3/9.
 */
public class BaseLayer extends CCLayer {

    protected final CGSize winsize;

    public BaseLayer(){
        winsize = CCDirector.sharedDirector().getWinSize();
        init();
    }

    public void init() {

    }
}
