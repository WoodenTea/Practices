package com.ymsfd.practices.ui.activity.recyclerview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemSpanLookup;
import com.ymsfd.practices.R;
import com.ymsfd.practices.domain.Entity;
import com.ymsfd.practices.domain.StringEntity;
import com.ymsfd.practices.ui.activity.BaseActivity;
import com.ymsfd.practices.ui.activity.util.RecyclerViewConfig;
import com.ymsfd.practices.ui.adapter.BindingRecyclerAdapter;
import com.ymsfd.practices.ui.adapter.BindingViewHolder;
import com.ymsfd.practices.ui.adapter.fancy.helper.DividerGridItemDecoration;
import com.ymsfd.practices.ui.adapter.util.SimpleHidingScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WoodenTea.
 * Date: 2016/1/6
 * Time: 13:48
 */
public class RecyclerViewActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, Paginate.Callbacks {
    private EditText editText;
    private BindingRecyclerAdapter adapter;
    private RecyclerViewConfig config;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Paginate paginate;

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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        List<Entity> list = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            StringEntity stringEntity = new StringEntity();
            stringEntity.name = "" + index;
            list.add(stringEntity);
        }

        adapter = new BindingRecyclerAdapter(this);
        RecyclerViewConfig.Builder builder = new RecyclerViewConfig.Builder()
                .recyclerLayoutManager(new LinearLayoutManager(this))
                .recyclerViewAnimator(new DefaultItemAnimator())
                .recyclerViewDecor(new DividerGridItemDecoration(this))
                .bind(StringEntity.class, StringViewHolder.class)
                .enableLoadMore(false)
                .defaultEntities(list);

        View floatingButton = findViewById(R.id.fabButton);
        recyclerView.addOnScrollListener(new SimpleHidingScrollListener(floatingButton));

        buildRecyclerViewConfig(builder.build());

        setupConfig();

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

    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }

    @Override
    public void onRefresh() {

    }

    private void setupConfig() {
        setupRecyclerView();
        adapter.addAll(config.getDefaultEntities());
    }

    public void setupSwipeRefreshLayout() {
        if (config != null) {
            int[] colors = config.getColorSchemeColors();
            if (colors != null) {
                swipeRefreshLayout.setColorSchemeColors(colors);
            }
            boolean enabled = config.isEnabled();
            swipeRefreshLayout.setEnabled(enabled);
            if (enabled) {
                swipeRefreshLayout.setOnRefreshListener(this);
            }
        }
    }

    public void setupPaginate() {
        if (config != null) {
            if (config.canLoadMore()) {
                paginate = Paginate.with(recyclerView, this)
                        .setLoadingTriggerThreshold(config.getLoadingTriggerThreshold())
                        .addLoadingListItem(config.hasAddLoadingListItem())
                        .setLoadingListItemCreator(config.getLoadingListItemCreator())
                        .setLoadingListItemSpanSizeLookup(new LoadingListItemSpanLookup() {
                            @Override
                            public int getSpanSize() {
                                return config.getSpanSizeLookup();
                            }
                        })
                        .build();
            }
        }
    }

    public void setupRecyclerView() {
        if (config != null) {
            RecyclerView.LayoutManager layoutManager = config.getLayoutManager();
            if (layoutManager != null) {
                recyclerView.setLayoutManager(layoutManager);
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }

            RecyclerView.ItemDecoration decor = config.getDecor();
            if (decor != null) {
                recyclerView.addItemDecoration(decor);
            }

            RecyclerView.ItemAnimator animator = config.getAnimator();
            if (animator != null) {
                recyclerView.setItemAnimator(animator);
            }
        }

        recyclerView.setAdapter(adapter);
    }

    public void buildRecyclerViewConfig(RecyclerViewConfig config) {
        if (config == null) {
            return;
        }

        Map<Class<? extends Entity>, Class<? extends BindingViewHolder>> bindViewHolders = config
                .getBindViewHolders();
        if (bindViewHolders != null) {
            for (Map.Entry<Class<? extends Entity>, Class<? extends BindingViewHolder>> entry
                    : bindViewHolders.entrySet()) {
                adapter.bind(entry.getKey(), entry.getValue());
            }
        }

        this.config = config;
    }
}
