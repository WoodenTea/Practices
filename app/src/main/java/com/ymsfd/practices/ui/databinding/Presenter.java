package com.ymsfd.practices.ui.databinding;

import android.view.View;

/**
 * Created by WoodenTea.
 * Date: 2016/7/1
 * Time: 11:04
 */
public class Presenter {
    public void onSaveClick(View view, Task task) {
        view.setEnabled(false);
        task.onExecute();
        view.setEnabled(true);
    }
}
