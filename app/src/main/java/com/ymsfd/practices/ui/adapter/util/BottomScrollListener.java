package com.ymsfd.practices.ui.adapter.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by WoodenTea.
 * Date: 2016/3/3
 * Time: 9:58
 */
public class BottomScrollListener extends RecyclerView.OnScrollListener {
    private static final int LINEAR = 0;
    private static final int GRID = 1;
    private static final int STAGGERED_GRID = 2;

    private int[] lastPositions;
    private int lastVisibleItemPosition;

    private boolean isLoadMore = false;
    private LoadMoreListener listener;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (isLoadMore()) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE &&
                (lastVisibleItemPosition) >= totalItemCount - 1)) {
            if (listener != null) {
                startLoadMore();
                listener.onLoadMore(this);
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (isLoadMore()) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int layoutType;
        if (layoutManager instanceof GridLayoutManager) {
            layoutType = GRID;
        } else if (layoutManager instanceof LinearLayoutManager) {
            layoutType = LINEAR;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            layoutType = STAGGERED_GRID;
        } else {
            throw new RuntimeException(
                    "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, " +
                            "GridLayoutManager and StaggeredGridLayoutManager");
        }

        switch (layoutType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void startLoadMore() {
        isLoadMore = true;
    }

    public void addLoadMoreListener(LoadMoreListener listener) {
        this.listener = listener;
    }

    public void endLoadMore() {
        isLoadMore = false;
    }

    public interface LoadMoreListener {
        void onLoadMore(BottomScrollListener listener);
    }
}
