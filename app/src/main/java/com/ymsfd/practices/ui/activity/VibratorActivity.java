package com.ymsfd.practices.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Preconditions;

/**
 * Created by WoodenTea.
 * Date: 2016/6/7
 * Time: 16:06
 */
public class VibratorActivity extends BaseActivity implements View.OnClickListener {

    private Vibrator vibrator;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.test_activity);
        View view = findViewById(R.id.submit);
        Preconditions.checkNotNull(view);
        view.setOnClickListener(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        return true;
    }

    @Override
    public void onClick(View view) {
        vibrator.vibrate(2000);
    }
}
