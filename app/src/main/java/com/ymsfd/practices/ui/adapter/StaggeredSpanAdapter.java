package com.ymsfd.practices.ui.adapter;

import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by WoodenTea.
 * Date: 2016/3/2
 * Time: 17:55
 */
public abstract class StaggeredSpanAdapter<T> extends ComplexRecyclerAdapter<T> {

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (hasFooter() && position == getItemCount() - 1) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager
                    .LayoutParams) holder.itemView.getLayoutParams();
            if (params != null)
                params.setFullSpan(true);
        }

        if (hasHeader() && positionIsHeader(position)) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager
                    .LayoutParams) holder.itemView.getLayoutParams();
            if (params != null)
                params.setFullSpan(true);
        }
    }
}
