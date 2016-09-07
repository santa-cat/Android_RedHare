package com.example.santa.redhare.utils;

/**
 * Created by santa on 16/7/12.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.santa.redhare.R;


public class PagerTag extends HorizontalScrollView {

    public interface IconTabProvider {
        public int getPageIconResId(int position);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[] {
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    // @formatter:on

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    private LinearLayout tabsContainer;
    private ViewPager pager;

    private int tabCount;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint trianglePaint;
    private Paint dividerPaint;

    private int dividerColor = 0x1A000000;
    private int tagTextColor = Color.BLUE;
    private int tagCurColor = Color.RED;
    private int tagBackColor = Color.BLACK;


    private boolean shouldExpand = false;
    private boolean textAllCaps = true;

    private int scrollOffset = 52;
    private int indicatorHeight = 8;
    private int underlineHeight = 10;
    private int dividerPadding = 12;
    private int tagPadding = 24;
    private int dividerWidth = 0;
    private int expandHeight = 30;

    private int tagTextSize = 12;

    private Path trainglePath = new Path();

    private int lastScrollX = 0;
    private int verticalPadding;


    public PagerTag(Context context) {
        this(context, null);
    }

    public PagerTag(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerTag(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tagPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tagPadding, dm);
        tagTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tagTextSize, dm);
        expandHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, expandHeight, dm);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerTag);

        tagTextSize = a.getDimensionPixelSize(R.styleable.PagerTag_ptTextSize, tagTextSize);
        tagTextColor = a.getColor(R.styleable.PagerTag_ptTextColor, tagTextColor);
        dividerColor = a.getColor(R.styleable.PagerTag_ptDividerColor, dividerColor);
        dividerPadding = a.getDimensionPixelSize(R.styleable.PagerTag_ptDividerPadding, dividerPadding);
        expandHeight = a.getDimensionPixelSize(R.styleable.PagerTag_ptExpandHeight, expandHeight);
        tagPadding = a.getDimensionPixelSize(R.styleable.PagerTag_ptTagPaddingLeftRight, tagPadding);
        tagCurColor = a.getColor(R.styleable.PagerTag_ptTagCurColor, tagCurColor);
        tagBackColor = a.getColor(R.styleable.PagerTag_ptTagBackColor, tagBackColor);

        a.recycle();

        trianglePaint = new Paint();
        trianglePaint.setAntiAlias(true);
        trianglePaint.setStyle(Style.FILL);
        trianglePaint.setColor(tagCurColor);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);
        dividerPaint.setColor(tagTextColor);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        setBackgroundColor(tagBackColor);

    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.setOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }


    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();

        tabCount = pager.getAdapter().getCount();

        for (int i = 0; i < tabCount; i++) {

            addTextTab(i, pager.getAdapter().getPageTitle(i).toString());

        }

        updateTabStyles();

    }

    private void updateTabStyles() {

        for (int i = 0; i < tabCount; i++) {

            View v = tabsContainer.getChildAt(i);


            if (v instanceof TextView) {

                TextView tab = (TextView) v;
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tagTextSize);
                tab.setTypeface(null, Typeface.BOLD);
                tab.setTextColor(tagTextColor);
                tab.setPadding(v.getPaddingLeft(), dividerPadding, v.getPaddingRight(), dividerPadding);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || tabCount == 0) {
            return;
        }

        final int height = getHeight();
        final int width = getWidth();
        // draw divider

        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height*2/3 - dividerPadding, dividerPaint);
        }
//
//        //draw Under triangle
//        trainglePath.moveTo(0, getHeight());
//        trainglePath.lineTo(getWidth(), getHeight()*2/3);
//        trainglePath.lineTo(getWidth(), getHeight());
//        trainglePath.close();
//        canvas.drawPath(trainglePath, trianglePaint);


        //draw rect
//        RectF rectF = new RectF(0 , 0, width/4, height);
        if(tabCount-1 >= currentPosition) {
            View tab = tabsContainer.getChildAt(currentPosition);
            float rectLeft = tab.getLeft()+tab.getWidth()*currentPositionOffset;
            float rectRight = rectLeft + tab.getWidth();
            canvas.drawRect(rectLeft, height - underlineHeight, rectRight, height, trianglePaint);
        }


    }

    private void addTextTab(final int position, String title) {

        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();

        addTab(position, tab);
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(position);
            }
        });

        tab.setPadding(tagPadding, 0, tagPadding, 0);
        tabsContainer.addView(tab, position, defaultTabLayoutParams);
    }



    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            currentPosition = position;
            currentPositionOffset = positionOffset;

//            Log.d("onPageScrolled", "currentPosition = "+currentPosition);
//            Log.d("onPageScrolled", "currentPositionOffset = "+currentPositionOffset);
            invalidate();

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {
        }

    }

}
