package com.ymsfd.practices.ui.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
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
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private SparseArray<View> mViews;
    private SparseArray<View.OnClickListener> listeners;
    private OnItemClickListener itemListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mViews = new SparseArray<>();
        listeners = new SparseArray<>();
    }

    @Override
    public void onClick(View view) {
        if (itemListener != null) {
            itemListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemListener = listener;
    }

    public TextView findTextViewById(int viewId) {
        return (TextView) findView(viewId);
    }

    public View findView(int viewId) {
        return findViewById(viewId);
    }

    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public Button findButtonById(int viewId) {
        return (Button) findView(viewId);
    }

    public ImageView findImageViewById(int viewId) {
        return (ImageView) findView(viewId);
    }

    public ImageButton findImageButtonById(int viewId) {
        return (ImageButton) findView(viewId);
    }

    public EditText findEditTextById(int viewId) {
        return (EditText) findView(viewId);
    }

    public RecyclerViewHolder setText(@IdRes int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);

        return this;
    }

    public void setSubviewClickListener(@IdRes int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
        listeners.put(viewId, listener);
    }

    public View.OnClickListener getSubviewClickListener(@IdRes int viewID) {
        return listeners.get(viewID);
    }

    public RecyclerViewHolder setBackground(@IdRes int viewId, @DrawableRes int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);

        return this;
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
