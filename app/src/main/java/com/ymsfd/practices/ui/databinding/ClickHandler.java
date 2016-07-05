package com.ymsfd.practices.ui.databinding;

import android.util.Log;
import android.view.View;

/**
 * Created by WoodenTea.
 * Date: 2016/7/1
 * Time: 11:03
 */
public class ClickHandler {
    public void onClickFriend(View view) {
        view.setEnabled(false);
        Log.d(getClass().getSimpleName(), "ClickHandler");
        view.setEnabled(true);
    }
}
