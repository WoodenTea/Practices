package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.ymsfd.practices.R;
import com.ymsfd.practices.ui.fragment.AFragment;
import com.ymsfd.practices.ui.fragment.BFragment;
import com.ymsfd.practices.ui.fragment.CFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/24/15
 * Time: 14:40
 */
public class FragmentViewPagerActivity extends BaseActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        enableToolbarHomeButton(true);

        viewPager = (ViewPager) findViewById(R.id.parent);
        setupViewPager();
    }

    private void setupViewPager() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new AFragment());
//        adapter.addFragment(new BFragment());
//        adapter.addFragment(new CFragment());
        viewPager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();

        private FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }
    }
}