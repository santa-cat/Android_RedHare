package com.example.santa.redhare.redharefragment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by santa on 16/8/3.
 */


public class AllGridView extends GridView {


    public AllGridView(Context context) {
        super(context);
    }

    public AllGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AllGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AllGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
