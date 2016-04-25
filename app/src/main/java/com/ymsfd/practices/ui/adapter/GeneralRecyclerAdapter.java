package com.ymsfd.practices.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WoodenTea.
 * Date: 2016/2/19
 * Time: 11:55
 */
public abstract class GeneralRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>
        implements RecyclerViewHolder.OnItemClickListener {
    protected final List<T> mItemDates;
    protected LayoutInflater inflater = null;
    private OnItemClickListener listener;

    public GeneralRecyclerAdapter() {
        mItemDates = new ArrayList<>();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        View view = inflater.inflate(getItemLayoutId(), parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        holder.setOnItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindHolder(holder, position, mItemDates.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemDates.size();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (listener != null && isEnable(position)) {
            listener.onItemClick(position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    abstract protected int getItemLayoutId();

    abstract protected void bindHolder(RecyclerViewHolder holder, int position, T item);

    public boolean isEnable(int position) {
        return true;
    }

    public T getItem(int position) {
        return mItemDates.get(position);
    }

    public void addAll(List<T> items) {
        int position = mItemDates.size();
        mItemDates.addAll(items);
        notifyItemInserted(position);
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

    public void clear() {
        mItemDates.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
