package com.ymsfd.practices.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.ymsfd.practices.R;
import com.ymsfd.practices.fragment.CarFragment;
import com.ymsfd.practices.fragment.CateFragment;
import com.ymsfd.practices.fragment.HomeFragment;
import com.ymsfd.practices.fragment.ShareFragment;
import com.ymsfd.practices.fragment.UserFragment;

/**
 * Created by WoodenTea.
 * Date: 12/11/15
 * Time: 22:02
 */
public class TabHostActivity extends FragmentActivity {

    private Class fragmentArray[] = {HomeFragment.class, CateFragment.class, ShareFragment.class,
            CarFragment.class, UserFragment.class};
    private int tabName[] = {R.string.home, R.string.category, R.string.share, R.string.car, R.string.user};
    private int tabView[] = {R.layout.tab_home, R.layout.tab_category, R.layout.tab_share, R.layout.tab_car, R.layout.tab_user};
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvt_tab_host);

        inflater = getLayoutInflater();
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.content);

        int count = fragmentArray.length;
        for (int index = 0; index < count; index++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(getString(tabName[index])).setIndicator(getTabView(tabView[index], tabHost));
            tabHost.addTab(tabSpec, fragmentArray[index], null);
        }
    }

    private View getTabView(int layoutID, ViewGroup parent) {
        return inflater.inflate(layoutID, null);
    }
}