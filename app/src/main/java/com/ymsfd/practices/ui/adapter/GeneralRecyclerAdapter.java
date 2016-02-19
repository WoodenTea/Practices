package com.ymsfd.practices.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WoodenTea.
 * Date: 2016/2/19
 * Time: 11:55
 */
public abstract class GeneralRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final int TYPE_HEADER = -1;
    private final int TYPE_ITEM = 0;
    protected final List<T> mItemDates;
    private final LayoutInflater mInflater;

    public GeneralRecyclerAdapter(Context context) {
        mItemDates = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (!isHeaderExist()) {
            return super.getItemViewType(position);
        }

        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mItemDates.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        if (viewType == TYPE_HEADER) {
            layoutId = getHeaderLayoutId();
            if (layoutId == -1)
                throw new RuntimeException("The method getHeaderLayoutId() return the wrong id, " +
                        "you should override it and return the correct id");
        } else {
            layoutId = getItemLayoutId(viewType);
        }

        return new RecyclerViewHolder(mInflater.inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (!isHeaderExist()) {
            bindHolder(holder, position, mItemDates.get(position));
        } else {
            if (position != 0) {
                bindHolder(holder, position - 1, mItemDates.get(position - 1));
            }
        }
    }

    public void addAll(List<T> items) {
        mItemDates.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        int position = mItemDates.size();
        mItemDates.add(item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mItemDates.remove(position);
        notifyItemRemoved(position);
    }

    protected int getHeaderLayoutId() {
        return -1;
    }

    public boolean isHeaderExist() {
        return getHeaderLayoutId() != -1;
    }

    abstract protected int getItemLayoutId(int viewType);

    abstract protected void bindHolder(RecyclerViewHolder holder, int position, T item);
}
