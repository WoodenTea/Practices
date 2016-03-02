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
    protected final List<T> mItemDates;

    private final LayoutInflater mInflater;

    public GeneralRecyclerAdapter(Context context) {
        mItemDates = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(mInflater.inflate(getItemLayoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindHolder(holder, position, mItemDates.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemDates.size();
    }

    abstract protected void bindHolder(RecyclerViewHolder holder, int position, T item);

    abstract protected int getItemLayoutId();

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
}
