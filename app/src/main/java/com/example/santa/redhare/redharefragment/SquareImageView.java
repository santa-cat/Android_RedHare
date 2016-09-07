package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by santa on 16/8/3.
 */
public class SquareImageView extends ImageView {

    private static final String TAG = "SquareImageView";

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getDrawable() == null) {
            return;
        }

        int h = this.getMeasuredHeight();
        int w = this.getMeasuredWidth();

        setMeasuredDimension(w, w);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//
//        Path clipPath = new Path();
//        float radius = 20.0f;
//        float padding = radius / 2;
//        int w = this.getWidth();
//        int h = this.getHeight();
//
//        clipPath.addRoundRect(new RectF(padding, padding, w - padding, h - padding), radius, radius, Path.Direction.CW);
//        canvas.clipPath(clipPath);
//        canvas.drawColor(Color.RED);
//
//        super.onDraw(canvas);
//    }
}