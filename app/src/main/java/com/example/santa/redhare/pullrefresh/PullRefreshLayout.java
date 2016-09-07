package com.example.santa.redhare.pullrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/6/21.
 */
public class PullRefreshLayout extends ViewGroup {
    private final String DEBUG_FLAG = "PullRefreshLayout";
    private View mHeaderView;
    private View mContentView;
    private PullHeader mPullHeaderView;
    private PullHandler mPullHandler;
    private float mDensity;

    //滑动相关
    private int mActivePointerId;
    private PullIndicator mIndicator;
    private OverScroller mScroller;
    public static final int STATE_IDLE = 0;
    public static final int STATE_DRAGE = 1;
    public static final int STATE_FLING = 2;
    public static final int STATE_RELEASE = 3;
    private int mStatus;
    private boolean isFlingConsumed = false;

    private final int RELEASE_TIME = 200;

    private int mDefaultHeight = 100;
    private int mMaxHeight = 280;
    private int mMinHeight = 0;
    private MotionEvent mLastMoveEvent;

    private boolean isDragged = false;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    public PullRefreshLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){

        mDensity = context.getResources().getDisplayMetrics().density;
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PullRefreshLayout, defStyleAttr, defStyleRes);
        mDefaultHeight = array.getDimensionPixelSize(R.styleable.PullRefreshLayout_default_height, calPxFromDp(mDefaultHeight));
        mMaxHeight = array.getDimensionPixelSize(R.styleable.PullRefreshLayout_max_height, calPxFromDp(mMaxHeight));
        mMinHeight = array.getDimensionPixelSize(R.styleable.PullRefreshLayout_min_height, calPxFromDp(mMinHeight));
        array.recycle();

        //TODO:增加增值器
        mScroller = new OverScroller(context);

        mIndicator = new PullIndicator(mDefaultHeight, mMaxHeight, mMinHeight);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        //应该增加一个上拉加载
        if(count > 2) {
            Log.e("refresh", "PullRefreshLayout only can host 2 elements");
            throw new IllegalStateException("PullRefreshLayout only can host 2 elements");
        } else if (count == 2){
            View child;

            for(int i = 0; i<count; i++) {
                child = getChildAt(i);
                if (child instanceof PullHeader) {
                    mHeaderView = child;
                    mPullHeaderView = (PullHeader) mHeaderView;
                } else {
                    mContentView = child;
                }
            }
        }
        if(mPullHeaderView.isMoveWithContent()){
            mIndicator.setHeightFixd();
            assert mHeaderView != null;
            mHeaderView.bringToFront();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(null != mHeaderView){
            measureChildWithMargins(mHeaderView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        if (null != mContentView){
            measureChildWithMargins(mContentView, widthMeasureSpec, 0, heightMeasureSpec, mIndicator.getHeaderMinHeight());
        }
    }



    public void setPullHandler(PullHandler handler) {
        mPullHandler = handler;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int offsetContent = mIndicator.getHeaderOffcet();
        int offsetHeader = mPullHeaderView.isMoveWithContent() ? (mHeaderView.getMeasuredHeight() - mIndicator.getHeaderOffcet()): 0;
        int paddingLeft   = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        if(null != mHeaderView){
            MarginLayoutParams lp = (MarginLayoutParams) mHeaderView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin - offsetHeader;
            final int right = left + mHeaderView.getMeasuredWidth();
            final int bottom = top + mHeaderView.getMeasuredHeight();
            mHeaderView.layout(left, top, right, bottom);
        }

        if(null != mContentView){
            MarginLayoutParams lp = (MarginLayoutParams)mContentView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin + offsetContent;
            final int right = left + mContentView.getMeasuredWidth();
            final int bottom = top + mContentView.getMeasuredHeight();
            mContentView.layout(left, top, right, bottom);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mLastMoveEvent = ev;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScroller.forceFinished(true);
                mIndicator.setHeaderLastPosition( (int) ev.getY() );
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                setStatus(STATE_DRAGE);
                obtainVelocityTracker(ev);
                int curPosition = (int)(ev.getY());
                int offsetY = mIndicator.getHeaderLastPosition() - curPosition;
                boolean isBeyondTop = isContentBeyondTop(mContentView);
                //up
                mIndicator.setHeaderLastPosition(curPosition);
                if (!mIndicator.isReachMinHeight() && offsetY>0 || !isBeyondTop && offsetY < 0) {
                    isDragged = (Math.abs(offsetY) > 5) || isDragged;
                    moveBy(offsetY);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d(DEBUG_FLAG, "up dis");
                Log.d(DEBUG_FLAG, "uisDragged = "+isDragged);
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, mActivePointerId);
                if ((Math.abs(initialVelocity) > mMinimumVelocity) && (!mIndicator.isReachMinHeight() && initialVelocity>0 || !isContentBeyondTop(mContentView) && initialVelocity < 0)) {
                    fling(initialVelocity);
                } else {
                    release();
                }
//                release();

                releaseVelocityTracker();
                if (isDragged) {
                    isDragged = false;
                    return true;
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    private void obtainVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    //content 是否超出顶端
    private boolean isContentBeyondTop(View view) {
//        if (view instanceof AbsListView) {
//            final AbsListView absListView = (AbsListView) view;
//            return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0
//                    || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
//        } else {
//            return view.getScrollY() > 0;
//        }
        return view.canScrollVertically(-1);
    }


    public int calPxFromDp(int px){
        return (int)(px * mDensity);
    }


    public int moveBy(float deltaY){
        int consumed = mIndicator.moveBy(deltaY);
//        Log.d(DEBUG_FLAG, "consumed = "+consumed);

        if (0 == consumed) {
            sendDownEvent();
        }
        if(null != mContentView) {
            mContentView.offsetTopAndBottom(-consumed);
        }

        if (null != mHeaderView) {
            mPullHeaderView.onPullProgress(mIndicator.getPercentUp(), mIndicator.getPercentDown(), mStatus);
            if (mPullHeaderView.isMoveWithContent()){
                mHeaderView.offsetTopAndBottom(-consumed);
                invalidate();
            }
//            Log.d(DEBUG_FLAG, "percent is "+ mIndicator.getPercent());
//            Log.d(DEBUG_FLAG, "mStatus is "+ mStatus);

        }


        return consumed;
    }


    private void sendCancelEvent() {

        // The ScrollChecker will update position and lead to send cancel event when mLastMoveEvent is null.
        // fix #104, #80, #92
        if (mLastMoveEvent == null) {
            return;
        }
        MotionEvent last = mLastMoveEvent;
        MotionEvent e = MotionEvent.obtain(last.getDownTime(), last.getEventTime() + ViewConfiguration.getLongPressTimeout(), MotionEvent.ACTION_CANCEL, last.getX(), last.getY(), last.getMetaState());
        super.dispatchTouchEvent(e);
    }

    private void sendDownEvent() {

        final MotionEvent last = mLastMoveEvent;
        MotionEvent e = MotionEvent.obtain(last.getDownTime(), last.getEventTime(), MotionEvent.ACTION_DOWN, last.getX(), last.getY(), last.getMetaState());
        super.dispatchTouchEvent(e);

    }
    private void sendMoveEvent(float x, float y) {

        final MotionEvent last = mLastMoveEvent;
        MotionEvent e = MotionEvent.obtain(last.getDownTime(), last.getEventTime(), MotionEvent.ACTION_MOVE, x, y, last.getMetaState());
        super.dispatchTouchEvent(e);

    }

    public void refreshComplete() {
        if (null != mPullHeaderView) {
            mPullHeaderView.onRefreshComplete();
        }
        resetBackAfterTouch();
    }

    public void release(){
        setStatus(STATE_RELEASE);

        //如果是header和content一起的情况,需要先设置一下是否能刷新的标志
//        if(mPullHeaderView.isMoveWithContent()) {
        mPullHeaderView.onPullProgress(mIndicator.getPercentUp(), mIndicator.getPercentDown(), mStatus);
//        }

        if(mPullHeaderView.isCanRefresh() && null != mPullHandler) {
            mPullHandler.onRefreshBegin(this);
        }
        if(!mPullHeaderView.isMoveWithContent() || !mPullHeaderView.isCanRefresh()) {
            resetBackAfterTouch();
        }
    }

    @Override
    public void computeScroll() {
        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 必须调用该方法，否则不一定能看到滚动效果

            int curY = mScroller.getCurrY();
            int delteY = curY - mIndicator.getLastScrollY();
            mIndicator.setLastScrollY(curY);
//            Log.d(DEBUG_FLAG, "mScroller.getCurrY() = "+mScroller.getCurrY());
//            Log.d(DEBUG_FLAG, "delteY = "+delteY);
            moveBy(delteY);
            postInvalidate();
        }else {
            resetBackAfterFling();
            setStatus(STATE_IDLE);

        }

    }

    private void resetBackAfterFling() {
        if(mIndicator.isOverDefaultHeight() && mStatus == STATE_FLING){
            mScroller.abortAnimation();
            resetBackAfterTouch();
        }
    }

    private void resetBackAfterTouch(){

        if (mIndicator.isOverDefaultHeight()){
            int curScrollY = mIndicator.getCurScrollY();
            mIndicator.setLastScrollY(-1);
            //start 入参意思是从哪个位置开始,滚动多少偏移,然后computeScroll中每次得到getCurrY
            mScroller.startScroll(0, 0, 0, -curScrollY, RELEASE_TIME);
            invalidate();
        } else {
            setStatus(STATE_IDLE);
        }
    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        @SuppressWarnings({"unused"})
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public void setStatus(int status) {
        mStatus = status;
    }


    private boolean nestedFling(int velocityY) {
        final boolean canFling = (!mIndicator.isReachMinHeight() && velocityY > 0) ||
                (!mIndicator.isReachMaxHeight() && velocityY < 0);
        //Log.d(DEBUG_FLAG, "canFling" + canFling);
        if(canFling) {
            fling(velocityY);
        }
        return canFling;
    }


    public void fling(int velocityY) {
        Log.d(DEBUG_FLAG, "velocityY" + velocityY);
        setStatus(STATE_FLING);
        mScroller.abortAnimation();
        int curScrollY = mIndicator.getCurScrollY();
        int maxScrollY = mIndicator.getHeaderMaxScroll();
        int minScrollY = mIndicator.getHeaderMinScroll();
        mIndicator.setLastScrollY(curScrollY);
        mScroller.fling(0, curScrollY, 0, velocityY, 0,  0, 0, maxScrollY);
        invalidate();
    }
}