package com.example.santa.redhare.pullrefresh;

/**
 * Created by santa on 16/6/21.
 */
public class PullIndicator {
    private int mHeaderMinHeight;
    private int mHeaderMaxHeight;
    private int mHeaderHeight;

    private int mHeaderCurHeight;
    //private int mHeaderOffcet = 800;
    private int mHeaderLastPosition;
    private float mResistance = 0.5f;
    private int mLastScrollY = 0;

    public PullIndicator(int defHeight, int maxHeight, int minHeight) {
        mHeaderHeight = mHeaderCurHeight = defHeight;
        mHeaderMaxHeight = maxHeight;
        mHeaderMinHeight = minHeight;
    }

    //如果头部是一起滑动的,需要设置
    public void setHeightFixd() {
        mHeaderMinHeight = mHeaderHeight;
    }

    public boolean isReachMaxHeight(){
        return mHeaderCurHeight >= mHeaderMaxHeight;
    }

    public boolean isReachMinHeight(){
        return mHeaderCurHeight <= mHeaderMinHeight;
    }

    public boolean isOverDefaultHeight(){
        return mHeaderCurHeight > mHeaderHeight;
    }

    public int getHeaderMaxScroll() {
        return mHeaderHeight - mHeaderMinHeight;
    }

    public int getHeaderMinScroll() {
        return mHeaderHeight - mHeaderMaxHeight;
    }

    public int getHeaderOffcet() {
        return mHeaderCurHeight;
    }

    public int getHeaderMinHeight() {
        return mHeaderMinHeight;
    }

    public int getCurScrollY() {
        return mHeaderHeight - mHeaderCurHeight;
    }


    public int getLastScrollY() {
        return  mLastScrollY;
    }

    public void setLastScrollY(int scrollY) {
        mLastScrollY = scrollY;
    }

    public int getHeaderLastPosition() {
        return mHeaderLastPosition;
    }

    public void resetHeaderLastPosition(){
        mHeaderLastPosition = 0;
    }

    public void setHeaderLastPosition(int mHeaderLastPosition) {
        this.mHeaderLastPosition = mHeaderLastPosition;
    }

    //返回实际滑动的距离
    public int moveBy(float deltaY){
        float consumed = deltaY;
        int curPosition = mHeaderCurHeight;
        //向下滑动??
        if(deltaY <= 0){
            //包括本来就已经到达最底端
            if (curPosition - deltaY > mHeaderMaxHeight) {
                consumed = curPosition - mHeaderMaxHeight;
            }
        }else {
            if(curPosition - deltaY < mHeaderMinHeight){
                consumed = curPosition - mHeaderMinHeight ;
            }
        }
        mHeaderCurHeight -= consumed ;
        return (int)consumed;
    }


    //上拉当前的百分比
    //只有上拉超过默认大小的才有用
    public float getPercentUp(){
        if(isOverDefaultHeight()){
            return 0;
        }
        return Math.min(1.0f, Math.abs((mHeaderHeight - mHeaderCurHeight)*1.0f/(mHeaderHeight - mHeaderMinHeight)));
    }

    public float getPercentDown(){
        if(!isOverDefaultHeight()){
            return 0;
        }
        return Math.min(1.0f, Math.abs((mHeaderCurHeight - mHeaderHeight)*1.0f/(mHeaderMaxHeight - mHeaderHeight)));
    }
}
