package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.ymsfd.RefreshLayout;
import com.ymsfd.practices.R;

/**
 * Created by WoodenTea. Date: 2016/8/11 Time: 17:21
 */
public class RefreshActivity extends BaseActivity {

    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.refresh_activity);
        enableToolbarUp(true);
        final RefreshLayout refreshLayout = findViewById(R.id.refresh);
        final View header = LayoutInflater.from(this)
                .inflate(R.layout.refresh_header, refreshLayout, false);
        refreshLayout.setHeader(header);

        final SwipeRefreshLayout swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        swipe.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light);

        return true;
    }
}
