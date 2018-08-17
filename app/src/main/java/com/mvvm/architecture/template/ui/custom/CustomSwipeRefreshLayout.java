package com.mvvm.architecture.template.ui.custom;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/*
    Cần dùng khi SwipeRefreshLayout có chứa ViewPager
    để việc vuốt sang trái sang phải ko bị lỗi
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    private int mTouchSlop;
    private float mPrevX;
    private float mPrevY;

    public CustomSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(ev).getX();
                mPrevY = MotionEvent.obtain(ev).getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float evX = ev.getX();
                final float evy = ev.getY();
                float xDiff = Math.abs(evX - mPrevX);
                float yDiff = Math.abs(evy - mPrevY);
                if (xDiff > mTouchSlop && xDiff > yDiff) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
