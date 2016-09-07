package com.example.santa.redhare.addressactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by santa on 16/8/7.
 */
public class AddrSideIdx extends LinearLayout {
    private Context mContext;
    private LinearLayout mTagsContainer;
    private LinearLayout.LayoutParams mDefLayoutParams;
    private ArrayList<String> mTags;
    private RecyclerView mRecyclerView;
    private OnTouchListener mTouchListener;
    private final static int FLAG_ACTIVE = 1;
    private final static int FLAG_IDEL = 0;
    private MotionEvent mLastEvent;
    private SideRunnable mRunnable;
    private OnSlideListener mListener;

    public AddrSideIdx(Context context) {
        this(context, null);
    }

    public AddrSideIdx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddrSideIdx(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AddrSideIdx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mDefLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDefLayoutParams.gravity = Gravity.CENTER;
        setOrientation(HORIZONTAL);

        mTagsContainer = new LinearLayout(context);
        mTagsContainer.setOrientation(LinearLayout.VERTICAL);
        mTagsContainer.setLayoutParams(mDefLayoutParams);
        addView(mTagsContainer);

        mTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("DEBUG", "onTouch ev = "+event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("DEBUG", ((TextView)v).getText().toString() + " onTouch ");
                    return true;
                }

                return false;
            }
        };

        mRunnable = new SideRunnable();

    }


    private class SideRunnable implements Runnable{
        private int mFlag = FLAG_IDEL;

        @Override
        public void run() {
            try {

                long time = System.currentTimeMillis();
                MotionEvent e1 = MotionEvent.obtain(time, time, MotionEvent.ACTION_UP, mLastEvent.getX(), mLastEvent.getY(), 0);
                dispatchTouchEvent(e1);


                MotionEvent e = MotionEvent.obtain(time, time, MotionEvent.ACTION_DOWN, mLastEvent.getX(), mLastEvent.getY(), 0);
                Log.d("DEBUG", "mLastEvent.getEventTime() = "+mLastEvent.getEventTime());
                Log.d("DEBUG", "mLastEvent.getDownTime() = "+mLastEvent.getDownTime());
                dispatchTouchEvent(e);
                Thread.sleep(100);
                mFlag = FLAG_IDEL;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void start() {
            if (mFlag == FLAG_IDEL) {
                mFlag = FLAG_ACTIVE;
                post(this);
            }
        }

    }

    private void getText(int curY) {
        if (null == mListener) {
            return;
        }
        int count = mTagsContainer.getChildCount();
        int base = mTagsContainer.getTop();
        if (curY < mTagsContainer.getTop()) {
            mListener.onSlideToTop();
            return;
        } else if (curY > mTagsContainer.getBottom()) {
            mListener.onSlideToBottom();
            return;
        }
        for (int i = count-1 ; i>=0; i--) {
            View view = mTagsContainer.getChildAt(i);
            if (base + view.getTop() < curY) {
                mListener.onSlide(((TextView)view).getText().toString());
                break;
            }
        }
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mLastEvent = event;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                getText((int) event.getY());
                break;
        }
        return true;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
        Iterator<String> iterator = mTags.iterator();
        while(iterator.hasNext()) {
            addTag(iterator.next());
        }
    }


    private void addTag(String tag) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(mDefLayoutParams);
        textView.setText(tag);
//        textView.setOnTouchListener(mTouchListener);
        mTagsContainer.addView(textView);
    }


    public void setOnSlideListener(OnSlideListener listener) {
        mListener = listener;
    }

    public interface OnSlideListener {
        void onSlide(String text);
        void onSlideToTop();
        void onSlideToBottom();
    }



}
