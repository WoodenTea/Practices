package com.ymsfd.practices.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by WoodenTea.
 * Date: 2016/2/19
 * Time: 11:57
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public View findView(int viewId) {
        return findViewById(viewId);
    }

    public TextView findTextViewById(int viewId) {
        return (TextView) findView(viewId);
    }

    public Button findButtonById(int viewId) {
        return (Button) findView(viewId);
    }

    public ImageView findImageViewById(int viewId) {
        return (ImageView) findView(viewId);
    }

    public ImageButton findImageButtonId(int viewId) {
        return (ImageButton) findView(viewId);
    }

    public EditText findEditTextById(int viewId) {
        return (EditText) findView(viewId);
    }

    public RecyclerViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public RecyclerViewHolder setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public RecyclerViewHolder setViewClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
