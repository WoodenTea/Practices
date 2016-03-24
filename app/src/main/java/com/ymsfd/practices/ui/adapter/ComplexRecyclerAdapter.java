package com.ymsfd.practices.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WoodenTea.
 * Date: 2016/3/2
 * Time: 14:36
 */
public abstract class ComplexRecyclerAdapter<T> extends GeneralRecyclerAdapter<T> {
    private final int TYPE_HEADER = Integer.MIN_VALUE;
    private final int TYPE_FOOTER = TYPE_HEADER + 100;
    private final int TYPE_ITEM = 0;

    private List<View> headerViews = null;
    private View footerView = null;

    public ComplexRecyclerAdapter() {
        headerViews = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        int headerNumber = countHeaderNumber();
        if (position < headerNumber) {
            return TYPE_HEADER + position;
        } else if (position < headerNumber + mItemDates.size()) {
            return TYPE_ITEM;
        }

        return TYPE_FOOTER;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < TYPE_HEADER + countHeaderNumber()) {
            return new RecyclerViewHolder(headerViews.get(viewType - TYPE_HEADER));
        } else if (viewType == TYPE_FOOTER) {
            return new RecyclerViewHolder(footerView);
        } else if (viewType == TYPE_ITEM) {
            return super.onCreateViewHolder(parent, viewType);
        }

        throw new RuntimeException("The method onCreateViewHolder() get the wrong viewType, " +
                "you should pass the correct viewType");
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int headerNumber = countHeaderNumber();
        if (position >= headerNumber && position < headerNumber + mItemDates.size()) {
            bindHolder(holder, position - headerNumber, mItemDates.get(position - headerNumber));
        }
    }

    @Override
    public int getItemCount() {
        return countHeaderNumber() + mItemDates.size() + countFooterNumber();
    }

    @Override
    public boolean isEnable(int position) {
        return !positionIsFooter(position) && !positionIsHeader(position);
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position - getHeaderCount());
    }

    @Override
    public void addAll(List<T> items) {
        int position = countHeaderNumber() + mItemDates.size();
        mItemDates.addAll(items);
        notifyItemChanged(position);
    }

    @Override
    public void addItem(T item) {
        int position = countHeaderNumber() + mItemDates.size();
        mItemDates.add(item);
        notifyItemChanged(position);
    }

    public void clear() {
        super.clear();
        headerViews.clear();
        footerView = null;
        notifyDataSetChanged();
    }

    private int countHeaderNumber() {
        return headerViews.size();
    }

    public int getHeaderCount() {
        return headerViews.size();
    }

    public boolean positionIsFooter(int position) {
        if (hasFooter() && position == getItemCount()) {
            return true;
        }

        return false;
    }

    public boolean positionIsHeader(int position) {
        return countHeaderNumber() > position;
    }

    public boolean hasFooter() {
        return footerView != null;
    }

    private int countFooterNumber() {
        return hasFooter() ? 1 : 0;
    }

    public void clearWithoutHeader() {
        super.clear();
        footerView = null;
        notifyDataSetChanged();
    }

    public boolean hasHeader() {
        return countHeaderNumber() > 0;
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
