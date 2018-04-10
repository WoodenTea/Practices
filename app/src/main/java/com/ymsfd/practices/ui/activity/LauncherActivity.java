package com.ymsfd.practices.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * Created by WoodenTea.
 * Date: 2016/6/20
 * Time: 11:18
 */
public class LauncherActivity extends BaseActivity {

    private CountDownTimer timer;

    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        timer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
        timer.start();

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
