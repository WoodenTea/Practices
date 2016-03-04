package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.task.GenericTask;
import com.ymsfd.practices.infrastructure.task.TaskAdapter;
import com.ymsfd.practices.infrastructure.task.TaskParams;
import com.ymsfd.practices.infrastructure.task.TaskResult;
import com.ymsfd.practices.ui.adapter.GeneralRecyclerAdapter;
import com.ymsfd.practices.ui.adapter.HeaderRecyclerAdapter;
import com.ymsfd.practices.ui.adapter.RecyclerViewHolder;
import com.ymsfd.practices.ui.adapter.fancy.helper.DividerGridItemDecoration;
import com.ymsfd.practices.ui.adapter.fancy.helper.RecyclerItemClickListener;
import com.ymsfd.practices.ui.adapter.util.BottomScrollListener;
import com.ymsfd.practices.ui.adapter.util.GridSpan;
import com.ymsfd.practices.ui.adapter.util.HidingScrollListener;

import java.util.ArrayList;

/**
 * Created by WoodenTea.
 * Date: 2016/1/6
 * Time: 13:48
 */
public class RecyclerViewActivity extends BaseActivity {
    private View floatingButton;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        ArrayList<String> strings = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            strings.add("" + index);
        }
        GeneralRecyclerAdapter<String> adapter = new GeneralRecyclerAdapter<String>(this) {
            @Override
            protected void bindHolder(RecyclerViewHolder holder, int position, String item) {
                holder.setText(R.id.news_title, item);
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_card_view;
            }
        };
        adapter.addAll(strings);
        HeaderRecyclerAdapter a = new HeaderRecyclerAdapter(adapter);
        gridLayoutManager.setSpanSizeLookup(new GridSpan(a, gridLayoutManager.getSpanCount()));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(a);
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_main, recyclerView, false);
            TextView tv = (TextView) view.findViewById(R.id.text);
            tv.setText(String.format(getString(R.string.text), i));
            a.addHeaderView(view);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.item_main, recyclerView, false);
        TextView tv = (TextView) view.findViewById(R.id.text);
        tv.setText(getString(R.string.app_name));
        a.addFooterView(view);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new
                RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Snackbar.make(findViewById(R.id.recycler_view), "Position: " + position,
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
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideView();
            }

            @Override
            public void onShow() {
                showView();
            }
        });
        final BottomScrollListener listener = new BottomScrollListener();
        listener.addLoadMoreListener(new BottomScrollListener.LoadMoreListener() {
            @Override
            public void onLoadMore(final BottomScrollListener l) {
                D("To bottom->" + listener.isLoadMore());
                GenericTask task = new GenericTask() {
                    @Override
                    protected TaskResult _doInBackground(TaskParams... params) {
                        try {
                            Thread.sleep(10000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                task.setListener(new TaskAdapter() {
                    @Override
                    public String getName() {
                        return null;
                    }

                    @Override
                    public void onPostExecute(GenericTask task, TaskResult result) {
                        l.endLoadMore();
                    }
                });
                task.execute();
            }
        });
        recyclerView.addOnScrollListener(listener);
        floatingButton = findViewById(R.id.fabButton);
        return true;
    }

    private void hideView() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) floatingButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        floatingButton.animate().translationY(floatingButton.getHeight() + fabBottomMargin)
                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showView() {
        floatingButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2))
                .start();
    }

}
