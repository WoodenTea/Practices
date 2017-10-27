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
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.activity_touch);
        enableToolbarUp(true);
        return true;
    }
}
