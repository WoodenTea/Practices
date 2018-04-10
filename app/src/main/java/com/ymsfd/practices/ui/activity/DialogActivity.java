package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

import com.ymsfd.practices.R;
import com.ymsfd.practices.ui.widget.AlertDialogFactory;

/**
 * Created by WoodenTea.
 * Date: 2016/7/13
 * Time: 14:16
 */
public class DialogActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.dialog_activity);
        enableToolbarUp(true);
        View view = findViewById(R.id.submit);
        view.setOnClickListener(this);
        findViewById(R.id.btn_tips).setOnClickListener(this);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            AlertDialog dialog = new AlertDialog
                    .Builder(DialogActivity.this, R.style.WTTransparentDialog)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.hello)
                    .setPositiveButton(R.string.submit, null)
                    .setNegativeButton(R.string.cancel, null)
                    .create();
            dialog.show();
        } else if (view.getId() == R.id.btn_tips) {
            AlertDialog dialog = AlertDialogFactory.createWaitingDialog(this);
            dialog.show();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = 200;
            lp.height = 200;
            dialog.getWindow().setAttributes(lp);
        }
    }
}
