package com.example.santa.redhare.chatactivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/6.
 */
public class ChatStroke extends FrameLayout {
    public final static int DIR_LEFT = 0;
    public final static int DIR_RIGHT = 1;
    private int mDirection = DIR_LEFT;
    private int mRadius = 6;
    private int mColor = Color.WHITE;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private Path mPathClip = new Path();
    private int mPaddingEx = 10;


    public ChatStroke(Context context) {
        this(context, null);
    }

    public ChatStroke(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatStroke(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChatStroke(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(0x00000000);
        float density = context.getResources().getDisplayMetrics().density;

        mPaddingEx = (int) (density*mPaddingEx);
        mRadius = (int) (density*mRadius);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChatStroke, defStyleAttr, defStyleRes);
        mColor = array.getColor(R.styleable.ChatStroke_cs_colorBackground, mColor);
        mDirection = array.getInteger(R.styleable.ChatStroke_cs_direction, mDirection);
        array.recycle();

        if (mDirection == DIR_LEFT) {
            setPadding(getPaddingLeft() + mPaddingEx, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        } else {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight() + mPaddingEx, getPaddingBottom());
        }

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPath.reset();
        RectF rectF;
        if (mDirection == DIR_LEFT) {
            rectF = new RectF(mPaddingEx, 0, getWidth(), getHeight());
            mPath.moveTo(0, mPaddingEx*3/2);
            mPath.lineTo(mPaddingEx, mPaddingEx);
            mPath.lineTo(mPaddingEx, mPaddingEx*2);
            mPath.close();
        } else {
            rectF = new RectF(0, 0, getWidth()-mPaddingEx, getHeight());
            mPath.moveTo(getWidth(), mPaddingEx*3/2);
            mPath.lineTo(getWidth() - mPaddingEx, mPaddingEx);
            mPath.lineTo(getWidth() - mPaddingEx, mPaddingEx*2);
            mPath.close();
        }

        canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
        canvas.drawPath(mPath, mPaint);

        mPathClip.reset();
        super.onDraw(canvas);

    }
}
