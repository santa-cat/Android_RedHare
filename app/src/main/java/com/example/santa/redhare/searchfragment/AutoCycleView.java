package com.example.santa.redhare.searchfragment;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santa on 16/6/28.
 */
public class AutoCycleView extends RelativeLayout {
    private final static String CYCLE_VIEW= "AtuoCycleView";
    private List<View> mViewList ;
    private ViewPager mViewpage;
    private Context mContext;
    private CyclePagerAdapter mAdapter;
    private CycleRunable mCycleRunable = new CycleRunable();

    private CycleIndexView mCycleIdxView;

    public AutoCycleView(Context context) {
        super(context);
        init(context);
    }

    public AutoCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoCycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){

        mViewList = new ArrayList<View>();

        mViewpage = new ViewPager(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewpage.setLayoutParams(layoutParams);
        addView(mViewpage);
        mViewpage.setAdapter(mAdapter = new CyclePagerAdapter());
        mViewpage.addOnPageChangeListener(new CycleViewChangeListener());
//        mViewpage.setCurrentItem(1);

        mCycleIdxView = new CycleIndexView(context);
        addView(mCycleIdxView);

    }

    public void setViewList(List<View> viewList) {
        mViewList = viewList;
        //增加循环项

        mViewpage.setCurrentItem(1);
        mAdapter.notifyDataSetChanged();

        createIdxView(viewList.size() - 2);
//
//        int itemStart = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2) % viewList.size();
//        mViewpage.setCurrentItem(itemStart);
    }



    public void createIdxView(int size) {
        if (null != mCycleIdxView) {
            mCycleIdxView.setViewCount(size);
            LayoutParams layoutParams = new LayoutParams(mCycleIdxView.getCycleIdxViewWidth(), mCycleIdxView.getCycleIdxViewHeight());
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mCycleIdxView.setLayoutParams(layoutParams);
        }
    }

    public void startCycle(long time){
        mCycleRunable.setCycleTime(time);
        mCycleRunable.startCycle();
    }

    public void startCycle(){
        mCycleRunable.startCycle();
    }

    public class CyclePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            position = position % mViewList.size();
//
//            if (position<0){
//                position = mViewList.size()+position;
//            }
//            View view = mViewList.get(position);
//            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//            ViewParent vp =view.getParent();
//            if (vp!=null){
//                ViewGroup parent = (ViewGroup)vp;
//                parent.removeView(view);
//            }
//            container.addView(view);
//            //add listeners here if necessary
//            return view;
//
//            Log.d(CYCLE_VIEW, "position is "+position);
            ((ViewPager) container).addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN) {
            //暂停自动滚动
            mCycleRunable.puaseCycle();

        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            //启动自动滚动
            mCycleRunable.startCycle();
        }
        return super.dispatchTouchEvent(ev);
    }

    public void changePager() {
        if (mViewList.isEmpty()){
            Log.e(CYCLE_VIEW, "data is empty!");
            throw new IllegalStateException("data is empty!");
        }
        int item = Math.min(mViewpage.getCurrentItem(), mViewList.size() - 1);
        //mViewpage.setCurrentItem(++item);


        if(item == mViewList.size() - 1) {
            mViewpage.setCurrentItem(0);
        } else {
            mViewpage.setCurrentItem(++item);
        }

    }

    class CycleRunable implements Runnable{
        private boolean isAnimotion = false;
        private long mDefaultCycleTime = 5000L;
        private long mLastTime;

        public void setCycleTime(long time) {
            mDefaultCycleTime = time;
        }

        @Override
        public void run() {
            if (isAnimotion) {
                long now = SystemClock.currentThreadTimeMillis();
                if (now - this.mLastTime >= this.mDefaultCycleTime) {
                    changePager();
                    this.mLastTime = now;
                }

                AutoCycleView.this.post(this);
            }
        }

        public void startCycle() {
            if(this.isAnimotion) {
                return;
            }
            this.mLastTime = SystemClock.currentThreadTimeMillis();
            this.isAnimotion = true;
            AutoCycleView.this.post(this);
        }

        public void puaseCycle() {
            this.isAnimotion = false;
        }
    }


    class CycleViewChangeListener implements ViewPager.OnPageChangeListener{
        //用户自己
        private boolean needJumpToRealPager = true;

        public void setNeedJumpFlag(boolean isNeedJump) {
            needJumpToRealPager = isNeedJump;
        }

        public boolean getNeedJumpFlag() {
            return needJumpToRealPager;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            Log.d(CYCLE_VIEW, "onPageSelected position is "+position);

            if (null != mCycleIdxView && mViewpage.getCurrentItem() != 0 && mViewpage.getCurrentItem() !=  mViewList.size()-1) {
                mCycleIdxView.setCurIndex(position - 1);
            }
            //如果是头或者尾,等滑动
            if (mViewpage.getCurrentItem() == 0 && getNeedJumpFlag()) {
                setNeedJumpFlag(false);
                mViewpage.setCurrentItem(mViewList.size()-1, false);
                mViewpage.setCurrentItem(mViewList.size()-2);
            } else if (mViewpage.getCurrentItem() == mViewList.size()-1 && getNeedJumpFlag()){
                setNeedJumpFlag(false);
                mViewpage.setCurrentItem(0, false);
                mViewpage.setCurrentItem(1);
            } else {
                setNeedJumpFlag(true);
            }
//            mViewpage

        }

        @Override
        public void onPageScrollStateChanged(int state) {
//            Log.d(CYCLE_VIEW, "onPageScrollStateChanged state is "+state);

        }
    }



}
