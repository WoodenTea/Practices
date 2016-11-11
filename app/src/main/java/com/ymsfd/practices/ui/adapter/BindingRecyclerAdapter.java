package com.ymsfd.practices.ui.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.ymsfd.practices.domain.Entity;

import java.util.ArrayList;
import java.util.List;

public class BindingRecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private List<Entity> dataList = new ArrayList<>();
    private SparseArray<Entity> sparseArray = new SparseArray<>();
    private BindingViewHolderFactory factory;
    private ItemClickPresenter presenter;

    public BindingRecyclerAdapter(Context context) {
        factory = new BindingViewHolderFactory(context);
    }

    public BindingRecyclerAdapter(Context context, ItemClickPresenter presenter) {
        this(context);
        this.presenter = presenter;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BindingViewHolder holder = factory.create(sparseArray.get(viewType), parent);
        if (presenter != null) {
            holder.setPresenter(presenter);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        ViewDataBinding viewDataBinding = holder.getViewDataBinding();
        holder.setEntity(dataList.get(position));
        viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = dataList.get(position).itemViewType();
        if (sparseArray.get(viewType) == null) {
            sparseArray.put(viewType, dataList.get(position));
        }

        return viewType;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(List<? extends Entity> list) {
        if (list == null) {
            throw new IllegalArgumentException("list can not be null");
        }

        int appendSize = list.size();
        if (appendSize == 0) {
            return;
        }

        int prevSize = dataList.size();
        List<Entity> data = new ArrayList<>(prevSize + appendSize);
        data.addAll(dataList);
        data.addAll(list);
        dataList = data;
        notifyItemRangeInserted(prevSize, appendSize);
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public <E extends Entity> void add(E entity) {
        this.dataList.add(entity);
    }

    public void bind(Class<? extends Entity> viewModel,
                     Class<? extends BindingViewHolder> viewHolder) {
        factory.bind(viewModel, viewHolder);
    }
}
