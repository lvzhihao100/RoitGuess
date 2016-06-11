package com.lvzhihao.roitguess.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lvzhihao.roitguess.R;

/**
 * Created by vzhihao on 2016/4/12.
 */
public class DrawableTextView extends TextView {

    private Drawable drawableBottom;
    private Drawable drawableTop;
    private Drawable drawableLeft;
    private Drawable drawableRight;
    private int mLeftHeight;
    private int mLeftWidth;
    private int mRightHeight;
    private int mRightWidth;
    private int mBottomHeight;
    private int mBottomWidth;
    private int mTopHeight;
    private int mTopWidth;

    public DrawableTextView(Context context) {
        super(context);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,context);
    }

    private void init(AttributeSet attrs,Context context) {
        if (attrs != null) {
            float scale = context.getResources().getDisplayMetrics().density;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.DrawableTextView_peng_drawableBottom:
                        drawableBottom = a.getDrawable(attr);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableTop:
                        drawableTop = a.getDrawable(attr);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableLeft:
                        drawableLeft = a.getDrawable(attr);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableRight:
                        drawableRight = a.getDrawable(attr);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableTopWidth:
                        mTopWidth = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableTopHeight:
                        mTopHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableBottomWidth:
                        mBottomWidth = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableBottomHeight:
                        mBottomHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableRightWidth:
                        mRightWidth = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableRightHeight:
                        mRightHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableLeftWidth:
                        mLeftWidth = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;
                    case R.styleable.DrawableTextView_peng_drawableLeftHeight:
                        mLeftHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    default:
                        break;
                }
            }
            a.recycle();
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,context);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, mLeftWidth <= 0 ? left.getIntrinsicWidth() : mLeftWidth, mLeftHeight <= 0 ? left.getMinimumHeight() : mLeftHeight);
        }
        if (right != null) {
            right.setBounds(0, 0, mRightWidth <= 0 ? right.getIntrinsicWidth() : mRightWidth, mRightHeight <= 0 ? right.getMinimumHeight() : mRightHeight);
        }
        if (top != null) {
            top.setBounds(0, 0, mTopWidth <= 0 ? top.getIntrinsicWidth() : mTopWidth, mTopHeight <= 0 ? top.getMinimumHeight() : mTopHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mBottomWidth <= 0 ? bottom.getIntrinsicWidth() : mBottomWidth, mBottomHeight <= 0 ? bottom.getMinimumHeight()
                    : mBottomHeight);
        }
        setCompoundDrawables(left, top, right, bottom);
    }
}
