package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ymsfd.practices.R;
import com.ymsfd.practices.ui.adapter.ComplexRecyclerAdapter;
import com.ymsfd.practices.ui.adapter.RecyclerViewHolder;
import com.ymsfd.practices.ui.adapter.fancy.helper.DividerGridItemDecoration;
import com.ymsfd.practices.ui.adapter.fancy.helper.RecyclerItemClickListener;
import com.ymsfd.practices.ui.adapter.util.GridSpan;
import com.ymsfd.practices.ui.adapter.util.SimpleHidingScrollListener;

import java.util.ArrayList;

/**
 * Created by WoodenTea.
 * Date: 2016/1/6
 * Time: 13:48
 */
public class RecyclerViewActivity extends BaseActivity implements View.OnClickListener {
    private EditText editText;
    private ComplexRecyclerAdapter<String> adapter;
    private int i = 1;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_recycler_view);
        editText = (EditText) findViewById(R.id.position);
        View view = findViewById(R.id.add);
        if (view == null) {
            return false;
        }
        view.setOnClickListener(this);
        view = findViewById(R.id.delete);
        if (view == null) {
            return false;
        }
        view.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (recyclerView == null) {
            return false;
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        ArrayList<String> strings = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            strings.add("" + index);
        }

        adapter = new ComplexRecyclerAdapter<String>() {
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
        gridLayoutManager.setSpanSizeLookup(new GridSpan(adapter,
                gridLayoutManager.getSpanCount()));
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new
                RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Snackbar.make(recyclerView, "Position: " + position,
                                Snackbar.LENGTH_LONG).setAction("Action", new View
                                .OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(RecyclerViewActivity.this, "Toast comes out",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                    }
                }));
        View floatingButton = findViewById(R.id.fabButton);
        recyclerView.addOnScrollListener(new SimpleHidingScrollListener(floatingButton));

        return true;
    }

    @Override
    public void onClick(View view) {
        int position = Integer.valueOf(editText.getText().toString());
        if (view.getId() == R.id.delete) {
            adapter.remove(position);
        } else if (view.getId() == R.id.add) {
            adapter.addItem(position, ("Added Item " + i++));
        }
    }
}
