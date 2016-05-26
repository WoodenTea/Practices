package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.ViewUtil;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/30/15
 * Time: 10:32
 */
public class TestActivity extends BaseTranslucentActivity {

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_test);

        setUpActionBar(true);
        View view = findViewById(R.id.button);
        ViewUtil.checkViewIsNull(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                D("Width: " + view.getWidth() + " Height: " + view.getHeight());
            }
        });

        return true;
    }
}