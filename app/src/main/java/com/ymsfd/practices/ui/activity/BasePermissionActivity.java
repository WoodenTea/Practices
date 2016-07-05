package com.ymsfd.practices.ui.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.ymsfd.practices.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WoodenTea.
 * Date: 2016/6/8
 * Time: 16:57
 */
public abstract class BasePermissionActivity extends BaseActivity {

    protected boolean checkPermissions(List<String> permissions,
                                       final int permissionCode,
                                       String tips) {
        if (permissions == null || permissions.size() == 0) {
            throw new RuntimeException("Permission is null");
        }

        List<String> list = new ArrayList<>();
        for (final String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    showTipsMessage(tips, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String[] permissions = new String[]{permission};
                            handlePermissions(permissions, permissionCode);
                        }
                    });
                } else {
                    list.add(permission);
                }
            }
        }

        if (list.size() > 0) {
            handlePermissions(list.toArray(new String[list.size()]), permissionCode);
        }

        return list.size() == 0;
    }

    private void handlePermissions(String[] permissions, int permissionCode) {
        ActivityCompat.requestPermissions(this,
                permissions,
                permissionCode);
    }

    protected void showTipsMessage(String tips, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this).setMessage(tips)
                .setPositiveButton(getString(R.string.submit), listener)
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();
    }
}
