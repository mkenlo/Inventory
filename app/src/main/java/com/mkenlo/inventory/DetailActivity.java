package com.mkenlo.inventory;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.mkenlo.inventory.data.InventoryContract;

public class DetailActivity extends AppCompatActivity {

    TextView itemName;
    TextView itemQuantity;
    TextView itemPrice;
    TextView itemDescription;
    ImageView article_image;
    long article_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemName = (TextView) findViewById(R.id.article_name);
        itemQuantity = (TextView) findViewById(R.id.article_quantity);
        itemPrice = (TextView) findViewById(R.id.article_price);
        itemDescription = (TextView) findViewById(R.id.article_description);
        article_image = (ImageView) findViewById(R.id.article_image);

        Intent intent = getIntent();
        article_id = intent.getLongExtra("itemId", 1);
        Uri uri = ContentUris.withAppendedId(InventoryContract.CONTENT_URI, article_id);
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
            // article_image.setImageBitmap((cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_IMAGE))));

            // MenuItem edit_menu = (MenuItem) findViewById(R.id.menu_edit);

            // edit_menu.setIntent(edit);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_edit:

                Intent edit = new Intent(this, EditActivity.class);
                edit.putExtra("itemId", article_id);
                startActivity(edit);
                break;

            case R.id.menu_save:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void adjustItemQuantity(int value){

    }
}
