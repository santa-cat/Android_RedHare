package com.example.santa.redhare.imageselect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/15.
 */
public class ImageSelectView extends RelativeLayout {

    public ImageView mMainImage;
    private ImageView mSelectImage;
    private int mRadiusSelect = 15;
    private int mMarginSelect = 3;
    private int mResDef = -1;
    private int mResChecked = -1;
    private int mResMain = -1;
    private boolean mFlagSelect = false;

    public ImageSelectView(Context context) {
        this(context, null);
    }

    public ImageSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ImageSelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mRadiusSelect = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mRadiusSelect, dm);
        mMarginSelect = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMarginSelect, dm);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageSelectView);
        mResDef = array.getResourceId(R.styleable.ImageSelectView_isv_selectdef, mResDef);
        mResChecked = array.getResourceId(R.styleable.ImageSelectView_isv_selectchecked, mResChecked);
        mResMain = array.getResourceId(R.styleable.ImageSelectView_isv_imagesrc, mResMain);
        mMarginSelect = (int) array.getDimension(R.styleable.ImageSelectView_isv_selectmargin, mMarginSelect);
        mRadiusSelect = (int) array.getDimension(R.styleable.ImageSelectView_isv_selectradius, mRadiusSelect);
        array.recycle();

        mSelectImage = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(mRadiusSelect, mRadiusSelect);
        layoutParams.setMargins(mMarginSelect, mMarginSelect, mMarginSelect, mMarginSelect);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mSelectImage.setLayoutParams(layoutParams);
        mSelectImage.setImageResource(mResDef);
        mSelectImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelect(v);
            }
        });
        addView(mSelectImage);

        mMainImage = new ImageView(context);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMainImage.setLayoutParams(layoutParams);
        mMainImage.setImageResource(mResMain);
        mMainImage.setBackgroundColor(Color.BLACK);
        mMainImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(mMainImage);

        mSelectImage.bringToFront();
    }


    private void onClickSelect(View v) {
        ImageView view = (ImageView) v;
        if(null == mSelectListener) {
            return;
        }

        if (mFlagSelect) {
            mSelectListener.onCancel();
            view.setImageResource(mResDef);
        } else {
            if (!mSelectListener.onSelect()) {
                return;
            }
            view.setImageResource(mResChecked);

        }

        mFlagSelect = !mFlagSelect;

    }

    private OnSelectListener mSelectListener;

    public void setOnSelectListener(OnSelectListener listener) {
        mSelectListener = listener;
    }


    public interface OnSelectListener{
        boolean onSelect();
        boolean onCancel();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, height);
    }

    public void setCheckImageVisbility(int visbility) {
        mSelectImage.setVisibility(visbility);
    }

    //
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (null != mSelectImage) {
//            measureChildWithMargins(mSelectImage, widthMeasureSpec, 0, heightMeasureSpec, 0);
//        }
//        if (null != mMainImage) {
//            measureChildWithMargins(mMainImage, widthMeasureSpec, 0, heightMeasureSpec, 0);
//        }
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int paddingRight = getPaddingRight();
//        int paddingTop = getPaddingTop();
//        if(null != mSelectImage) {
//            MarginLayoutParams lp = (MarginLayoutParams) mSelectImage.getLayoutParams();
//            int right = getWidth() - lp.rightMargin - paddingRight;
//            int top = paddingTop + lp.topMargin;
//            int left = right - mSelectImage.getMeasuredWidth();
//            int bottom = top + mSelectImage.getMeasuredWidth();
//            mSelectImage.layout(left, top, right, bottom);
//        }
//        if(null != mMainImage) {
//            MarginLayoutParams lp = (MarginLayoutParams) mMainImage.getLayoutParams();
//            int right = getWidth() - lp.rightMargin - paddingRight;
//            int top = paddingTop + lp.topMargin;
//            int left = right - mMainImage.getMeasuredWidth();
//            int bottom = top + mMainImage.getMeasuredWidth();
//            mMainImage.layout(left, top, right, bottom);
//        }
//    }

    public void setImageResource(int resId) {
        if (null != mMainImage) {
            mMainImage.setImageResource(resId);
        }
    }




}
