package com.ymsfd.practices.ui.activity.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.ymsfd.practices.R;
import com.ymsfd.practices.domain.StringEntity;
import com.ymsfd.practices.ui.activity.BaseActivity;
import com.ymsfd.practices.ui.adapter.BindingRecyclerAdapter;
import com.ymsfd.practices.ui.adapter.fancy.helper.DividerGridItemDecoration;
import com.ymsfd.practices.ui.adapter.util.SimpleHidingScrollListener;

import java.util.ArrayList;

/**
 * Created by WoodenTea.
 * Date: 2016/1/6
 * Time: 13:48
 */
public class RecyclerViewActivity extends BaseActivity implements View.OnClickListener {
    private EditText editText;
    private BindingRecyclerAdapter adapter;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.recycler_view_activity);
        editText = (EditText) findViewById(R.id.position);
        View view = findViewById(R.id.add);
        view.setOnClickListener(this);
        view = findViewById(R.id.delete);
        view.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        ArrayList<StringEntity> list = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            StringEntity stringEntity = new StringEntity();
            stringEntity.name = "" + index;
            list.add(stringEntity);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BindingRecyclerAdapter(this);
        adapter.bind(StringEntity.class, StringViewHolder.class);
        adapter.addAll(list);
        recyclerView.setAdapter(adapter);

        View floatingButton = findViewById(R.id.fabButton);
        recyclerView.addOnScrollListener(new SimpleHidingScrollListener(floatingButton));

        return true;
    }

    @Override
    public void onClick(View view) {
        int position = Integer.valueOf(editText.getText().toString());
        if (view.getId() == R.id.delete) {
//            adapter.remove(position);
        } else if (view.getId() == R.id.add) {
//            adapter.addItem(position, ("Added Item " + i++));
        }
    }
}
