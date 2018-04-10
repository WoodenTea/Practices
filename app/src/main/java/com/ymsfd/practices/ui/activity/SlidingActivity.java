package com.ymsfd.practices.ui.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2015/12/8
 * Time: 14:13
 */
public class SlidingActivity extends BaseActivity {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.sliding_activity);
        return true;
    }
}
