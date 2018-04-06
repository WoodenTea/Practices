package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        enableToolbarUp(true);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.parent);
        setupViewPager();
    }

    private void setupViewPager() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new AFragment(), "A");
        adapter.addFragment(new BFragment(), "B");
        adapter.addFragment(new CFragment(), "C");
        viewPager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);

        tabLayout.setupWithViewPager(viewPager);
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> titles = new ArrayList<>();

        private FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }
    }
}