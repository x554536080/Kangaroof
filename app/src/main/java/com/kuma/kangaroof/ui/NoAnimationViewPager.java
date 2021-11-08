package com.kuma.kangaroof.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * name: NoAnimaViewPager
 * author: 熊熊熊
 * date: 2021/11/8 19:16
 * description:
 */
public class NoAnimationViewPager extends ViewPager {
    //定义一个变量，用来设置是否有切换动画，支持配置
    private boolean animationEnabled = false;
    //定义一个变量，用来设置是否可以左右滑动，支持配置
    private boolean noScroll;

    public NoAnimationViewPager(@NonNull Context context) {
        super(context);
    }

    public NoAnimationViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        //这里读取设置的值
        super.setCurrentItem(item, animationEnabled);
    }

    @Override
    public void setCurrentItem(int item) {
        //这里读取设置的值
        super.setCurrentItem(item, animationEnabled);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    //设置方法
    public void setAnimationEnabled(boolean animationEnabled) {
        this.animationEnabled = animationEnabled;
    }
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
}
