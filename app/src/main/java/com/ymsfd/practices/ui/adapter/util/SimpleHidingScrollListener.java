package com.ymsfd.practices.ui.adapter.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by WoodenTea.
 * Date: 2016/3/8
 * Time: 17:55
 */
public class SimpleHidingScrollListener extends HidingScrollListener {
    private View view;

    public SimpleHidingScrollListener(View view) {
        this.view = view;
    }

    @Override
    public void onHide() {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            int fabBottomMargin = ((ViewGroup.MarginLayoutParams) lp).bottomMargin;
            view.animate().translationY(view.getHeight() + fabBottomMargin)
                    .setInterpolator(new AccelerateInterpolator(2)).start();
        }
    }

    @Override
    public void onShow() {
        view.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
}
