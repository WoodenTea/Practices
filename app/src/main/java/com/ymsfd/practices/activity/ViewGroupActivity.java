package com.ymsfd.practices.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;

public class ViewGroupActivity extends BaseActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_viewgroup);
        return true;
    }
}