package com.ymsfd.practices.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _onCreate(savedInstanceState);
    }

    protected boolean _onCreate(Bundle savedInstanceState) {
        return true;
    }

    protected void D(String msg) {
        Log.d(TAG, msg);
    }

    protected void E(String msg) {
        Log.e(TAG, msg);
    }

    protected void I(String msg) {
        Log.i(TAG, msg);
    }
}
