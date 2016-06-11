package com.lvzhihao.roitguess;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvzhihao.roitguess.bean.User;
import com.lvzhihao.roitguess.util.HttpUtils;
import com.lvzhihao.roitguess.view.DrawableTextView;

/**
 * Created by vzhihao on 2016/4/20.
 */
public class PopShop {
    int[] itemsId = new int[]{
            R.mipmap.heart,
            R.mipmap.s_heart,
            R.mipmap.bow,
            R.mipmap.s_bow,
            R.mipmap.shield,
            R.mipmap.sword,
            R.mipmap.star
    };
    private final int layheight;
    private final int laywidth;
    private Context context;
    private RelativeLayout root;
    private PopupWindow myimgpopupwindow;
    private LayoutInflater inflater;
    private TextView money;
    private ListView list;
    private int positionY;


    public PopShop(Context context, RelativeLayout root, int positionY) {
        this.positionY = positionY;
        this.context = context;
        this.root = root;
        laywidth = root.getMeasuredWidth();
        layheight = root.getMeasuredHeight();
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout popupwindowShopLayout = (RelativeLayout) inflater.inflate(R.layout.popupwindow_shop, null);
        list = (ListView) popupwindowShopLayout.findViewById(R.id.shop_list);
        money = (TextView) popupwindowShopLayout.findViewById(R.id.money);

        list.setAdapter(new MyAdapter());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int flag=0;
                int mymoney = BaseApplication.user.getMoney();
                if ((position == 0 || position == 2 || position == 6)) {
                    if (mymoney >= 500) {
                        BaseApplication.user.setMoney(mymoney - 500);
                        updataPropNum(position);
                        flag=1;
                    }

                } else {
                    if (mymoney >= 100) {
                        BaseApplication.user.setMoney(mymoney - 100);
                        updataPropNum(position);
                        flag=1;
                    }
                }
                if (flag==1) {
                    money.setText(BaseApplication.user.getMoney() + "");
                    ((HomeActivity)context).handler.sendEmptyMessage(0);
                }

            }
        });
        myimgpopupwindow = new PopupWindow(popupwindowShopLayout, laywidth * 2 / 3, layheight * 2 / 3);
        myimgpopupwindow.setAnimationStyle(R.style.popwindow_anim_style_shop); //设置动画

    }

    private void updataPropNum(int position) {
        User user=BaseApplication.user;
        switch (position){
            case 0:user.setHeartNum(user.getHeartNum()+5);
                break;
            case 1:
                user.setSamllheartNum(user.getSamllheartNum()+5);
                break;
            case 2:
                user.setBowNum(user.getBowNum()+5);
                break;
            case 3:
                user.setSmallbowNum(user.getSmallbowNum()+5);
                break;
            case 4:
                user.setShieldNum(user.getShieldNum()+5);
                break;
            case 5:
                user.setSwordNum(user.getSwordNum()+5);
                break;
            case 6:
                user.setStarNum(user.getStarNum()+5);
                break;

        }
        HttpUtils.updataLocal();
    }

    public void toggle() {
        money.setText(BaseApplication.user.getMoney() + "");
        if (myimgpopupwindow.isShowing())
            myimgpopupwindow.dismiss();
        else
            myimgpopupwindow.showAtLocation(root, Gravity.NO_GRAVITY, laywidth / 3, positionY);
    }

    public void dismiss() {
        myimgpopupwindow.dismiss();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoder viewHoder = null;
            if (convertView == null) {
                viewHoder = new ViewHoder();
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.popshop_item_heart, null);
                DrawableTextView d = (DrawableTextView) linearLayout.findViewById(R.id.item);
                Drawable drawableLeft = context.getResources().getDrawable(itemsId[position]);
                Drawable drawableRight = context.getResources().getDrawable(R.mipmap.coin);
                d.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null);
                viewHoder.setD(d);
                viewHoder.setDrawableLeft(drawableLeft);
                viewHoder.setDrawableRight(drawableRight);
                convertView = linearLayout;
            } else {
                viewHoder = (ViewHoder) convertView.getTag();
                viewHoder.getD().setCompoundDrawablesWithIntrinsicBounds(
                        context.getResources().getDrawable(itemsId[position]),
                        null, viewHoder.getDrawableRight(), null);
            }
            if (position == 0 || position == 2 || position == 6) {
                viewHoder.getD().setText("5个 500");
            } else {
                viewHoder.getD().setText("5个 100");
            }
            convertView.setTag(viewHoder);
            return convertView;
        }
    }

    class ViewHoder {
        DrawableTextView d;
        Drawable drawableLeft;
        Drawable drawableRight;

        public DrawableTextView getD() {
            return d;
        }

        public void setD(DrawableTextView d) {
            this.d = d;
        }

        public Drawable getDrawableLeft() {
            return drawableLeft;
        }

        public void setDrawableLeft(Drawable drawableLeft) {
            this.drawableLeft = drawableLeft;
        }

        public Drawable getDrawableRight() {
            return drawableRight;
        }

        public void setDrawableRight(Drawable drawableRight) {
            this.drawableRight = drawableRight;
        }
    }
}
