package com.ymsfd.practices.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 11/11/15
 * Time: 21:10
 */
public class ScrollLayoutActivity extends BaseActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_scroll_layout);
        return true;
    }
}
