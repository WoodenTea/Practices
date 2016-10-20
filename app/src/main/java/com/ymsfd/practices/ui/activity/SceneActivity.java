package com.ymsfd.practices.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * User: ymsfd
 * Date: 5/25/15
 * Time: 10:27
 */
public class SceneActivity extends BaseActivity {

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.scene_activity);
        setUpActionBar(true);
        View view = findViewById(R.id.cartoon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SceneActivity.this, SceneActivity2.class);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(SceneActivity.this, view, "robot");
                ActivityCompat.startActivity(SceneActivity.this, intent, options.toBundle());
            }
        });

        return true;
    }

}
