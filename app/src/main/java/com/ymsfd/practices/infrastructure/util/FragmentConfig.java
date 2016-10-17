package com.ymsfd.practices.infrastructure.util;

import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;

/**
 * Created by WoodenTea.
 * Date: 15/10/2016
 * Time: 22:45
 */
public class FragmentConfig {

    // RecyclerView config
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration decor;
    private RecyclerView.ItemAnimator animator;

    // SwipeRefreshLayout config
    private int[] colorSchemeColors;
    private boolean enabled;

    private int pageSize = 20;
    private int startPage = 1;

    public static class Builder {
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.ItemDecoration decor;
        private RecyclerView.ItemAnimator animator;

        private int[] colorSchemeColors;
        private boolean enabled = true; // default true

        private int pageSize = 20;
        private int startPage = 1;

        public FragmentConfig build() {
            FragmentConfig config = new FragmentConfig();
            config.layoutManager = layoutManager;
            config.decor = decor;
            config.animator = animator;
            config.colorSchemeColors = colorSchemeColors;
            config.enabled = enabled;
            config.pageSize = pageSize;
            config.startPage = startPage;
            return config;
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
            this.enabled = enabled;
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder startPage(int startPage) {
            this.startPage = startPage;
            return this;
        }
    }
}
