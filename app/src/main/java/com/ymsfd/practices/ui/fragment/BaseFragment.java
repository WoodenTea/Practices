package com.ymsfd.practices.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymsfd.practices.R;

/**
 * Created by Deepan.
 * User: ymsfd
 * Date: 4/24/15
 * Time: 14:13
 */
public class BaseFragment extends Fragment {
    protected TextView tv_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        tv_name = (TextView) view.findViewById(R.id.tv_name);

        tv_name.setText(getClass().getSimpleName());
        return view;
    }
}
