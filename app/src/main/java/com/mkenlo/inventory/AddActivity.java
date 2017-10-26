package com.mkenlo.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mkenlo.inventory.data.InventoryContract;


public class AddActivity extends AppCompatActivity {

    TextView itemName;
    TextView itemDescription;
    TextView itemQuantity;
    TextView itemPrice;
    ImageView itemImage;
    ImageButton itemSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        itemName = (TextView) findViewById(R.id.article_name);
        itemDescription = (TextView) findViewById(R.id.article_description);
        itemQuantity = (TextView) findViewById(R.id.article_quantity);
        itemPrice = (TextView) findViewById(R.id.article_price);
        itemImage = (ImageView) findViewById(R.id.article_image);
        itemSave = (ImageButton) findViewById(R.id.save_item);

        itemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (addNewItem(v) != null) {
                Toast.makeText(v.getContext(), "Item saved", Toast.LENGTH_LONG);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            } else Toast.makeText(v.getContext(), "Oups!! Error occured", Toast.LENGTH_LONG);
            }
        });


    }

    public Uri addNewItem(View v) {

        String qty = itemQuantity.getText().toString();
        String price = itemPrice.getText().toString();
        String imgLink = "Dummy string image";

        ContentValues values = new ContentValues();
        values.put(InventoryContract.Entries.ARTICLE_NAME, itemName.getText().toString());
        values.put(InventoryContract.Entries.ARTICLE_DESCRIPTION, itemDescription.getText().toString());
        values.put(InventoryContract.Entries.ARTICLE_QUANTITY, Integer.valueOf(qty));
        values.put(InventoryContract.Entries.ARTICLE_PRICE, Double.valueOf(price));
        values.put(InventoryContract.Entries.ARTICLE_IMAGE, imgLink);

        return getContentResolver().insert(InventoryContract.CONTENT_URI, values);

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
                break;

            case R.id.menu_save:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
