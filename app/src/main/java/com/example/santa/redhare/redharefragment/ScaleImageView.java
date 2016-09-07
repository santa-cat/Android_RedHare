package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by santa on 16/8/3.
 */
public class ScaleImageView extends ImageView {
    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.FIT_START);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.d("DEBUG", "heightMeasureSpec = "+MeasureSpec.getSize(heightMeasureSpec));
//        Log.d("DEBUG", "widthMeasureSpec = "+MeasureSpec.getSize(widthMeasureSpec));

        if (getDrawable() == null) {
            return;
        }

        Bitmap bitmap = ((BitmapDrawable)getDrawable()).getBitmap();

//        Log.d("DEBUG", "h = "+bitmap.getHeight());
//        Log.d("DEBUG", "w = "+bitmap.getWidth());
        int w = this.getMeasuredWidth();
        int h = this.getMeasuredHeight();

        if (bitmap.getWidth() >= bitmap.getHeight()) {
            w = getMeasuredWidth();
            h = (bitmap.getHeight()*w/bitmap.getWidth());
        } else {
            h = getMeasuredWidth();
            w = (bitmap.getWidth()*h/bitmap.getHeight());
        }
        setMeasuredDimension(w, h);
    }
}
