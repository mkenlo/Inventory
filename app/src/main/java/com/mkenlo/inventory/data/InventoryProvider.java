package com.mkenlo.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.content.UriMatcher;


public class InventoryProvider extends ContentProvider {


    private static final int ARTICLE = 100;
    private static final int ARTICLE_ID = 200;
    private static final int DELETED_ARTICLE = 500;


    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(InventoryContract.AUTHORITY, InventoryContract.CONTENT_PATH, ARTICLE);
        sURIMatcher.addURI(InventoryContract.AUTHORITY, InventoryContract.CONTENT_PATH+"/#", ARTICLE_ID);
    }

    InventoryDbHelper mDbHelper;


    @Override
    public boolean onCreate() {

        mDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = sURIMatcher.match(uri);

        switch (match) {
            case ARTICLE:
                break;
            case ARTICLE_ID:
                selection = InventoryContract.Entries.ARTICLE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        cursor = db.query(InventoryContract.Entries.TABLE_ARTICLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sURIMatcher.match(uri);

        if ( match == ARTICLE){
            long itemId = db.insert(InventoryContract.Entries.TABLE_ARTICLE, null, contentValues);
            return itemId>0 ? ContentUris.withAppendedId(uri,itemId) : null;
        }
        else{
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        //return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        /**
         * DELETE * FROM ARTICLES WHERE ARTICLE_ID = ??
         */

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (sURIMatcher.match(uri) == ARTICLE_ID){
            selection = InventoryContract.Entries.ARTICLE_ID + "=?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            return db.delete(InventoryContract.Entries.TABLE_ARTICLE, selection,selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        /**
         *
         * UPDATE FROM ARTICLES WHERE ARTICLE_ID =?
         */
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (sURIMatcher.match(uri) == ARTICLE_ID){
            selection = InventoryContract.Entries.ARTICLE_ID + "=?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            return db.update(InventoryContract.Entries.TABLE_ARTICLE, contentValues, selection,selectionArgs);
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sURIMatcher.match(uri);
        switch (match) {
            case ARTICLE:
                return InventoryContract.CONTENT_LIST_TYPE;
            case ARTICLE_ID:
                return InventoryContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }

}
