package com.ymsfd.practices.ui.adapter.util;

import android.support.v7.widget.GridLayoutManager;

import com.ymsfd.practices.ui.adapter.ComplexRecyclerAdapter;

/**
 * Created by WoodenTea.
 * Date: 2016/3/2
 * Time: 17:19
 */
public class GridSpan extends GridLayoutManager.SpanSizeLookup {
    private ComplexRecyclerAdapter adapter;
    private int spanSize = 1;

    public GridSpan(ComplexRecyclerAdapter adapter, int spanSize) {
        this.adapter = adapter;
        this.spanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        if (adapter.hasFooter()) {
            if (position == adapter.getItemCount() - 1) {
                return spanSize;
            }
        }

        if (adapter.hasHeader() && adapter.positionIsHeader(position)) {
            return spanSize;
        }

        return 1;
    }
}
