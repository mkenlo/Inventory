package com.mkenlo.inventory;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mkenlo.inventory.data.ArticleModel;
import com.mkenlo.inventory.data.InventoryContract;

import static java.security.AccessController.getContext;

public class DetailActivity extends AppCompatActivity {

    TextView itemName;
    TextView itemQuantity;
    TextView itemPrice;
    TextView itemDescription;
    ImageView article_image;
    Button incrementButton;
    Button decrementButton;
    ArticleModel item;
    long article_id;
    Uri itemUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemName = (TextView) findViewById(R.id.article_name);
        itemQuantity = (TextView) findViewById(R.id.article_quantity);
        itemPrice = (TextView) findViewById(R.id.article_price);
        itemDescription = (TextView) findViewById(R.id.article_description);
        article_image = (ImageView) findViewById(R.id.article_image);
        incrementButton = (Button) findViewById(R.id.increment_quantity);
        decrementButton = (Button) findViewById(R.id.decrement_quantity);
        item = new ArticleModel();


        Intent intent = getIntent();

        item.setId(intent.getLongExtra("itemId", 1));
        itemUri = ContentUris.withAppendedId(InventoryContract.CONTENT_URI, item.getId());
        updateUI();

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustItemQuantity("increment");
            }
        });
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustItemQuantity("decrement");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_delete:
                confirmDeletion();
                break;

            case R.id.menu_order:
                Intent openPageIntent = new Intent();
                openPageIntent.setAction(Intent.ACTION_VIEW);
                openPageIntent.setData(Uri.parse("https://www.amazon.com/"));
                startActivity(openPageIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void adjustItemQuantity(String order) {
        int qty = item.getQuantity();
        switch (order) {
            case "increment":
                item.setQuantity(qty+1);
                break;
            case "decrement":
                if(qty>0)
                    item.setQuantity(qty-1);
                break;
        }
        updateItem();
        updateUI();
    }

    public void updateItem() {
        ContentValues values = new ContentValues();
        values.put(InventoryContract.Entries.ARTICLE_QUANTITY, item.getQuantity());
        int res = getContentResolver().update(itemUri, values, null, null);
    }

    public void deleteItem() {
        int res = getContentResolver().delete(itemUri, null, null);
        startActivity(getParentActivityIntent());
    }

    public void confirmDeletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_delete_message)
                .setTitle(R.string.alert_title);
        builder.setPositiveButton(R.string.positive_del, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getBaseContext(), "User clicked Yes", Toast.LENGTH_LONG);
                dialog.dismiss();
                deleteItem();
            }
        });

        builder.setNegativeButton(R.string.negative_del, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getBaseContext(), "User cancelled", Toast.LENGTH_LONG);
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateUI() {
        Cursor cursor = getContentResolver().query(
                itemUri,
                InventoryContract.Entries.PROJECTION,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            item.setName(cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_NAME)));
            item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_DESCRIPTION)));
            item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_QUANTITY)));
            item.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_PRICE)));

            itemDescription.setText(item.getDescription());
            itemName.setText(item.getName());
            itemQuantity.setText(String.valueOf(item.getQuantity()));
            itemPrice.setText(String.valueOf(item.getPrice()));
        }

        /**
         * TODO:  get Bitmap image from a string value
         */
        // article_image.setImageBitmap((cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_IMAGE))));

        // MenuItem edit_menu = (MenuItem) findViewById(R.id.menu_edit);

        // edit_menu.setIntent(edit);
    }
}
