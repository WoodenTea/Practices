package com.ymsfd.practices.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private View rootView;
    protected TextView tv_name;
    protected String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.frg_base, container, false);
            tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            tv_name.setText(getClass().getSimpleName());
            D("Create View 1");
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
                D("Create View 2");
            }

            D("Create View 3");
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        D("Pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        D("Resume " + ((ViewGroup) rootView.getParent()).getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        D("Destroy View");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        D("Destroy");
    }

    private void D(String msg) {
        Log.d(TAG, msg);
    }
}
