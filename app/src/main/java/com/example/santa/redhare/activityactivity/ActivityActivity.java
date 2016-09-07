package com.example.santa.redhare.activityactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.santa.redhare.utils.PagerTag;
import com.example.santa.redhare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/8/7.
 */
public class ActivityActivity extends AppCompatActivity {
    private List<View> mViewList ;
    private String[] mTitles = {"详情", "讨论区", "直播间"};
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        initImage();
        initViewPager();
        initReback();

    }

    private void initReback() {
        View reback = findViewById(R.id.activity_reback);
        assert reback != null;
        reback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initImage() {
        ImageView imageView = (ImageView) findViewById(R.id.activity_image);
        Intent intent = getIntent();
        imageView.setImageResource(intent.getIntExtra("imageId", R.mipmap.op1));
    }


    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.activity_viewpager);
        mViewPager.setAdapter(new ActivityAdapter(getViews(), mTitles));

        PagerTag pagerTag = (PagerTag) findViewById(R.id.activity_pagertag);
        pagerTag.setViewPager(mViewPager);
    }

    private List<View> getViews() {
        List<View> views = new ArrayList<>();
        View view1 = LayoutInflater.from(this).inflate(R.layout.pager_activity_detail, null);
        setDatas(view1);
        View view2 = LayoutInflater.from(this).inflate(R.layout.pager_activity_talk, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        TextView view3 = new TextView(this);
        view3.setBackgroundColor(getResources().getColor(R.color.colorBg));
        view3.setTextColor(getResources().getColor(R.color.colorTextMidGray));
        view3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        view3.setGravity(Gravity.CENTER);
        view3.setLayoutParams(layoutParams);
        view3.setText("直播间将于活动开始后开启");
        views.add(view1);
        views.add(view2);
        views.add(view3);
        return views;
    }

    private void setDatas(View view){
        TextView time = (TextView) view.findViewById(R.id.activity_time);
        TextView num = (TextView) view.findViewById(R.id.activity_numOfPeople);
        TextView name = (TextView) view.findViewById(R.id.activity_name);
        TextView station = (TextView) view.findViewById(R.id.activity_station);

        ActivityItem item = ActivityManager.getAtyByImageId(getIntent().getIntExtra("imageId", 0));
        if (null != item) {
            time.setText(item.getTime());
            num.setText(item.getNumOfPeople());
            name.setText(item.getName());
            station.setText(item.getStation());
        }
    }

    private class ActivityAdapter extends PagerAdapter{
        private List<View> mViews;
        private String[] mTitles;

        public ActivityAdapter(List<View> views, String[] titles) {
            mViews = views;
            mTitles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }
    }
}
