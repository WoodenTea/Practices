package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Preconditions;

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

        setContentView(R.layout.test_activity);

        setUpActionBar(true);
        final ImageView imageView = (ImageView) findViewById(R.id.cartoon);
        Preconditions.checkNotNull(imageView);
        View view = findViewById(R.id.submit);
        Preconditions.checkNotNull(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.scrollBy(-10, 0);
            }
        });

        return true;
    }
}