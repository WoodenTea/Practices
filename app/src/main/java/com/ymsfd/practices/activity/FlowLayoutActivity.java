package com.ymsfd.practices.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 11/11/15
 * Time: 21:17
 */
public class FlowLayoutActivity extends BaseActivity {

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_flow_layout);
        return true;
    }
}