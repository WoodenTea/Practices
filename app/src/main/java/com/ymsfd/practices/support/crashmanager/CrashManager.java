package com.ymsfd.practices.support.crashmanager;

import android.text.TextUtils;

import com.ymsfd.practices.support.file.FileManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CrashManager {

    private static final int MAX_LOG_FILES = 10;

    public static void registerHandler() {
        Thread.UncaughtExceptionHandler currentHandler = Thread
                .getDefaultUncaughtExceptionHandler();

        if (!(currentHandler instanceof ExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(currentHandler));
        }

        ScheduledExecutorService scheduledExecutorService = Executors
                .newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(new ClearLogTask(), 5, TimeUnit.SECONDS);
    }

    private static void onlyLeftTenLogFilesInStorage() {
        String[] files = searchForStackTraces();
        if (files == null) {
            return;
        }
        int length = files.length;
        if (length > MAX_LOG_FILES) {
            for (int i = MAX_LOG_FILES; i < length; i++) {
                new File(files[i]).delete();
            }
        }
    }

    private static String[] searchForStackTraces() {
        String path = FileManager.getLogDir();
        if (TextUtils.isEmpty(path)) {
            return new String[0];
        }

        File dir = new File(path);

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".log");
            }
        };

        String[] files = dir.list(filter);
        for (int i = 0; i < files.length; i++) {
            files[i] = path + File.separator + files[i];
        }

        Arrays.sort(files, new Comparator<String>() {
            @Override
            public int compare(String aFilePath, String bFilePath) {
                long aDate = new File(aFilePath).lastModified();
                long bDate = new File(bFilePath).lastModified();
                return (aDate > bDate ? -1 : 1);
            }
        });
        return files;
    }

    private static class ClearLogTask implements Runnable {
        @Override
        public void run() {
            onlyLeftTenLogFilesInStorage();
        }
    }
}
