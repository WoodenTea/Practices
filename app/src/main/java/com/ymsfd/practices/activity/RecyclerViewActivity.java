package com.ymsfd.practices.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ymsfd.practices.R;
import com.ymsfd.practices.helper.DividerGridItemDecoration;
import com.ymsfd.practices.helper.RecyclerItemClickListener;
import com.ymsfd.practices.ui.adapter.GeneralRecyclerAdapter;

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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        ArrayList<String> strings = new ArrayList<>();
        for (int index = 0; index < 35; index++) {
            strings.add("" + index);
        }
        GeneralRecyclerAdapter<String> adapter = new GeneralRecyclerAdapter<String>() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.textView.setText(list.get(position));
            }

            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .item_common, parent, false);
                return new ItemViewHolder(view);
            }

            class ItemViewHolder extends RecyclerView.ViewHolder {
                TextView textView;

                public ItemViewHolder(View itemView) {
                    super(itemView);
                    textView = (TextView) this.itemView.findViewById(R.id.text);
                }
            }
        };
        adapter.addAll(strings);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new
                RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Snackbar.make(findViewById(R.id.recycler_view), "Position: " + position,
                                Snackbar
                                        .LENGTH_LONG)
                                .setAction("Action", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(
                                                RecyclerViewActivity.this,
                                                "Toast comes out",
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

    public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
        private static final int HIDE_THRESHOLD = 20;
        private int scrolledDistance = 0;
        private boolean controlsVisible = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            D("dy: " + dy);
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                onHide();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                onShow();
                controlsVisible = true;
                scrolledDistance = 0;
            }

            if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                scrolledDistance += dy;
            }
        }

        public abstract void onHide();

        public abstract void onShow();
    }
}
