package com.example.santa.redhare.emoji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.santa.redhare.connfragment.CircleImageView;

/**
 * Created by santa on 16/8/5.
 */
public class FloatCircleImage extends CircleImageView {
    private Paint mPaint = new Paint();
    private int padding = 5;
    private float mDensity;
    private static final int DEFAULT_CIRCLE_BG_LIGHT = 0xFFFDFDFD;

    public FloatCircleImage(Context context) {
        this(context, null);
    }

    public FloatCircleImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatCircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FloatCircleImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mDensity = metrics.density;

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(padding);
        mPaint.setStyle(Paint.Style.STROKE);
        setShadow();
    }


    private void setShadow() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            ShapeDrawable mBgCircle = new ShapeDrawable(new OvalShape());
            ViewCompat.setElevation(this, 10 * mDensity);
            mBgCircle.getPaint().setColor(DEFAULT_CIRCLE_BG_LIGHT);
            setBackgroundDrawable(mBgCircle);
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        canvas.drawCircle(width/2, width/2, width/2-padding, mPaint);

    }
}
