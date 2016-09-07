package com.example.santa.redhare.pullrefresh;

/**
 * Created by santa on 16/6/21.
 */
public interface PullHeader {
    boolean isMoveWithContent();

    boolean isCanRefresh();

    void onPullProgress(float percentUp, float percentDown, int status);

    void onRefreshComplete();
}
