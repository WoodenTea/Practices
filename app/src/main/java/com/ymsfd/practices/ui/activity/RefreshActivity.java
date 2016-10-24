package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.ymsfd.practices.R;
import com.ymsfd.practices.ui.widget.RefreshLayout;
import com.ymsfd.practices.ui.widget.SwipeRefreshLayout;

/**
 * Created by WoodenTea.
 * Date: 2016/8/11
 * Time: 17:21
 */
public class RefreshActivity extends BaseActivity {
    int offset = -10;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.refresh_activity);
        enableToolbarHomeButton(true);
        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refresh);
        final View header = LayoutInflater.from(this).inflate(R.layout.refresh_header,
                refreshLayout,
                false);
        refreshLayout.addHeader(header);

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        if (swipe != null) {
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
            swipe.setColorSchemeResources(android.R.color.holo_red_light, android.R.color
                            .holo_green_light,
                    android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        }

        View button = findViewById(R.id.button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    header.bringToFront();
                    header.offsetTopAndBottom(offset);
                    offset += offset;
                }
            });
        }
        return true;
    }
}
