package com.ymsfd.practices.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by WoodenTea.
 * Date: 2016/5/26
 * Time: 11:09
 */
public class BaseTranslucentActivity extends BaseActivity {

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | params.flags);
        }

        return true;
    }
}
