package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.woodentea.fun.Happy;
import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2016/7/19
 * Time: 14:46
 */
public class NativeActivity extends BaseTranslucentActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.native_activity);
        setUpActionBar(true);
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(Happy.goPlay());

        return true;
    }
}
