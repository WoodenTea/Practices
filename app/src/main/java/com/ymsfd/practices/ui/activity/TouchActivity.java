package com.ymsfd.practices.ui.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/4/12
 */
public class TouchActivity extends BaseActivity {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.activity_touch);
        enableToolbarUp(true);
        return true;
    }
}
