package com.monsordi.na_at.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.monsordi.na_at.sqlite.FeedReaderContract.FeedEntry;
/**
 * Created by diego on 06/04/18.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "feedreader.db";
    private static final String CREATE_WORKER_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME;
    public static final String COLUMNS = " (" + FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FeedReaderContract.FeedEntry.COLUMN_NAME + " TEXT NOT NULL,"
            + FeedEntry.COLUMN_EMAIL + " TEXT NOT NULL,"
            + FeedEntry.COLUMN_JOB + " TEXT NOT NULL,"
            + FeedEntry.COLUMN_IMAGE + " TEXT NOT NULL)";

    private static final String DELETE_PENDING_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORKER_ENTRIES + COLUMNS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_PENDING_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
