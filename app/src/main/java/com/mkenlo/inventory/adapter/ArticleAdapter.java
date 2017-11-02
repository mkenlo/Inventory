package com.mkenlo.inventory.adapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
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

    private Context context;
    public ArticleAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.content_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView article_name = (TextView) view.findViewById(R.id.article_name);
        TextView article_price = (TextView) view.findViewById(R.id.article_price);
        final TextView article_quantity = (TextView) view.findViewById(R.id.article_quantity);
        ImageView article_image = (ImageView) view.findViewById(R.id.article_image);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_NAME));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_QUANTITY));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_PRICE));

        article_name.setText(name);
        article_quantity.setText(String.valueOf(quantity));
        article_price.setText(String.valueOf(price));
        Bitmap imageBitmap = Utils.decodeItemImage(cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_IMAGE)));
        if (imageBitmap != null)
            article_image.setImageBitmap(imageBitmap);

        final Uri itemUri = ContentUris.withAppendedId(InventoryContract.URI_ARTICLES,
                cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_ID)));



        Button shopItem = (Button) view.findViewById(R.id.shop_article);
        shopItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.valueOf(article_quantity.getText().toString());
                if (qty>0){
                    qty = qty -1;
                    article_quantity.setText(String.valueOf(qty));
                    updateItem(qty, itemUri);
                }

            }
        });
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    private void updateItem(int qty, Uri itemUri){
        ContentValues values = new ContentValues();
        values.put(InventoryContract.Entries.ARTICLE_QUANTITY, qty);
        context.getContentResolver().update(itemUri, values, null, null);
    }
}
