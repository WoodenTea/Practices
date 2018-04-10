package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.ymsfd.practices.R;
import com.ymsfd.practices.ui.widget.LuckView;

/**
 * Created by WoodenTea.
 * Date: 2016/6/23
 * Time: 9:54
 */
public class LuckViewActivity extends BaseActivity {
    private boolean flag = false;

    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.luck_view_activity);
        enableToolbarUp(true);
        final LuckView luckView = findViewById(R.id.luck_view);
        View view = findViewById(R.id.submit);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    luckView.luckyEnd();
                    flag = false;
                } else {
                    luckView.luckyStart(0);
                    flag = true;
                }
            }
        });

        return true;
    }
}
