package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/2.
 */
public class ShrinkText extends LinearLayout {
    private TextView mContent;
    private TextView mDetail;
    private int mTextColor = Color.BLUE;
    private int mDetailColor = Color.RED;
    private String mDetailText = "查看详情";
    private String mDetailTextClicked = "收起";
    private int mTextSize = 16;
    private int mMaxLines = 6;

    private Rearrangable mRearrangable;
    private final static int STATE_IDEL = 0;
    private final static int STATE_DETAIL = 1;
    private int mState = STATE_IDEL;

    public ShrinkText(Context context) {
        this(context, null);
    }

    public ShrinkText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShrinkText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ShrinkText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShrinkText, defStyleAttr, defStyleRes);
        mDetailColor = array.getColor(R.styleable.ShrinkText_st_textColorDetail, mDetailColor);
        mTextColor = array.getColor(R.styleable.ShrinkText_st_textColor, mTextColor);
        String content = array.getString(R.styleable.ShrinkText_st_text);
        mMaxLines = array.getInteger(R.styleable.ShrinkText_st_maxLines, mMaxLines);
        array.recycle();

        LayoutParams layoutParams = new  LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContent = new TextView(context);
        mContent.setLayoutParams(layoutParams);
        mContent.setText(content);
        mContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        mContent.setTextColor(mTextColor);
        mContent.setMaxLines(Integer.MAX_VALUE);
//        mContent.se
        addView(mContent);

        Log.d("DEBUG", "num = "+mContent.getLineCount());

//        layoutParams.setMargins(0, 0, 0, 0);
        mDetail = new TextView(context);
        mDetail.setLayoutParams(layoutParams);
        mDetail.setText(mDetailText);
        mDetail.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        mDetail.setTextColor(mDetailColor);
        mDetail.setVisibility(GONE);
        addView(mDetail);

        mRearrangable = new Rearrangable();
    }


    //TODO:加上入参是否展开
    public void setText(CharSequence text) {
        initView();
        if (null != mContent) {
            mContent.setText(text);
        }
    }


    private void initView() {
        mState = STATE_IDEL;
        mContent.setMaxLines(Integer.MAX_VALUE);
        mDetail.setVisibility(GONE);
        mDetail.setText(mDetailText);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mState == STATE_IDEL) {
            mRearrangable.start();
        }
    }

    private boolean isBeyondMaxLines() {
        return mContent.getLineCount() > mMaxLines;
    }

    private boolean isAlreadyToDetail() {
        return mDetail.getText() == mDetailTextClicked;
    }

    private void rearrange() {
        if (isBeyondMaxLines()) {
            mState = STATE_DETAIL;
            mContent.setMaxLines(mMaxLines);
            mContent.setEllipsize(TextUtils.TruncateAt.END);
            mDetail.setVisibility(VISIBLE);
            mDetail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeState(v);
                }
            });
        }
        requestLayout();
    }

    private void changeState(View v) {
        if (mContent.getMaxLines() == Integer.MAX_VALUE) {
            ((TextView) v).setText(mDetailText);
            mContent.setMaxLines(mMaxLines);
        } else {
            ((TextView) v).setText(mDetailTextClicked);
            mContent.setMaxLines(Integer.MAX_VALUE);
        }
        mContent.requestLayout();
    }

    public void setMovementMethod(MovementMethod method) {
        mContent.setMovementMethod(method);
    }

    public float getTextSize() {
        return mContent.getTextSize();
    }

    public class Rearrangable implements Runnable {
        @Override
        public void run() {
            rearrange();

        }

        public void start() {
            post(this);
        }
    }



}
