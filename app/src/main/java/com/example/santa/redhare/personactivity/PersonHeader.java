package com.example.santa.redhare.personactivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.santa.redhare.pullrefresh.PullHeader;
import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/5.
 */
public class PersonHeader extends RelativeLayout implements PullHeader {
    private ImageView mReback;
    private ImageView mMore;
    private int circlePadding = 12;
    private int circleDiameter = 30;
    private Drawable mBackground;
    private Drawable mBackCircleBg;
    private Drawable mMoreCircleBg;
    private int mDefAlpha = 50;
    private int mAlpahArea = 100;
    private RelativeLayout mContainer;
    private Drawable mContainerBg;
    private TextView mName;
    private TextView mRebackText;
    private TextView mMoreText;
    private ImageView mUnderLine;


    public PersonHeader(Context context) {
        this(context, null);
    }

    public PersonHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PersonHeader(final Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float density = metrics.density;

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        };

        LayoutParams layoutParams = new LayoutParams((int) (density*circleDiameter), (int) (density*circleDiameter));
        mBackCircleBg = context.getResources().getDrawable(R.drawable.bg_circle);
        mBackCircleBg.setAlpha(mDefAlpha);
        mReback = new ImageView(context);
        mReback.setLayoutParams(layoutParams);
        mReback.setBackground(mBackCircleBg);
        mReback.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mReback.setImageDrawable(context.getResources().getDrawable(R.mipmap.reback_white));
        mReback.setPadding(circlePadding, circlePadding, circlePadding, circlePadding);
        mReback.setOnClickListener(listener);


        mMoreCircleBg = context.getResources().getDrawable(R.drawable.bg_circle);
        mMoreCircleBg.setAlpha(mDefAlpha);
        layoutParams = new LayoutParams((int) (density*circleDiameter), (int) (density*circleDiameter));
        layoutParams.addRule(ALIGN_PARENT_RIGHT);
        mMore = new ImageView(context);
        mMore.setLayoutParams(layoutParams);
        mMore.setBackground(mMoreCircleBg);
        mMore.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mMore.setImageDrawable(context.getResources().getDrawable(R.mipmap.plus_white));
        mMore.setPadding(circlePadding, circlePadding, circlePadding, circlePadding);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_LEFT);
        layoutParams.addRule(CENTER_VERTICAL);
        mRebackText = new TextView(context);
        mRebackText.setLayoutParams(layoutParams);
        mRebackText.setText("返回");
        mRebackText.setTextColor(Color.BLACK);
        mRebackText.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.textsize_big));
        mRebackText.setVisibility(GONE);
        mRebackText.setOnClickListener(listener);


        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_RIGHT);
        layoutParams.addRule(CENTER_VERTICAL);
        mMoreText = new TextView(context);
        mMoreText.setLayoutParams(layoutParams);
        mMoreText.setText("更多");
        mMoreText.setTextColor(Color.BLACK);
        mMoreText.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.textsize_big));
        mMoreText.setVisibility(GONE);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        mName = new TextView(context);
        mName.setLayoutParams(layoutParams);
        mName.setText("傅劲");
        mName.setTextColor(Color.BLACK);
        mName.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.textsize_title));
        mName.setVisibility(GONE);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (density*20));
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        mUnderLine = new ImageView(context);
        mUnderLine.setLayoutParams(layoutParams);
        mUnderLine.setBackgroundColor(context.getResources().getColor(R.color.colorTextMidGray));


        mContainerBg = context.getResources().getDrawable(R.drawable.person_bg);
        mContainerBg.setAlpha(0);
        mContainer = new RelativeLayout(context);
        mContainer.setBackground(mContainerBg);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContainer.setPadding((int) (density*8), (int) (density*8), (int) (density*8), (int) (density*8));
        mContainer.setLayoutParams(layoutParams);
        mContainer.addView(mReback);
        mContainer.addView(mMore);
        mContainer.addView(mName);
        mContainer.addView(mMoreText);
        mContainer.addView(mRebackText);
//        mContainer.addView(mUnderLine);
    }

    public void setName(String name) {
        mName.setText(name);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(mContainer);
    }

    @Override
    public boolean isMoveWithContent() {
        return false;
    }

    @Override
    public boolean isCanRefresh() {
        return false;
    }

    @Override
    public void onPullProgress(float percentUp, float percentDown, int status) {
        mBackCircleBg.setAlpha((int) (percentUp*mAlpahArea + mDefAlpha));
        mMoreCircleBg.setAlpha((int) (percentUp*mAlpahArea + mDefAlpha));
        mContainerBg.setAlpha((int) (255*percentUp));
        if (percentUp>=1.0f) {
            mMore.setVisibility(GONE);
            mReback.setVisibility(GONE);
            mName.setVisibility(VISIBLE);
            mMoreText.setVisibility(VISIBLE);
            mRebackText.setVisibility(VISIBLE);
        } else {
            mName.setVisibility(GONE);
            mMoreText.setVisibility(GONE);
            mRebackText.setVisibility(GONE);
            mMore.setVisibility(VISIBLE);
            mReback.setVisibility(VISIBLE);
        }
    }


    @Override
    public void onRefreshComplete() {}
}
