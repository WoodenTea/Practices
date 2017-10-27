package com.ymsfd.practices.ui.activity.recyclerview;

import android.databinding.ViewDataBinding;

import com.ymsfd.practices.BR;
import com.ymsfd.practices.domain.StringViewModel;
import com.ymsfd.practices.ui.adapter.BindingViewHolder;
import com.ymsfd.practices.ui.adapter.ItemClickPresenter;

public class StringViewHolder extends BindingViewHolder<StringViewModel> {
    public StringViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding);
    }

    @Override
    protected void setPresenter(ItemClickPresenter presenter) {
        viewDataBinding.setVariable(BR.presenter, presenter);
    }

    @Override
    public void setViewModel(StringViewModel entity) {
        viewDataBinding.setVariable(BR.stringViewModel, entity);
    }
}
