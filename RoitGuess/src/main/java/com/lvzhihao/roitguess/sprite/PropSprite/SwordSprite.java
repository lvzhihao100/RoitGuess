package com.lvzhihao.roitguess.sprite.PropSprite;

import com.lvzhihao.roitguess.layer.LuckyGuessLayer;
import com.lvzhihao.roitguess.sprite.BaseSprite;

import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.ccColor3B;

/**
 * Created by vzhihao on 2016/3/12.
 */
public class SwordSprite extends BasePropSprite {

    private CCLabel label;

    @Override
    public void init() {
        sprite = CCSprite.sprite("desk/sword.png");
        sprite.setScaleX((winsize.width-20)/5/sprite.getTextureRect().size.width);
        sprite.setScaleY((winsize.height)/8/sprite.getTextureRect().size.height);
    }


    @Override
    public void showPower(LuckyGuessLayer layer) {

        sprite.setPosition(layer.normalSprite.getSprite().getPosition());
        label = CCLabel.labelWithString("攻击加倍", "hkbd.ttf", 24);
        label.setColor(ccColor3B.ccc3(50, 0, 255));
        label.setPosition(winsize.width / 2 + 60, sprite.getPosition().y + 40);
        layer.addChild(label);
    }

    @Override
    public void clear() {
        label.removeFromParentAndCleanup(true);
        sprite.removeFromParentAndCleanup(true);
    }
}
