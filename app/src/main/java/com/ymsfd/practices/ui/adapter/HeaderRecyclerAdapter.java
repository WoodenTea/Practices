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
        if (viewType < TYPE_HEADER + countHeaderNumber()) {
            return new RecyclerViewHolder(headerViews.get(viewType - TYPE_HEADER));
        } else if (viewType < TYPE_FOOTER + countFooterNumber()) {
            return new RecyclerViewHolder(footerView);
        } else if (viewType >= TYPE_ITEM) {
            return adapter.onCreateViewHolder(parent, viewType);
        }

        throw new RuntimeException("The method onCreateViewHolder() get the wrong viewType, " +
                "you should pass the correct viewType");
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int headerNumber = countHeaderNumber();
        if (position >= headerNumber && position < headerNumber + adapter.getItemCount()) {
            adapter.onBindViewHolder(holder, position - headerNumber);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int headerNumber = countHeaderNumber();
        if (position < headerNumber) {
            return TYPE_HEADER + position;
        } else if (position < headerNumber + adapter.getItemCount()) {
            return TYPE_ITEM + adapter.getItemViewType(position - headerNumber);
        }

        return TYPE_FOOTER + position - headerNumber - adapter.getItemCount();
    }

    @Override
    public int getItemCount() {
        return countHeaderNumber() + adapter.getItemCount() + countFooterNumber();
    }

    private int countHeaderNumber() {
        return headerViews.size();
    }

    private int countFooterNumber() {
        return hasFooter() ? 1 : 0;
    }

    public boolean hasFooter() {
        return footerView != null;
    }

    public boolean hasHeader() {
        return countHeaderNumber() > 0;
    }

    public boolean positionIsHeader(int position) {
        return countHeaderNumber() > position;
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
