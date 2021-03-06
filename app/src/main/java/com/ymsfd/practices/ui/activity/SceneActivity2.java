package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * User: ymsfd
 * Date: 5/25/15
 * Time: 10:27
 */
public class SceneActivity2 extends BaseActivity {

    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.scene2_activity);
        enableToolbarUp(true);
        View view = findViewById(R.id.cartoon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(SceneActivity2.this);
            }
        });

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCompat.finishAfterTransition(this);
    }
}
