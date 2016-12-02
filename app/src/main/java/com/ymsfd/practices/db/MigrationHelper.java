package com.ymsfd.practices.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MigrationHelper {
    private static String TAG = "MigrationHelper";

    @SuppressWarnings({"unchecked", "varargs"})
    public static void migrate(SQLiteDatabase db,
                               Class<? extends AbstractDao<?, ?>>... daoClasses) {
        Database database = new StandardDatabase(db);

        generateTempTables(database, daoClasses);

        dropAllTables(database, true, daoClasses);
        createAllTables(database, false, daoClasses);

        restoreData(database, daoClasses);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    private static void generateTempTables(Database db,
                                           Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            String tempTableName = null;

            try {
                DaoConfig daoConfig = new DaoConfig(db, daoClass);
                String tableName = daoConfig.tablename;
                tempTableName = daoConfig.tablename.concat("_TEMP");

                String dropString = "DROP TABLE IF EXISTS " + tempTableName + ";";
                db.execSQL(dropString);

                String insertString = "CREATE TEMPORARY TABLE " + tempTableName +
                        " AS SELECT * FROM " + tableName + ";";
                db.execSQL(insertString);
            } catch (SQLException e) {
                Log.e(TAG, "【Failed to generate temp table】" + tempTableName, e);
            }
        }
    }

    private static String getColumnsStr(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "no columns";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < daoConfig.allColumns.length; i++) {
            builder.append(daoConfig.allColumns[i]);
            builder.append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    @SuppressWarnings({"unchecked", "varargs"})
    private static void dropAllTables(Database db, boolean ifExists,
                                      @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    private static void createAllTables(Database db, boolean ifNotExists,
                                        @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
    }

    /**
     * dao class already define the sql exec method, so just invoke it
     */
    @SuppressWarnings({"unchecked", "varargs"})
    private static void reflectMethod(Database db, String methodName, boolean isExists,
                                      @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        if (daoClasses.length < 1) {
            return;
        }

        try {
            for (Class cls : daoClasses) {
                Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
                method.invoke(null, db, isExists);
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"unchecked", "varargs"})
    private static void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            String tempTableName = null;

            try {
                DaoConfig daoConfig = new DaoConfig(db, daoClass);
                String tableName = daoConfig.tablename;
                tempTableName = daoConfig.tablename.concat("_TEMP");
                // get all columns from tempTable, take careful to use the columns list
                List<String> columns = getColumns(db, tempTableName);
                ArrayList<String> properties = new ArrayList<>(columns.size());
                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;
                    if (columns.contains(columnName)) {
                        properties.add(columnName);
                    }
                }
                if (properties.size() > 0) {
                    final String columnSQL = TextUtils.join(",", properties);

                    String insertTableStringBuilder = "INSERT INTO " + tableName +
                            " (" + columnSQL + ") SELECT " + columnSQL +
                            " FROM " + tempTableName + ";";
                    db.execSQL(insertTableStringBuilder);
                }
                db.execSQL("DROP TABLE " + tempTableName);
            } catch (SQLException e) {
                Log.e(TAG, "【Failed to restore data from temp table (probably new table)】" +
                        tempTableName, e);
            }
        }
    }

    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (null == columns)
                columns = new ArrayList<>();
        }

        return columns;
    }
}
