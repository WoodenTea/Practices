package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Preconditions;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    protected int statusBarHeight() {
        int height = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        D("Status bar height->" + height);
        return height;
    }

    protected void translucentStatusBar(View view) {
        if (view != null) {
            int paddingTop = view.getPaddingTop();
            paddingTop += statusBarHeight();
            view.setPadding(view.getPaddingLeft(), paddingTop,
                    view.getPaddingRight(), view.getPaddingBottom());
            D("paddingTop->" + paddingTop);
        }
    }

    protected ActionBar enableActionBar(Toolbar toolbar, boolean up) {
        if (toolbar == null) {
            throw new NullPointerException("The toolbar is null.");
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && up) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        return actionBar;
    }

    protected ActionBar setUpActionBar(boolean up) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Preconditions.checkNotNull(toolbar);
        translucentStatusBar(toolbar);
        return enableActionBar(toolbar, up);
    }
}
