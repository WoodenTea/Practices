package com.ymsfd.practices.ui.activity.recyclerview;

import android.databinding.ViewDataBinding;

import com.ymsfd.practices.domain.StringViewModel;
import com.ymsfd.practices.ui.adapter.BindingViewHolder;

public class StringViewHolder extends BindingViewHolder<StringViewModel> {
    public StringViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding);
    }

    @Override
    public void setEntity(StringViewModel entity) {

    }
}
