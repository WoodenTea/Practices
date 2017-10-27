package com.ymsfd.practices.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ymsfd.practices.domain.BaseViewModel;

public abstract class BindingViewHolder<T extends BaseViewModel> extends RecyclerView.ViewHolder {
    protected ViewDataBinding viewDataBinding;

    public BindingViewHolder(ViewDataBinding viewDataBinding) {
        this(viewDataBinding.getRoot());
        this.viewDataBinding = viewDataBinding;
        this.viewDataBinding.executePendingBindings();
    }

    private BindingViewHolder(View itemView) {
        super(itemView);
    }

    protected void setPresenter(ItemClickPresenter presenter) {

    }

    public abstract <E extends T> void setViewModel(E entity);

    public ViewDataBinding getViewDataBinding() {
        return viewDataBinding;
    }
}
