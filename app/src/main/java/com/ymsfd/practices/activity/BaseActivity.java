package com.ymsfd.practices.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _onCreate(savedInstanceState);
    }

    protected boolean _onCreate(Bundle savedInstanceState) {
        return true;
    }

    final protected void D(String msg) {
        Log.d(TAG, msg);
    }

    final protected void E(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        E(stringWriter.toString());
    }

    final protected void E(String msg) {
        Log.e(TAG, msg);
    }

    final protected void I(String msg) {
        Log.i(TAG, msg);
    }
}
