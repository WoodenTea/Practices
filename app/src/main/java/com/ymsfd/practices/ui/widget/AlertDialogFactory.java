package com.ymsfd.practices.ui.widget;

import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.ymsfd.practices.R;
import com.ymsfd.practices.ui.activity.BaseActivity;

/**
 * Description:
 * Author: WoodenTea
 * Date: 2018/4/10
 */
public class AlertDialogFactory {
    public static AlertDialog createWaitingDialog(BaseActivity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.WTTransparentDialog)
                .setView(R.layout.dialog_waiting)
                .create();

        return dialog;
    }
}
