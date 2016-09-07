package com.example.santa.redhare.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/11.
 */
public class UnderlineText extends TextView {
    private int underlineHeight = 10;
    private Paint mPaint = new Paint();
    private int mColor = Color.BLACK;
    private boolean isChecked = false;

    public UnderlineText(Context context) {
        this(context, null);
    }

    public UnderlineText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlineText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public UnderlineText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UnderlineText);
        mColor = a.getColor(R.styleable.UnderlineText_ut_underlineColor, mColor);
        underlineHeight = a.getDimensionPixelSize(R.styleable.UnderlineText_ut_underlineHeight, underlineHeight);
        a.recycle();



        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = getPaddingLeft();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight();
        int top = bottom - underlineHeight;

        if (isChecked) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }


    public void setUnChecked() {
        isChecked = false;
        invalidate();
    }

    public void setChecked() {
        isChecked = true;
        invalidate();
    }


}
