package com.example.santa.redhare.emoji;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/10.
 */
public class EmojiImageView extends ImageView implements View.OnClickListener{
    private EditText mEditText;
    private View mEmojiPager;
    private InputMethodManager mImm;
    private Drawable mDrawableDef;
    private Drawable mDrawableChecked;

    public EmojiImageView(Context context) {
        this(context, null);
    }

    public EmojiImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public EmojiImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mImm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EmojiImageView, defStyleAttr, defStyleRes);
        int defId = array.getResourceId(R.styleable.EmojiImageView_eiv_srcDef, -1);
        int checkedId = array.getResourceId(R.styleable.EmojiImageView_eiv_srcChecked, -1);
        array.recycle();

        if (-1 != defId) {
            mDrawableDef = getResources().getDrawable(defId);
        } else {
            mDrawableDef = getResources().getDrawable(R.mipmap.ic_launcher);
        }
        if (-1 != checkedId) {
            mDrawableChecked = getResources().getDrawable(checkedId);
        } else {
            mDrawableChecked = getResources().getDrawable(R.mipmap.ic_launcher);
        }


        setImageDrawable(mDrawableDef);
        setOnClickListener(this);
    }




    public void boundEmojiImage(View pager, EditText editText) {
        mEmojiPager = pager;
        mEditText = editText;
    }



    private void softInputShow() {
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        mImm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
    }

    private void softInputHide() {
        mImm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        if (null == mEmojiPager || null == mEditText || null == mImm) {
            return;
        }

        if (mEmojiPager.getVisibility() == View.GONE) {
            setImageDrawable(mDrawableChecked);
            mEmojiPager.setVisibility(View.VISIBLE);
            softInputHide();
        } else {
            setImageDrawable(mDrawableDef);
            mEmojiPager.setVisibility(View.GONE);
            softInputShow();
        }
    }
}
