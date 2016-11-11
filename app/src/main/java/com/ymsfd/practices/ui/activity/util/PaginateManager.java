package com.ymsfd.practices.ui.activity.util;

public class PaginateManager {
    private int requestSize = 10;
    private int responseSize = 0;
    private int paginate = 0;
    private boolean loading = false;

    public boolean hasLoadCompleted() {
        return responseSize < requestSize;
    }

    public int getNextPaginate() {
        return paginate + 1;
    }

    public int getCurrentPaginate() {
        return paginate;
    }

    public boolean isFirstPaginate() {
        return paginate == 1;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(int requestSize) {
        this.requestSize = requestSize;
    }

    public void reset() {
        paginate = 0;
        responseSize = 0;
    }

    public void loadCompleted(int size) {
        if (size > 0) {
            paginate++;
        }

        responseSize = size;
    }

    public boolean getLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
