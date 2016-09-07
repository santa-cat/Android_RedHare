package com.example.santa.redhare.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.santa.redhare.R;

import org.w3c.dom.Text;

/**
 * Created by santa on 16/8/2.
 */
public class ImageText extends LinearLayout{

    private ImageView mImageView;
    private TextView mTextView;
    private int mColorDef = Color.BLACK;
    private int mColorChecked = Color.BLUE;
    private Drawable mDrawableDef;
    private Drawable mDrawableChecked;
    private int mTextSize = 12;

    public ImageText(Context context) {
        this(context, null);
    }

    public ImageText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ImageText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageText, defStyleAttr, defStyleRes);
        mColorDef = array.getColor(R.styleable.ImageText_it_textColorDef, mColorDef);
        mColorChecked = array.getColor(R.styleable.ImageText_it_textColorChecked, mColorChecked);
        int defId = array.getResourceId(R.styleable.ImageText_it_srcDef, -1);
        int checkedId = array.getResourceId(R.styleable.ImageText_it_srcChecked, -1);
        String text = array.getString(R.styleable.ImageText_it_text);
        array.recycle();

        if (-1 != defId) {
            mDrawableDef = getResources().getDrawable(defId);
        }
        if (-1 != checkedId) {
            mDrawableChecked = getResources().getDrawable(checkedId);
        }

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        mImageView = new ImageView(context);
        mImageView.setLayoutParams(layoutParams);
        mImageView.setImageDrawable(mDrawableDef);
        addView(mImageView);

        mTextView = new TextView(context);
        mTextView.setText(text);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        mTextView.setTextColor(mColorDef);
        mTextView.setLayoutParams(layoutParams);
        addView(mTextView);

//        setOnClickListener(this);
    }

    private void changeState() {
        if (mTextView.getTextColors().getDefaultColor() == mColorDef) {
            mTextView.setTextColor(mColorChecked);
            mImageView.setImageDrawable(mDrawableChecked);
        } else {
            mTextView.setTextColor(mColorDef);
            mImageView.setImageDrawable(mDrawableDef);
        }
    }

    public void setChecked(boolean isChecked) {
        if (isChecked) {
            mTextView.setTextColor(mColorChecked);
            mImageView.setImageDrawable(mDrawableChecked);
        } else {

            mTextView.setTextColor(mColorDef);
            mImageView.setImageDrawable(mDrawableDef);
        }
    }


    public interface ClickListener{
        void onClick();
    }

}
