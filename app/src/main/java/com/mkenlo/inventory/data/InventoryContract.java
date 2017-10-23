package com.mkenlo.inventory.data;


import android.provider.BaseColumns;
import android.net.Uri;

public class InventoryContract {

    public static final String AUTHORITY = "com.mkenlo.inventory";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ AUTHORITY);

    public static final String ARTICLE_PATH = "articles";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ARTICLE_PATH);

    public InventoryContract() {
    }

    public static class Entries implements BaseColumns {
        public static final String TABLE_ARTICLE = "articles";
        public static final String ARTICLE_ID = BaseColumns._ID;
        public static final String ARTICLE_NAME = "articleName";
        public static final String ARTICLE_IMAGE = "articleImage";
        public static final String ARTICLE_CATEGORY = "articleCategory";
        public static final String ARTICLE_PRICE = "articlePrice";
        public static final String ARTICLE_QUANTITY = "articleQuantity";



    }


}
