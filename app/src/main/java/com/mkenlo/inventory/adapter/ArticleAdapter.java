package com.mkenlo.inventory.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mkenlo.inventory.R;
import com.mkenlo.inventory.Utils;
import com.mkenlo.inventory.data.InventoryContract;


public class ArticleAdapter extends CursorAdapter {

    public ArticleAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.content_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView article_name = (TextView) view.findViewById(R.id.article_name);
        final TextView article_quantity = (TextView) view.findViewById(R.id.article_quantity);
        ImageView article_image = (ImageView) view.findViewById(R.id.article_image);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_NAME));
        final int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_QUANTITY));

        article_name.setText(name);
        article_quantity.setText(String.valueOf(quantity));
        Bitmap imageBitmap = Utils.decodeItemImage(cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_IMAGE)));
        if (imageBitmap != null)
            article_image.setImageBitmap(imageBitmap);

        Button shopItem = (Button) view.findViewById(R.id.shop_article);
        shopItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article_quantity.setText(String.valueOf(quantity - 1));
            }
        });
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }


}
