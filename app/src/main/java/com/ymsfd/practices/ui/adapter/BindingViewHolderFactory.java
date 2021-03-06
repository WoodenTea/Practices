package com.ymsfd.practices.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ymsfd.practices.domain.BaseViewModel;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class BindingViewHolderFactory {

    private Context context;
    private Map<Class<? extends BaseViewModel>, Class<? extends BindingViewHolder>> map = new
            HashMap<>();

    public BindingViewHolderFactory(Context context) {
        this.context = context;
    }

    BindingViewHolder create(BaseViewModel viewModel, ViewGroup parent) {
        try {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                    viewModel.itemViewType(), parent, false);
            Class<? extends BindingViewHolder> viewHolder = map.get(viewModel.getClass());
            Constructor<? extends BindingViewHolder> constructor =
                    viewHolder.getDeclaredConstructor(ViewDataBinding.class);
            return constructor.newInstance(binding);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }
    }

    void bind(Class<? extends BaseViewModel> viewModel,
              Class<? extends BindingViewHolder> viewHolder) {
        map.put(viewModel, viewHolder);
    }
}
