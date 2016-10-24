package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Preconditions;
import com.ymsfd.practices.infrastructure.util.WTLogger;

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

    protected ActionBar enableToolbarHomeButton(boolean enable) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Preconditions.checkNotNull(toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && enable) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        return actionBar;
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

    @SuppressWarnings("unused")
    protected void translucentStatusBar(View view) {
        if (view != null) {
            int paddingTop = view.getPaddingTop();
            paddingTop += statusBarHeight();
            view.setPadding(view.getPaddingLeft(), paddingTop,
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    final protected void D(String msg) {
        WTLogger.d(TAG, msg);
    }

    final protected void E(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        E(stringWriter.toString());
    }

    final protected void E(String msg) {
        WTLogger.e(TAG, msg);
    }
}
