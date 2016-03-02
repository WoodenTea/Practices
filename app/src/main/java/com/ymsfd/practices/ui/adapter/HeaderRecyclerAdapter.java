package com.ymsfd.practices.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WoodenTea.
 * Date: 2016/3/2
 * Time: 14:36
 */
public class HeaderRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final int TYPE_HEADER = Integer.MIN_VALUE;
    private final int TYPE_FOOTER = TYPE_HEADER + 100;
    private final int TYPE_ITEM = 0;

    private List<View> headerViews = null;
    private View footerView = null;

    private GeneralRecyclerAdapter adapter;

    public HeaderRecyclerAdapter(GeneralRecyclerAdapter adapter) {
        this.adapter = adapter;
        headerViews = new ArrayList<>();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < TYPE_HEADER + headerCount()) {
            return new RecyclerViewHolder(headerViews.get(viewType - TYPE_HEADER));
        } else if (viewType < TYPE_FOOTER + footerCount()) {
            return new RecyclerViewHolder(footerView);
        } else if (viewType >= TYPE_ITEM) {
            return adapter.onCreateViewHolder(parent, viewType);
        }

        throw new RuntimeException("The method onCreateViewHOlder() get the wrong viewType, " +
                "you should pass the correct viewType");
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (position >= headerCount() && position < headerCount() + adapter.getItemCount()) {
            adapter.onBindViewHolder(holder, position - headerCount());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < headerCount()) {
            return TYPE_HEADER + position;
        } else if (position < headerCount() + adapter.getItemCount()) {
            return TYPE_ITEM + adapter.getItemViewType(position - headerCount());
        }

        return TYPE_FOOTER + position - headerCount() - adapter.getItemCount();
    }

    @Override
    public int getItemCount() {
        return headerCount() + adapter.getItemCount() + footerCount();
    }

    private int headerCount() {
        return headerViews.size();
    }

    private int footerCount() {
        return hasFooter() ? 1 : 0;
    }

    public boolean hasFooter() {
        return footerView != null;
    }

    public boolean hasHeader() {
        return headerCount() > 0;
    }

    public boolean positionIsHeader(int position) {
        return headerCount() > position;
    }

    public void addHeaderView(View view) {
        int position = headerViews.size();
        headerViews.add(view);
        notifyItemInserted(position);
    }

    public void addFooterView(View view) {
        int position = getItemCount();
        footerView = view;
        if (hasFooter()) {
            notifyItemChanged(position);
        } else {
            notifyItemInserted(position);
        }
    }

}
