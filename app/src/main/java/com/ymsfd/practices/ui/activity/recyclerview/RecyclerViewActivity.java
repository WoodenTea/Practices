package com.ymsfd.practices.ui.activity.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemSpanLookup;
import com.ymsfd.practices.R;
import com.ymsfd.practices.domain.BaseViewModel;
import com.ymsfd.practices.domain.StringViewModel;
import com.ymsfd.practices.ui.activity.BaseActivity;
import com.ymsfd.practices.ui.activity.util.PaginateManager;
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
    private BindingRecyclerAdapter adapter;
    private RecyclerViewConfig config;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Paginate paginate;
    private PaginateManager paginateManager;
    private List<BaseViewModel> list;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.recycler_view_activity);
        enableToolbarHomeButton(true);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        list = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            StringViewModel stringViewModel = new StringViewModel();
            stringViewModel.name = "" + index;
            list.add(stringViewModel);
        }

        adapter = new BindingRecyclerAdapter(this);
        RecyclerViewConfig.Builder builder = new RecyclerViewConfig.Builder()
                .swipeRefreshLayoutColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW)
                .recyclerLayoutManager(new LinearLayoutManager(this))
                .recyclerViewAnimator(new DefaultItemAnimator())
                .recyclerViewDecor(new DividerGridItemDecoration(this))
                .bind(StringViewModel.class, StringViewHolder.class)
                .enableLoadMore(true)
                .defaultEntities(list)
                .addLoadingListItem(true);

        View floatingButton = findViewById(R.id.fabButton);
        recyclerView.addOnScrollListener(new SimpleHidingScrollListener(floatingButton));

        buildRecyclerViewConfig(builder.build());

        setupConfig();

        return true;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onLoadMore() {
        paginateManager.setLoading(true);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<BaseViewModel> data = list.subList(0, 10);
                onLoadCompleted(data);
            }
        }, 2000);
    }

    @Override
    public boolean isLoading() {
        return paginateManager.getLoading();
    }

    @Override
    public boolean hasLoadedAllItems() {
        return paginateManager.hasLoadCompleted();
    }

    @Override
    public void onRefresh() {
        try {
            paginateManager.reset();
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                    List<BaseViewModel> data = list.subList(0, 10);
                    onLoadCompleted(data);
                }
            }, 2000);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setupConfig() {
        setupSwipeRefreshLayout();
        setupRecyclerView();
        if (config != null) {
            paginateManager = new PaginateManager();
            paginateManager.setRequestSize(config.getRequestSize());

            onLoadCompleted(config.getDefaultEntities());
        }

        setupPaginate();
    }

    public void onLoadCompleted(List<BaseViewModel> list) {
        int size = list == null ? 0 : list.size();
        paginateManager.loadCompleted(size);
        paginateManager.setLoading(false);
        if (size == 0) {
            paginate.setHasMoreDataToLoad(false);
            return;
        }

        if (paginateManager.isFirstPaginate()) {
            adapter.clear();
        }

        adapter.addAll(list);
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

        Map<Class<? extends BaseViewModel>, Class<? extends BindingViewHolder>> bindViewHolders =
                config
                .getBindViewHolders();
        if (bindViewHolders != null) {
            for (Map.Entry<Class<? extends BaseViewModel>, Class<? extends BindingViewHolder>> entry
                    : bindViewHolders.entrySet()) {
                adapter.bind(entry.getKey(), entry.getValue());
            }
        }

        this.config = config;
    }
}
