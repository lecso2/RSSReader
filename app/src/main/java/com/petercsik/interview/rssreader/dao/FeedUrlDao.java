package com.petercsik.interview.rssreader.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FeedUrlDao extends SQLiteOpenHelper {

    private static FeedUrlDao instance;

    public static synchronized FeedUrlDao getInstance(Context context) {
        if (instance == null) {
            instance = new FeedUrlDao(context);
        }
        return instance;
    }

    //database name
    private static final String DATABASE_NAME = "RSSFeedReader.db";
    private static final int DATABASE_VERSION = 1;

    //table names
    public static final String TABLE_FEEDS = "feeds";

    //feeds table
    public static final String FEEDS_COLUMN_NAME = "name";

    // Songs table create statement
    private static final String CREATE_TABLE_FEEDS = "CREATE TABLE "
            + TABLE_FEEDS + "(" + FEEDS_COLUMN_NAME + " TEXT" + ")";


    protected FeedUrlDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FEEDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDS);
        onCreate(db);
    }

    public boolean insertFeed(String feed) {
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FEEDS_COLUMN_NAME, feed.trim());
        db.insert(TABLE_FEEDS, null, contentValues);
        return true;
    }

    public void deleteFeed(String feed) {
        SQLiteDatabase db = instance.getWritableDatabase();
        db.delete(TABLE_FEEDS, FEEDS_COLUMN_NAME + "=?",
                new String[]{String.valueOf(feed.trim())});
    }

    public boolean modifyFeed(String oldFeed, String newFeed) {
        deleteFeed(oldFeed.trim());
        insertFeed(newFeed.trim());
        return true;
    }

    public List<String> getAllFeeds() {
        List<String> array_list = new ArrayList<>();

        SQLiteDatabase db = instance.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_FEEDS, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            String feed = res.getString(res.getColumnIndex(FEEDS_COLUMN_NAME));
            array_list.add(feed);
            res.moveToNext();
        }
        return array_list;
    }

}
