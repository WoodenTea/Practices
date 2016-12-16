package com.ymsfd.practices.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.WTLogger;
import com.ymsfd.practices.ui.activity.TestActivity;

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
        D("onCreateView");
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
            }
        });
        tv_name.setText(getClass().getSimpleName());

        return view;
    }

    @Override
    public void onStart() {
        D("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        D("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        D("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        D("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        D("onDestroyView");
        super.onDestroyView();
    }

    void D(String message) {
        WTLogger.d("BaseFragment", message);
    }
}
