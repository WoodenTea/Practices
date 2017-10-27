package com.ymsfd.practices.ui.adapter.util;

import com.ymsfd.practices.ui.adapter.ItemClickPresenter;

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/2/9
 */
public class SimpleItemClickPresenter implements ItemClickPresenter {
    @Override
    public void onItemClick() {
        System.out.println("Click");
    }
}
