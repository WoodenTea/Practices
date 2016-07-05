package com.ymsfd.practices.ui.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;

public class ViewGroupActivity extends BaseActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.viewgroup_activity);
        setUpActionBar(true);

        return true;
    }
}
