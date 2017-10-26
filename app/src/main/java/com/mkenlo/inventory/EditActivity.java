package com.mkenlo.inventory;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mkenlo.inventory.data.InventoryContract;

public class EditActivity extends AppCompatActivity {


    TextView itemName;
    TextView itemDescription;
    TextView itemQuantity;
    TextView itemPrice;
    ImageView itemImage;
    ImageButton itemSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        itemName = (TextView) findViewById(R.id.article_name);
        itemDescription = (TextView) findViewById(R.id.article_description);
        itemQuantity = (TextView) findViewById(R.id.article_quantity);
        itemPrice = (TextView) findViewById(R.id.article_price);
        itemImage = (ImageView) findViewById(R.id.article_image);
        itemSave = (ImageButton) findViewById(R.id.save_item);

        Bundle bundle = getIntent().getExtras();
        long itemId = bundle.getLong("itemId", 1);

        Uri uri = ContentUris.withAppendedId(InventoryContract.CONTENT_URI, itemId);
        Cursor cursor = getContentResolver().query(
                uri,
                InventoryContract.Entries.PROJECTION,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            itemDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_DESCRIPTION)));
            itemName.setText(cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_NAME)));

            Double price = cursor.getDouble(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_QUANTITY));
            itemQuantity.setText(String.valueOf(quantity));
            itemPrice.setText(price.toString());

            /**
             * TODO:  get Bitmap image from a string value
             */

        }

        itemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (editItem(v)) {
                Toast.makeText(v.getContext(), "Edit Item saved", Toast.LENGTH_LONG);
            } else Toast.makeText(v.getContext(), "Oups!! Error occured", Toast.LENGTH_LONG);
            }
        });
    }

    public boolean editItem(View v) {

        return true;

    }

}
