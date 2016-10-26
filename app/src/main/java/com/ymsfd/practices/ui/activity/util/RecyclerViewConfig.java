package com.ymsfd.practices.ui.activity.util;

import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;

import com.paginate.recycler.LoadingListItemCreator;
import com.ymsfd.practices.domain.Entity;
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

    private boolean enableLoadMore;
    private int loadingTriggerThreshold = 4;
    private LoadingListItemCreator loadingListItemCreator;
    private boolean addLoadingListItem;
    private int spanSizeLookup;

    private List<Entity> defaultEntities;

    private Map<Class<? extends Entity>, Class<? extends BindingViewHolder>> bindViewHolders =
            new HashMap<>();

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

    public boolean hasAddLoadingListItem() {
        return addLoadingListItem;
    }

    public boolean canLoadMore() {
        return enableLoadMore;
    }

    public int getSpanSizeLookup() {
        return spanSizeLookup;
    }

    public LoadingListItemCreator getLoadingListItemCreator() {
        return loadingListItemCreator;
    }

    public int getLoadingTriggerThreshold() {
        return loadingTriggerThreshold;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public List<Entity> getDefaultEntities() {
        return defaultEntities;
    }

    public Map<Class<? extends Entity>, Class<? extends BindingViewHolder>> getBindViewHolders() {
        return bindViewHolders;
    }

    public static class Builder {
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.ItemDecoration decor;
        private RecyclerView.ItemAnimator animator;

        private int[] colorSchemeColors;
        private boolean enableRefresh = true; // default true

        private int requestSize = 10;
        private boolean enableLoadMore = false;
        private int loadingTriggerThreshold = 5;
        private LoadingListItemCreator loadingListItemCreator;
        private boolean addLoadingListItem;
        private int spanSizeLookup = 1;

        private List<Entity> defaultEntities;

        private Map<Class<? extends Entity>, Class<? extends BindingViewHolder>> bindViewHolders
                = new HashMap<>();

        public RecyclerViewConfig build() {
            RecyclerViewConfig config = new RecyclerViewConfig();
            config.layoutManager = layoutManager;
            config.decor = decor;
            config.animator = animator;

            config.colorSchemeColors = colorSchemeColors;
            config.enableRefresh = enableRefresh;

            config.requestSize = requestSize;
            config.addLoadingListItem = addLoadingListItem;
            config.loadingTriggerThreshold = loadingTriggerThreshold;
            config.loadingListItemCreator = loadingListItemCreator;
            config.spanSizeLookup = spanSizeLookup;

            config.bindViewHolders = bindViewHolders;

            config.defaultEntities = defaultEntities;
            config.enableLoadMore = enableLoadMore;

            return config;
        }

        public Builder defaultEntities(List<Entity> list) {
            defaultEntities = list;

            return this;
        }

        public Builder enableLoadMore(boolean enable) {
            enableLoadMore = enable;
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

        public Builder requestSize(int pageSize) {
            this.requestSize = pageSize;
            return this;
        }

        public Builder loadingTriggerThreshold(int loadingTriggerThreshold) {
            this.loadingTriggerThreshold = loadingTriggerThreshold;
            return this;
        }

        public Builder loadingListItemCreator(LoadingListItemCreator loadingListItemCreator) {
            this.loadingListItemCreator = loadingListItemCreator;
            return this;
        }

        public Builder addLoadingListItem(boolean addLoadingListItem) {
            this.addLoadingListItem = addLoadingListItem;
            return this;
        }

        public Builder spanSizeLookup(int spanSizeLookup) {
            this.spanSizeLookup = spanSizeLookup;
            return this;
        }

        public Builder bind(Class<? extends Entity> key, Class<? extends BindingViewHolder> value) {
            bindViewHolders.put(key, value);
            return this;
        }
    }
}
