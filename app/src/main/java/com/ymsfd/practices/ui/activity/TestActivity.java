package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Preconditions;
import com.ymsfd.practices.ui.adapter.ComplexRecyclerAdapter;
import com.ymsfd.practices.ui.adapter.RecyclerViewHolder;
import com.ymsfd.practices.ui.adapter.fancy.RecyclerListAdapter;

import java.util.ArrayList;

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> strings = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            strings.add("" + index);
        }
        ComplexRecyclerAdapter<String> adapter = new ComplexRecyclerAdapter<String>() {
            @Override
            protected int getItemLayoutId() {
                return R.layout.item_card_view;
            }

            @Override
            protected void bindHolder(RecyclerViewHolder holder, int position, String item) {
                holder.setText(R.id.news_title, item);
            }

            @Override
            public void bindHeaderHolder(RecyclerViewHolder holder, int position, int index) {
                holder.setText(R.id.text, "0");
            }

            @Override
            public int[] getHeaderLayoutId() {
                return new int[]{R.layout.item_main};
            }
        };
        adapter.addAll(strings);
        recyclerView.setAdapter(adapter);
        return true;
    }
}