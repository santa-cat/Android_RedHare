package com.example.santa.redhare.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.santa.redhare.utils.ImageText;

/**
 * Created by santa on 16/8/2.
 */
public class MenuLayout extends LinearLayout {
    private ViewPager mPager;

    public MenuLayout(Context context) {
        this(context, null);
    }

    public MenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MenuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        int count = getChildCount();
        for (int i = 0 ; i<count; i++) {
            View view = getChildAt(i);
            if (!(view instanceof ImageText)) {
                throw new IllegalStateException("MenuLayout don't support chid expect ImageText");
            }
            setItemListener(view, i);
        }
        super.onFinishInflate();
    }


    private void setItemListener(View view, final int position) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mPager) {
                    mPager.setCurrentItem(position);
                }
            }
        });
    }

    public void setViewPager(ViewPager pager) {
        mPager = pager;

        if (pager.getAdapter() == null || pager.getAdapter().getCount() != getChildCount()) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.addOnPageChangeListener(new PageListener());
        changeChildState(mPager.getCurrentItem());
    }


    private class PageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d("DEBUG_santa", "onPageScrolled");
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("DEBUG_santa", "onPageScrollStateChanged");

        }

        @Override
        public void onPageSelected(int position) {
            Log.d("DEBUG_santa", "onPageSelected");
            changeChildState(position);

        }

    }

    private void changeChildState(int position) {
        intChild();
        ((ImageText)getChildAt(position)).setChecked(true);
    }

    private void intChild() {
        final int count = getChildCount();
        for (int i = 0 ; i<count; i++) {
            ImageText item = (ImageText) getChildAt(i);
            item.setChecked(false);
        }
    }



}
