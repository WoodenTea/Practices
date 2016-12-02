package com.ymsfd.practices.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ymsfd.practices.domain.DaoMaster;
import com.ymsfd.practices.domain.UserDao;

public class UpdateOpenHelper extends DaoMaster.OpenHelper {
    public UpdateOpenHelper(Context context, String name) {
        super(context, name);
    }

    public UpdateOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @SuppressWarnings({"unchecked", "varargs"})
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, UserDao.class);
    }
}
