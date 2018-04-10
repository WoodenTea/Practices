package com.ymsfd.practices.ui.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;

public class ViewGroupActivity extends BaseActivity {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.viewgroup_activity);
        enableToolbarUp(true);

        return true;
    }
}
