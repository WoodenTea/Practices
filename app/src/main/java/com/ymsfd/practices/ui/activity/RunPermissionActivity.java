package com.ymsfd.practices.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.ymsfd.practices.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WoodenTea.
 * Date: 2016/6/13
 * Time: 10:37
 */
public class RunPermissionActivity extends BasePermissionActivity implements View.OnClickListener {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.run_permission_activity);
        View view = findViewById(R.id.submit);
        view.setOnClickListener(this);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            List<String> list = new ArrayList<>();
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            checkPermissions(list, 0, "You need to allow access to SD card");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    D("Pass");
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest
                            .permission.WRITE_CONTACTS)) {
                        new AlertDialog.Builder(this)
                                .setMessage(Manifest.permission.WRITE_CONTACTS +
                                        " permission denied, please enable it " +
                                        "in Settings-Apps.")
                                .setNegativeButton(R.string.cancel, null)
                                .setPositiveButton(R.string.submit, new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startAppSettings();
                                    }
                                })
                                .create()
                                .show();
                        return;
                    }
                    D("Forbid");
                }
                break;
            }
        }
    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
