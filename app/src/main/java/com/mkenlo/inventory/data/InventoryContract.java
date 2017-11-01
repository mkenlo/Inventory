package com.mkenlo.inventory.data;


import android.content.ContentResolver;
import android.provider.BaseColumns;
import android.net.Uri;

public final class InventoryContract {

    public static final String AUTHORITY = "com.mkenlo.inventory";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ AUTHORITY);

    public static final String PATH_ARTICLES = "articles";
    public static final String PATH_SUPPLIERS = "suppliers";

    public static final Uri URI_ARTICLES = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ARTICLES);
    public static final Uri URI_SUPPLIERS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SUPPLIERS);

    /**
     * The MIME type of the {@link #URI_ARTICLES} for a list of articles.
     */
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_ARTICLES;

    /**
     * The MIME type of the {@link #URI_ARTICLES} for a single item.
     */
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_ARTICLES;


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

        public static final String TABLE_SUPPLIER = "suppliers";
        public static final String SUPPLIER_ID = BaseColumns._ID;
        public static final String SUPPLIER_NAME = "supplierName";
        public static final String SUPPLIER_EMAIL = "supplierEmailAddress";
        public static final String SUPPLIER_PHONE = "supplierPhone";
        public static final String SUPPLIER_WEBSITE = "supplierWebsite";


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
