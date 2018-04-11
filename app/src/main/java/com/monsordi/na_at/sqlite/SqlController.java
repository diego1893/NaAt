package com.monsordi.na_at.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.monsordi.na_at.Worker;

/**
 * Created by diego on 09/04/18.
 */

public class SqlController {
    private FeedReaderDbHelper mDbHelper;

    public SqlController(Context context){
        this.mDbHelper = new FeedReaderDbHelper(context);
    }
    public long saveData(String tableName,Worker Worker){
        return mDbHelper.getWritableDatabase().insert(tableName,null,Worker.toContentValues());
    }
    public int editData(String tableName,Worker object, String whereClause, String[] whereArgs){
        return mDbHelper.getWritableDatabase().update(tableName,object.toContentValues(),whereClause,whereArgs);
    }
    public int editDataWhereId(String tableName,Worker object, String id){
        String query = "=?";
        return editData(tableName,object, FeedReaderContract.FeedEntry._ID + query,new String[]{id});
    }
    public int deleteData(String tableName,String whereClause,String[] whereArgs){
        return mDbHelper.getWritableDatabase().delete(tableName,whereClause,whereArgs);
    }

    public int deleteDataWhereId(String tableName,String id){
        String query = "=?";
        return deleteData(tableName,FeedReaderContract.FeedEntry._ID + query,new String[]{id});
    }

    public Cursor readData(String tableName, String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy, String
            limit){
        Cursor cursor =
                mDbHelper.getWritableDatabase().query(tableName,columns,whereClause,whereArgs,groupBy,having,orderBy,limit);
        if(cursor!=null)
            cursor.moveToFirst();
        return cursor;
    }
    public Cursor selectColumnsFromDb(String tableName,String[] columns){
        return readData(tableName,columns,null,null,null,null,null,null);
    }
    public Cursor selectColumnsFromDbWhereId(String tableName,String[] columns,String id){
        String query = "=?";
        return readData(tableName,columns, FeedReaderContract.FeedEntry._ID + query,new String[]{id},null,null,null,null);
    }

    public void closeDatabase(){
        mDbHelper.close();
    }
}
