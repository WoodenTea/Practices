package com.ymsfd.practices.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 12/12/15
 * Time: 22:18
 */
public class BaseSupportFragment extends Fragment {
    protected TextView tv_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_base, container, false);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(getClass().getSimpleName());
        return view;
    }
}
