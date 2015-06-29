package com.dojoandroid.dojoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dojoandroid.dojoapp.entity.Character;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    /** Log tag */
    private static final String TAG = DBManager.class.getSimpleName();

    /** Database helper instance */
    private DBHelper mDatabaseHelper = null;

    /** Database instance */
    private SQLiteDatabase mDatabase = null;

    /**
     * Constructor
     *
     * @param context of the application
     */
    public DBManager(Context context) {
        mDatabaseHelper = new DBHelper(context);
    }

    /**
     * Open the database
     */
    private void open() throws SQLException {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    /**
     * Close the database
     */
    private void close() {
        mDatabaseHelper.close();
    }

    /**
     * Insert the char on the database
     *
     * @param name of the char
     */
    public void insert(String name) {
        open();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);

        long newItem = mDatabase.insert(DBHelper.TABLE_CHAR, null, values);

        Log.d(TAG, "Inserted " + name + " at position " + newItem);
        close();
    }

    /**
     * Get all the chars
     *
     *
     * @return list of chars
     */
    public List<Character> get() {
        open();

        String name = null;
        String[] allColumns = { DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME };

        List<Character> content = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_CHAR,
                allColumns, null, null, null, null, null);

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));

                Character character = new Character();
                character.setName(name);
                content.add(character);

            } while(cursor.moveToNext());

            cursor.close();
        }

        close();

        return content;
    }
}
