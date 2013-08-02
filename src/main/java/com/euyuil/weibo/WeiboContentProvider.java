package com.euyuil.weibo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Yue on 13-7-31.
 */

public class WeiboContentProvider extends ContentProvider {

    private static final int FRIENDS_ENTRIES = 1;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI("com.euyuil.weibo", "#/friends/entries", FRIENDS_ENTRIES);

    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        switch (matcher.match(uri)) {
            case FRIENDS_ENTRIES:
                break;
            default:
                break;
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
