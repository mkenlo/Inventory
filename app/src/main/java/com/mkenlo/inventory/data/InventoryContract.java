package com.mkenlo.inventory.data;


import android.content.ContentResolver;
import android.provider.BaseColumns;
import android.net.Uri;

public final class InventoryContract {

    public static final String AUTHORITY = "com.mkenlo.inventory";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ AUTHORITY);

    public static final String CONTENT_PATH = "articles";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, CONTENT_PATH);

    /**
     * The MIME type of the {@link #CONTENT_URI} for a list of articles.
     */
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + CONTENT_PATH;

    /**
     * The MIME type of the {@link #CONTENT_URI} for a single item.
     */
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + CONTENT_PATH;


    private InventoryContract() {
    }

    public static class Entries implements BaseColumns {
        public static final String TABLE_ARTICLE = "articles";
        public static final String ARTICLE_ID = BaseColumns._ID;
        public static final String ARTICLE_NAME = "articleName";
        public static final String ARTICLE_IMAGE = "articleImage";
        public static final String ARTICLE_DESCRIPTION = "articleDescription";
        public static final String ARTICLE_PRICE = "articlePrice";
        public static final String ARTICLE_QUANTITY = "articleQuantity";

        public static final String[] PROJECTION = {
                ARTICLE_ID,
                ARTICLE_NAME,
                ARTICLE_IMAGE,
                ARTICLE_PRICE,
                ARTICLE_QUANTITY,
                ARTICLE_DESCRIPTION
        };

    }


}
