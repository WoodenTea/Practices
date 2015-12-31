package com.ymsfd.practices.support.file;

import android.os.Environment;

import com.ymsfd.practices.support.util.GlobalContext;

import java.io.File;

public class FileManager {
    private static final String LOG = "log";
    private static volatile boolean cantRead = false;

    public static String getLogDir() {
        if (!isExternalStorageMounted()) {
            return "";
        } else {
            String path = getSdCardPath() + File.separator + LOG;
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            return path;
        }
    }

    public static boolean isExternalStorageMounted() {
        boolean canRead = Environment.getExternalStorageDirectory().canRead();
        boolean onlyRead = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean unMounted = Environment.getExternalStorageState().equals(
                Environment.MEDIA_UNMOUNTED);

        return !(!canRead || onlyRead || unMounted);
    }

    public static String getSdCardPath() {
        if (isExternalStorageMounted()) {
            File path = GlobalContext.getInstance().getExternalCacheDir();
            if (path != null) {
                return path.getAbsolutePath();
            } else {
                if (!cantRead) {
                    cantRead = true;
                }
            }
        } else {
            return "";
        }

        return "";
    }
}
