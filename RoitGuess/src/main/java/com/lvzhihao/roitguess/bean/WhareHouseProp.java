package com.lvzhihao.roitguess.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvzhihao.roitguess.R;

/**
 * Created by vzhihao on 2016/4/22.
 */
public class WhareHouseProp {
    int [] image=new int[]{
            R.mipmap.heart,
            R.mipmap.s_heart,
            R.mipmap.bow,
            R.mipmap.s_bow,
            R.mipmap.shield,
            R.mipmap.sword,
            R.mipmap.star
    };
    int num;
    int type;
    int popwidth;
    int popheight;
    TextView textView;
    private RelativeLayout.LayoutParams layoutParams;

    public WhareHouseProp(int popwidth,int popheight,int type){
        this.popheight=popheight;
        this.popwidth=popwidth;
        this.type=type;
    }
   public TextView getTextView(Context context,int i){
        if (textView==null) {
            textView = new TextView(context);
            layoutParams = new RelativeLayout.LayoutParams(popwidth * 5 / 6, popheight / 10);
            layoutParams.setMargins(0, popheight * 50 / 791 + i * popheight * 98 / 791, 0, 0);
            textView.setLayoutParams(layoutParams);
            textView.setText("0");
            textView.setTextSize(25);
            textView.setGravity(Gravity.CENTER);
            Drawable drawable = context.getResources().getDrawable(image[type]);
            drawable.setBounds(0, 0, popheight / 10, popheight / 10);
            textView.setCompoundDrawables(null, null, drawable, null);
        }else {
            layoutParams.setMargins(0, popheight * 50 / 791 + i * popheight * 98 / 791, 0, 0);
            textView.setLayoutParams(layoutParams);
        }
        return textView;
    }
   public void setText(String text){
        textView.setText(text);
    }
}
