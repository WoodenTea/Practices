package com.ymsfd.practices.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.ymsfd.practices.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements
    AdapterView.OnItemClickListener {

    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(navigationView);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        enableToolbarHomeButton(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
            toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);

        Intent intent = getIntent();
        String path = intent.getStringExtra("com.ymsfd.android.practices.Path");
        if (path == null) {
            path = "";
        }

        ListView listView = (ListView) findViewById(R.id.activities);
        listView.setAdapter(new SimpleAdapter(this, getActivities(path),
            android.R.layout.simple_list_item_1,
            new String[]{"title"}, new int[]{android.R.id.text1}));
        listView.setOnItemClickListener(this);

        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> tmp = new HashMap<>();
        tmp.put("title", name);
        tmp.put("intent", intent);
        data.add(tmp);
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        result.putExtra("com.ymsfd.android.practices.Path", path);
        return result;
    }

    private List<Map<String, Object>> getActivities(String prefix) {
        List<Map<String, Object>> mapActivity = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("com.ymsfd.android.practices.SAMPLE");
        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            Log.d(getClass().getSimpleName(), info.packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list) {
            return mapActivity;
        }

        String[] prefixPath;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(mapActivity, nextLabel, activityIntent(
                        info.activityInfo.applicationInfo.packageName,
                        info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(mapActivity, nextLabel, browseIntent(prefix.equals("") ?
                            nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        return mapActivity;
    }
}
