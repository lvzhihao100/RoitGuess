package com.lvzhihao.roitguess;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvzhihao.roitguess.bean.User;
import com.lvzhihao.roitguess.bean.WhareHouseProp;

import java.util.ArrayList;

/**
 * Created by vzhihao on 2016/4/21.
 */
public class PopWhareHouse {
    private final int layheight;
    private final int laywidth;
    private Context context;
    private RelativeLayout root;
    private PopupWindow myimgpopupwindow;
    private LayoutInflater inflater;
    private int positionY;
    private int popwidth;
    private int popheight;
    private ArrayList<WhareHouseProp> props;
    private RelativeLayout popupwindowShopLayout;

    public PopWhareHouse(Context context, RelativeLayout root, int positionY) {
        this.positionY = positionY;
        this.context = context;
        this.root = root;
        laywidth = root.getMeasuredWidth();
        layheight = root.getMeasuredHeight();
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupwindowShopLayout = (RelativeLayout) inflater.inflate(R.layout.popupwindow_wharehouse, null);
        popwidth = laywidth / 4;
        popheight = layheight * 2 / 3;
        props = new ArrayList<WhareHouseProp>();
        for (int i = 0; i < 7; i++) {
            WhareHouseProp whareHouseProp = new WhareHouseProp(popwidth, popheight, i);
            whareHouseProp.getTextView(context, i);
            props.add(whareHouseProp);
        }
        myimgpopupwindow = new PopupWindow(popupwindowShopLayout, laywidth / 4, layheight * 2 / 3);
        myimgpopupwindow.setAnimationStyle(R.style.popwindow_anim_style_wharehouse); //设置动画

    }

    public void toggle() {
        if (myimgpopupwindow.isShowing())
            myimgpopupwindow.dismiss();
        else {
            updataView();
            myimgpopupwindow.showAtLocation(root, Gravity.NO_GRAVITY, 0, positionY);
        }
    }
public boolean isShowing(){
    return myimgpopupwindow.isShowing();
}
    public void updataView() {
        User user=BaseApplication.user;
        int[] propsnum = new int[]{
                user.getHeartNum(),
                user.getSamllheartNum(),
                user.getBowNum(),
                user.getSmallbowNum(),
                user.getShieldNum(),
                user.getSwordNum(),
                user.getStarNum()
        };
        int flag = 0;
        popupwindowShopLayout.removeAllViews();
        for (int i = 0; i < props.size(); i++) {
            if (propsnum[i] > 0) {
                props.get(i).setText(propsnum[i] + "");
                popupwindowShopLayout.addView(props.get(i).getTextView(context, flag));
                flag++;
            }
        }
    }

    public void dismiss() {
        myimgpopupwindow.dismiss();
    }
}
