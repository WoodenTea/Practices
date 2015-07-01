package com.ymsfd.practices.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ymsfd.practices.R;
import com.ymsfd.practices.fragment.AFragment;
import com.ymsfd.practices.fragment.BFragment;
import com.ymsfd.practices.fragment.CFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/24/15
 * Time: 14:40
 */
public class FragmentViewPagerActivity extends FragmentActivity {
    private List<Fragment> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvt_fragment);

        list = new ArrayList<>();
        list.add(new AFragment());
        list.add(new BFragment());
        list.add(new CFragment());
        ViewPager viewPager = (ViewPager) findViewById(R.id.parent);
        FragmentPagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}