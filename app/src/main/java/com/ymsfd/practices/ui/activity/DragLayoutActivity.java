package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2015/12/3
 * Time: 18:45
 */
public class DragLayoutActivity extends BaseActivity {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.drag_activity);
        enableToolbarUp(true);
        View view = findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                D("View Click");
            }
        });
        return true;
    }
}
