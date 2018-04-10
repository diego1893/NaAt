package com.monsordi.na_at;

import android.content.ContentValues;
import android.database.Cursor;
import com.monsordi.na_at.sqlite.FeedReaderContract.FeedEntry;

import java.io.Serializable;

/**
 * Created by diego on 09/04/18.
 */

public class Worker{

    private String name;
    private String email;
    private String job;
    private String imageUrl;

    public Worker(String name, String email, String job, String imageUrl) {
        this.name = name;
        this.email = email;
        this.job = job;
        this.imageUrl = imageUrl;
    }

    public Worker(Cursor cursor){
        this.name = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME));
        this.email = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_EMAIL));
        this.job = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_JOB));
        this.imageUrl = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_IMAGE));
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getJob() {
        return job;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ContentValues toContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedEntry.COLUMN_NAME,name);
        contentValues.put(FeedEntry.COLUMN_EMAIL,email);
        contentValues.put(FeedEntry.COLUMN_JOB,job);
        contentValues.put(FeedEntry.COLUMN_IMAGE,imageUrl);
        return contentValues;
    }
}
