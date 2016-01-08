package com.ymsfd.practices.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WoodenTea.
 * Date: 2016/1/7
 * Time: 10:03
 */
public abstract class GeneralRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView
        .ViewHolder> {
    protected List<T> list = new ArrayList<>();

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<T> list) {
        this.list.addAll(list);
    }
}
