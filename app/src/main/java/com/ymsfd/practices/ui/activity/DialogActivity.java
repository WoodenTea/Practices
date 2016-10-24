package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2016/7/13
 * Time: 14:16
 */
public class DialogActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.dialog_activity);
        enableToolbarHomeButton(true);
        View view = findViewById(R.id.submit);
        view.setOnClickListener(this);
        return true;
    }

    @Override
    public void onClick(View view) {
        AlertDialog dialog = new AlertDialog
                .Builder(DialogActivity.this, R.style.WTTransparentDialog)
                .setTitle(R.string.app_name)
                .setMessage(R.string.hello)
                .setPositiveButton(R.string.submit, null)
                .setNegativeButton(R.string.cancel, null)
                .create();
        dialog.show();
    }
}
