package com.ymsfd.practices.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.ymsfd.practices.R;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/19/15
 * Time: 15:07
 */
public class CustomViewActivity extends BaseActivity {

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_custom_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    localLayoutParams.flags);
        }
        return true;
    }
}