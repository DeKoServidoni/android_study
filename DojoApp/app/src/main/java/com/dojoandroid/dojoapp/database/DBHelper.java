package com.dojoandroid.dojoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    public static final String TABLE_CHAR = "characteres";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "character_name";

    /** Database constants */
    private static final String DATABASE_NAME = "database_dojo.db";
    private static final int DATABASE_VERSION = 1;

    /** Database queries */
    private static final String CREATE_TABLE_CHAR = "create table "
            + TABLE_CHAR + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_CHAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // empty
    }
}
