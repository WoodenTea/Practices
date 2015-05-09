package com.ymsfd.practices.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneralAdapter<T> extends BaseAdapter {
    private List<T> list = new ArrayList<T>();

    public void addData(List<T> l) {
        this.list.addAll(l);
    }

    public void setData(List<T> l) {
        list.clear();
        addData(l);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    protected View layoutView(ViewGroup parent, int resource) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return layoutInflater.inflate(resource, parent, false);
    }
}
