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
public class TestActivity extends BaseTranslucentActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.test_activity);

        setUpActionBar(true);
        imageView = (ImageView) findViewById(R.id.cartoon);
        Preconditions.checkNotNull(imageView);
        View view = findViewById(R.id.submit);
        Preconditions.checkNotNull(view);
        view.setOnClickListener(this);
        view = findViewById(R.id.cancel);
        Preconditions.checkNotNull(view);
        view.setOnClickListener(this);

        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            imageView.scrollBy(-10, 0);
        } else if (v.getId() == R.id.cancel) {
            imageView.scrollBy(10, 0);
        }
    }
}