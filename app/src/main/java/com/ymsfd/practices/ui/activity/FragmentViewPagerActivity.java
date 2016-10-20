package com.ymsfd.practices.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
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

        setUpActionBar(true);

        viewPager = (ViewPager) findViewById(R.id.parent);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setupViewPager();
        setupTabLayout();
    }

    private void setupTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new AFragment(), R.drawable.ic_schedule);
        adapter.addFragment(new BFragment(), R.drawable.ic_schedule);
        adapter.addFragment(new CFragment(), R.drawable.ic_schedule);
        viewPager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private List<Integer> ids = new ArrayList<>();
        private List<Fragment> fragments = new ArrayList<>();

        private FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            SpannableString spannableString = new SpannableString("ymsfd");
            try {
                Drawable drawable = ContextCompat.getDrawable(FragmentViewPagerActivity.this,R.drawable.ic_schedule);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM);
                spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return spannableString;
        }

        public void addFragment(Fragment fragment, int id) {
            fragments.add(fragment);
            ids.add(id);
        }
    }
}