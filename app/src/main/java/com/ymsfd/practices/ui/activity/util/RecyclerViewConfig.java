package com.ymsfd.practices.ui.activity.util;

import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;

import com.ymsfd.practices.domain.BaseViewModel;
import com.ymsfd.practices.ui.adapter.BindingViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WoodenTea.
 * Date: 15/10/2016
 * Time: 22:45
 */
public class RecyclerViewConfig {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration decor;
    private RecyclerView.ItemAnimator animator;

    private int[] colorSchemeColors;
    private boolean enableRefresh = true; // default true

    private int requestSize = 10;

    private List<BaseViewModel> defaultEntities;

    private Map<Class<? extends BaseViewModel>, Class<? extends BindingViewHolder>>
            bindViewHolders = new HashMap<>();

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public RecyclerView.ItemDecoration getDecor() {
        return decor;
    }

    public RecyclerView.ItemAnimator getAnimator() {
        return animator;
    }

    public int[] getColorSchemeColors() {
        return colorSchemeColors;
    }

    public boolean isEnabled() {
        return enableRefresh;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public List<BaseViewModel> getDefaultEntities() {
        return defaultEntities;
    }

    public Map<Class<? extends BaseViewModel>, Class<? extends BindingViewHolder>>
    getBindViewHolders() {
        return bindViewHolders;
    }

    public static class Builder {

        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.ItemDecoration decor;
        private RecyclerView.ItemAnimator animator;

        private int[] colorSchemeColors;
        private boolean enableRefresh = true; // default true

        private List<BaseViewModel> defaultEntities;

        private Map<Class<? extends BaseViewModel>, Class<? extends BindingViewHolder>>
                bindViewHolders = new HashMap<>();

        public RecyclerViewConfig build() {
            RecyclerViewConfig config = new RecyclerViewConfig();
            config.layoutManager = layoutManager;
            config.decor = decor;
            config.animator = animator;

            config.colorSchemeColors = colorSchemeColors;
            config.enableRefresh = enableRefresh;

            config.bindViewHolders = bindViewHolders;

            config.defaultEntities = defaultEntities;

            return config;
        }

        public Builder defaultEntities(List<BaseViewModel> list) {
            defaultEntities = list;

            return this;
        }

        public Builder recyclerLayoutManager(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            return this;
        }

        public Builder recyclerViewDecor(RecyclerView.ItemDecoration decor) {
            this.decor = decor;
            return this;
        }

        public Builder recyclerViewAnimator(RecyclerView.ItemAnimator animator) {
            this.animator = animator;
            return this;
        }

        public Builder swipeRefreshLayoutColors(@ColorInt int... colors) {
            this.colorSchemeColors = colors;
            return this;
        }

        public Builder swipeRefreshLayoutEnabled(boolean enabled) {
            this.enableRefresh = enabled;
            return this;
        }

        public Builder bind(Class<? extends BaseViewModel> key,
                            Class<? extends BindingViewHolder> value) {
            bindViewHolders.put(key, value);
            return this;
        }
    }
}
