package com.ymsfd.practices.ui.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 11/11/15
 * Time: 21:10
 */
public class ScrollLayoutActivity extends BaseActivity {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.scroll_layout_activity);
        enableToolbarUp(true);
        return true;
    }
}
