package com.vluver.cbj.colegio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class BottomNavigationViewPager extends ViewPager {
    private boolean isPagingEnable;


    public BottomNavigationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isPagingEnable = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isPagingEnable) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isPagingEnable) {
            return super.onTouchEvent(event);
        }

        return false;
    }


    public void setPagingEnable(boolean pagingEnable) {
        isPagingEnable = pagingEnable;
    }

    public void setPagingEnabled(boolean enabled) {
        this.isPagingEnable = enabled;
    }
}
