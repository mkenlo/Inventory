package com.mkenlo.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mkenlo.inventory.data.InventoryContract;

import static com.mkenlo.inventory.MainActivity.projection;

public class EditorActivity extends AppCompatActivity {

    TextView itemName;
    TextView itemDescription;
    TextView itemQuantity;
    TextView itemPrice;
    ImageView itemImage;
    ImageButton itemSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        itemName = (TextView) findViewById(R.id.article_name);
        itemDescription = (TextView) findViewById(R.id.article_description);
        itemQuantity = (TextView) findViewById(R.id.article_quantity);
        itemPrice = (TextView) findViewById(R.id.article_price);
        itemImage = (ImageView) findViewById(R.id.article_image);
        itemSave = (ImageButton) findViewById(R.id.save_item);

        itemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void addNewItem(View v){

        String qty = (String) itemQuantity.getText();
        String price = (String) itemPrice.getText();
        String imgLink ;

        ContentValues values = new ContentValues();

        values.put(InventoryContract.Entries.ARTICLE_NAME, String.valueOf(itemName.getText()));
        values.put(InventoryContract.Entries.ARTICLE_DESCRIPTION, String.valueOf(itemDescription.getText()));
        values.put(InventoryContract.Entries.ARTICLE_QUANTITY, Integer.valueOf(qty));
        values.put(InventoryContract.Entries.ARTICLE_PRICE, Double.valueOf(price));

        Uri uri = getContentResolver().insert(InventoryContract.CONTENT_URI, values);
        if (uri!=null){
            Toast.makeText(this, "Item saved", Toast.LENGTH_LONG);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void editItem(){

    }
}
