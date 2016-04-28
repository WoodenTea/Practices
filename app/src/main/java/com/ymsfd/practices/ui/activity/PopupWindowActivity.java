package com.ymsfd.practices.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.DensityUtil;

/**
 * Created by WoodenTea.
 * Date: 2016/4/28
 * Time: 10:54
 */
public class PopupWindowActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_popup_window);
        findViewById(R.id.btn).setOnClickListener(this);
        return true;
    }

    @Override
    public void onClick(View view) {
        View v = View.inflate(this, R.layout.ppw_extend_function, null);
        PopupWindow popupWindow = new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        int layoutWidth = (int) DensityUtil.dp2px(110) / 2;
        int xOff = -layoutWidth + view.getWidth() / 2 - (layoutWidth - 45);
        popupWindow.showAsDropDown(view, xOff, 0);
    }
}
