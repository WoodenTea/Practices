package com.ymsfd.practices.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by WoodenTea.
 * Date: 2016/3/2
 * Time: 14:36
 */
public abstract class ComplexRecyclerAdapter<T> extends GeneralRecyclerAdapter<T> {

    private static final int MAX_HEADER = 10;
    private static final int MAX_FOOTER = MAX_HEADER;
    private static final int HEADER = Integer.MIN_VALUE;
    private static final int FOOTER = HEADER + MAX_HEADER;
    private static final int ITEM = 0;
    private static final int PIN_ITEM = 1;

    @Override
    public int getItemViewType(int position) {
        if (countHeaderLength() > position) {
            return HEADER + position;
        } else if (position < countHeaderLength() + mItemDates.size()) {
            return pinItemAt(position) ? PIN_ITEM : ITEM;
        }
        return FOOTER;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("RecyclerAdapter", "ViewType->" + viewType);
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        if (viewType < HEADER + countHeaderLength()) {
            View view = inflater.inflate(getHeaderLayoutId()[viewType - HEADER], parent, false);
            return new RecyclerViewHolder(view);
        } else if (viewType < FOOTER + countFooterLength()) {
            View view = inflater.inflate(getFooterLayoutId()[viewType - FOOTER], parent, false);
            return new RecyclerViewHolder(view);
        } else if (viewType == PIN_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(getPinItemLayoutId(), parent, false);
            return new RecyclerViewHolder(view);
        } else if (viewType == ITEM) {
            return super.onCreateViewHolder(parent, viewType);
        }

        throw new RuntimeException("The method onCreateViewHolder() get the wrong viewType, " +
                "you should pass the correct viewType");
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType < FOOTER) {
            bindHeaderHolder(holder, position, viewType - HEADER);
        } else if (viewType < FOOTER + MAX_FOOTER) {
            bindFooterHolder(holder, position, viewType - FOOTER);
        } else if (viewType == PIN_ITEM) {
            bindPinItemHolder(holder, position);
        } else {
            int headerNumber = countHeaderLength();
            bindHolder(holder, position - headerNumber, mItemDates.get(position - headerNumber));
        }
    }

    @Override
    public int getItemCount() {
        return countHeaderLength() + mItemDates.size() + countFooterLength();
    }

    @Override
    public boolean isEnable(int position) {
        return !positionIsFooter(position) && !positionIsHeader(position);
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position - countHeaderLength());
    }

    @Override
    public void addAll(List<T> items) {
        int position = countHeaderLength() + mItemDates.size();
        mItemDates.addAll(items);
        notifyItemChanged(position);
    }

    @Override
    public void addItem(T item) {
        int position = countHeaderLength() + mItemDates.size();
        mItemDates.add(item);
        notifyItemChanged(position);
    }

    public void clear() {
        super.clear();
        notifyDataSetChanged();
    }

    public void bindHeaderHolder(RecyclerViewHolder holder, int position, int index) {

    }

    public void bindPinItemHolder(RecyclerViewHolder holder, int position) {

    }

    public void bindFooterHolder(RecyclerViewHolder holder, int position, int index) {

    }

    public boolean pinItemAt(int position) {
        return false;
    }

    public int[] getHeaderLayoutId() {
        return null;
    }

    public int getPinItemLayoutId() {
        return 0;
    }

    public int[] getFooterLayoutId() {
        return null;
    }

    public void addItem(int position, T item) {
        mItemDates.add(position, item);
        notifyItemInserted(position);
    }

    private int countHeaderLength() {
        int length = getHeaderLayoutId() == null ? 0 : getHeaderLayoutId().length;
        if (length > MAX_HEADER) {
            throw new RuntimeException("The header length is too large.");
        }

        return length;
    }

    public boolean positionIsFooter(int position) {
        return hasFooter() && position == getItemCount();

    }

    public boolean positionIsHeader(int position) {
        return countHeaderLength() > position;
    }

    public boolean hasFooter() {
        return getFooterLayoutId() != null;
    }

    private int countFooterLength() {
        int length = hasFooter() ? getFooterLayoutId().length : 0;
        if (length > MAX_FOOTER) {
            throw new RuntimeException("The footer length is too large.");
        }

        return length;
    }

    public void clearWithoutHeader() {
        super.clear();
        notifyDataSetChanged();
    }

    public boolean hasHeader() {
        return countHeaderLength() > 0;
    }
}
