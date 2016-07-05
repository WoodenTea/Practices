package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Preconditions;

/**
 * Created by WoodenTea.
 * Date: 2015/12/3
 * Time: 18:45
 */
public class DragLayoutActivity extends BaseActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.drag_activity);
        setUpActionBar(true);
        View view = findViewById(R.id.view);
        Preconditions.checkNotNull(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                D("View Click");
            }
        });
        return true;
    }
}
