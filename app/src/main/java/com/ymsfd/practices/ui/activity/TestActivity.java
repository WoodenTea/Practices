package com.ymsfd.practices.ui.activity;

import android.os.Bundle;

import com.ymsfd.practices.R;
import com.ymsfd.practices.algorithm.Signature;
import com.ymsfd.practices.infrastructure.util.WTLogger;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/30/15
 * Time: 10:32
 */
public class TestActivity extends BaseActivity {

    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.test_activity);
        enableToolbarUp(true);
        WTLogger.d("Test", Signature.stringFromJNI());
        String str = "String from j\r\na\rv\na";
        WTLogger.d("Test", "" + str.length());
        str = Signature.encode(str);
        WTLogger.d("Test", "String: " + str + " Length: " + str.length());
        return true;
    }
}