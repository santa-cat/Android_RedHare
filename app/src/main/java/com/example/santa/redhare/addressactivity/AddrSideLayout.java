package com.example.santa.redhare.addressactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by santa on 16/8/6.
 */
public class AddrSideLayout extends RelativeLayout{
    private Context mContext;
    private LinearLayout mTagsContainer;
    private LinearLayout mOutLayout;
    private LinearLayout.LayoutParams mDefLayoutParams;
    private ArrayList<String> mTags;
    private int mDefPadding = 8;
    private RecyclerView mRecyclerView;

    public AddrSideLayout(Context context) {
        this(context, null);
    }

    public AddrSideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddrSideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AddrSideLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mDefLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDefLayoutParams.gravity = Gravity.CENTER;

        float density = context.getResources().getDisplayMetrics().density;


        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(ALIGN_PARENT_RIGHT);
        mOutLayout = new LinearLayout(context);
        int padding = (int) (mDefPadding*density);
        mOutLayout.setPadding(padding, padding, padding, padding);
        mOutLayout.setLayoutParams(layoutParams);
        mOutLayout.setOrientation(LinearLayout.HORIZONTAL);


        mTagsContainer = new LinearLayout(context);
        mTagsContainer.setOrientation(LinearLayout.VERTICAL);
        mTagsContainer.setLayoutParams(mDefLayoutParams);
        mOutLayout.addView(mTagsContainer);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("F");
        tags.add("H");
        tags.add("L");
        tags.add("M");
        tags.add("Q");
        tags.add("W");
        tags.add("Z");
        setTags(tags);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(mOutLayout);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
        Iterator<String> iterator = mTags.iterator();
        while(iterator.hasNext()) {
            addTag(iterator.next());
        }
    }


    private void addTag(String tag) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(mDefLayoutParams);
        textView.setText(tag);
        mTagsContainer.addView(textView);
    }

}
