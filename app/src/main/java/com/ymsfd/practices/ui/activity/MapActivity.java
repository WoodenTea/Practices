package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea on 2016/1/15.
 * Author Deepan
 */
public class MapActivity extends BaseActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.map_activity);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }
}
