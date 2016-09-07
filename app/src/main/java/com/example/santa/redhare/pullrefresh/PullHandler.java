package com.example.santa.redhare.pullrefresh;

/**
 * Created by santa on 16/6/24.
 */
public interface PullHandler {
    public void onRefreshBegin(final PullRefreshLayout layout);

    public void onRefreshFinshed();
}
