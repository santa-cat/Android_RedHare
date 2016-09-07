package com.example.santa.redhare.personactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.santa.redhare.emoji.FloatCircleImage;
import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/5.
 */
public class PersonView extends RelativeLayout {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private FloatCircleImage mImageView;
    private int circlePadding = 5;
    private Context mContext;
    private TextView mName;
    private TextView mCompany;
    private TextView mStation;
    private TextView mSay;
    private TextView mFriendsNum;

    public PersonView(Context context) {
        this(context, null);
    }

    public PersonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PersonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(0x00000000);
        mContext = context;

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.FILL);



    }

    private void initCircleView(int width) {
        mImageView = new FloatCircleImage(mContext);
        LayoutParams layoutParams = new LayoutParams(width, width);
        layoutParams.addRule(ALIGN_PARENT_TOP);
        layoutParams.addRule(CENTER_HORIZONTAL);
        mImageView.setLayoutParams(layoutParams);
        mImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sc_person));
        addView(mImageView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (null == mImageView) {
//            initCircleView(MeasureSpec.getSize(widthMeasureSpec)/4);
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        canvas.drawCircle(width/2, 2*width+width/8, 2*width, mPaint);

    }
}
