package com.mkenlo.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.mkenlo.inventory.data.InventoryContract.Entries.ARTICLE_DESCRIPTION;
import static com.mkenlo.inventory.data.InventoryContract.Entries.ARTICLE_ID;
import static com.mkenlo.inventory.data.InventoryContract.Entries.ARTICLE_IMAGE;
import static com.mkenlo.inventory.data.InventoryContract.Entries.ARTICLE_NAME;
import static com.mkenlo.inventory.data.InventoryContract.Entries.ARTICLE_PRICE;
import static com.mkenlo.inventory.data.InventoryContract.Entries.ARTICLE_QUANTITY;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_ADDRESS;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_ID;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_NAME;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_PHONE;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_WEBSITE;
import static com.mkenlo.inventory.data.InventoryContract.Entries.TABLE_ARTICLE;
import static com.mkenlo.inventory.data.InventoryContract.Entries.TABLE_SUPPLIER;

public class InventoryDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "inventory.db";


    private static final String CREATE_TABLE_ARTICLE =
            "CREATE TABLE " + TABLE_ARTICLE + " (" +
                    ARTICLE_ID + " INTEGER PRIMARY KEY," +
                    ARTICLE_IMAGE + " TEXT," +
                    ARTICLE_NAME + " TEXT," +
                    ARTICLE_PRICE + " REAL, " +
                    ARTICLE_DESCRIPTION + " TEXT," +
                    ARTICLE_QUANTITY + " INTEGER )";

    private static final String CREATE_TABLE_SUPPLIER =
            "CREATE TABLE " + TABLE_SUPPLIER + " (" +
                    SUPPLIER_ID + " INTEGER PRIMARY KEY," +
                    SUPPLIER_NAME + " TEXT," +
                    SUPPLIER_ADDRESS + " TEXT, " +
                    SUPPLIER_PHONE + " INTEGER," +
                    SUPPLIER_WEBSITE+ " TEXT )";

    private static final String DELETE_TABLE_ARTICLE =
            "DROP TABLE IF EXISTS " + TABLE_ARTICLE;

    private static final String DELETE_TABLE_SUPPLIER =
            "DROP TABLE IF EXISTS " + TABLE_SUPPLIER;

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ARTICLE);
        db.execSQL(CREATE_TABLE_SUPPLIER);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_ARTICLE);
        db.execSQL(DELETE_TABLE_SUPPLIER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}