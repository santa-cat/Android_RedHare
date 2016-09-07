package com.example.santa.redhare.redharefragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.santa.redhare.R;
import com.example.santa.redhare.pullrefresh.PullHeader;
import com.example.santa.redhare.pullrefresh.PullRefreshLayout;

/**
 * Created by santa on 16/8/19.
 */
public class RedHareRefreshView extends RelativeLayout implements PullHeader {

    private ImageView mFoot;
    private ImageView mHeader;
    private float mScreenDensity;
    private int mDefaultSizeOfSun = 50;
    private boolean isCanRefresh = false;
    private ValueAnimator mAnim;
    private TextView mText;
    private final static String STATE_IDEL = "下拉刷新";
    private final static String STATE_READY = "松开立即刷新";
    private final static String STATE_REFRESH = "正在刷新";


    public RedHareRefreshView(Context context) {
        this(context, null);
    }

    public RedHareRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedHareRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RedHareRefreshView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mScreenDensity = metrics.density;

        LayoutParams l = new LayoutParams(getDpSize(mDefaultSizeOfSun), getDpSize(mDefaultSizeOfSun));
        l.addRule(RelativeLayout.CENTER_HORIZONTAL);
        l.addRule(RelativeLayout.CENTER_VERTICAL);

        mHeader = new ImageView(context);
        mHeader.setLayoutParams(l);
        mHeader.setImageResource(R.mipmap.red_header);
        mHeader.setId(View.generateViewId());
        addView(mHeader);

        mFoot = new ImageView(context);
        mFoot.setLayoutParams(l);
        mFoot.setImageResource(R.mipmap.red_foot);
        addView(mFoot);

        mText = new TextView(context);
        l = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.addRule(RelativeLayout.RIGHT_OF, mHeader.getId());
        l.addRule(CENTER_VERTICAL);
        mText.setText(STATE_READY);
        mText.setLayoutParams(l);
        addView(mText);

    }

    private int getDpSize(int px) {
        return (int)(mScreenDensity * px);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initAnim();
    }

    private void initAnim() {
        if (null == mAnim) {
            mHeader.setPivotX(mHeader.getWidth()/2);
            mHeader.setPivotY(mHeader.getHeight()*3/4);
            mAnim = ObjectAnimator.ofFloat(mHeader, "rotation", 0, 10, 0, -10, -20, -10 , 0);
            mAnim.setDuration(1500);
            mAnim.setInterpolator(new LinearInterpolator());
            mAnim.setRepeatMode(ValueAnimator.RESTART);
            mAnim.setRepeatCount(ValueAnimator.INFINITE);
        }
    }

    private void startAnim() {
        if (!mAnim.isStarted()) {
            mAnim.start();
        }
    }

    private void endAnim() {
        if (mAnim.isRunning()) {
            mAnim.cancel();
        }
    }


    @Override
    public boolean isMoveWithContent() {
        return true;
    }

    @Override
    public boolean isCanRefresh() {
        return isCanRefresh;
    }

    @Override
    public void onPullProgress(float percentUp, float percentDown, int status) {
        startAnim();
        if (PullRefreshLayout.STATE_RELEASE == status){
            if (percentDown > 0.9f) {
                mText.setText(STATE_REFRESH);
                isCanRefresh = true;
                return;
            }
        }else {
            isCanRefresh = false;
        }
        if (percentDown >= 0.9f) {
            mText.setText(STATE_READY);
        } else {
            mText.setText(STATE_IDEL);
        }
    }


    @Override
    public void onRefreshComplete() {
        mText.setText(STATE_IDEL);
        endAnim();
    }
}
