package com.mkenlo.inventory.data;


import android.provider.BaseColumns;

public class InventoryContract {

    public InventoryContract() {
    }

    public static class Inventory implements BaseColumns {
        public static final String TABLE_ARTICLE = "articles";
        public static final String ARTICLE_ID = BaseColumns._ID;
        public static final String ARTICLE_NAME = "articleName";
        public static final String ARTICLE_IMAGE = "articleImage";
        public static final String ARTICLE_CATEGORY = "articleCategory";
        public static final String ARTICLE_PRICE = "articlePrice";
        public static final String ARTICLE_QUANTITY = "articleQuantity";
    }

}
