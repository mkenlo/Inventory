package com.mkenlo.inventory;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ArticleAdapter extends CursorAdapter {


    public ArticleAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.article_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView article_name = (TextView) view.findViewById(R.id.article_name);
        TextView article_quantity = (TextView) view.findViewById(R.id.article_quantity);
        ImageView article_image = (ImageView) view.findViewById(R.id.article_image);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

        article_name.setText(name);
        article_quantity.setText(String.valueOf(quantity));
    }
}
