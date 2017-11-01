package com.mkenlo.inventory;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mkenlo.inventory.data.ArticleModel;
import com.mkenlo.inventory.data.InventoryContract;

import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_EMAIL;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_ID;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_NAME;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_PHONE;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_WEBSITE;


public class DetailActivity extends AppCompatActivity {

    private TextView itemName;
    private TextView itemQuantity;
    private TextView itemPrice;
    private TextView itemDescription;
    private ImageView itemImage;
    private ArticleModel item;
    private Uri itemUri;
    private final static int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private boolean MY_PERMISSIONS_REQUEST_GRANTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemName = (TextView) findViewById(R.id.article_name);
        itemQuantity = (TextView) findViewById(R.id.article_quantity);
        itemPrice = (TextView) findViewById(R.id.article_price);
        itemDescription = (TextView) findViewById(R.id.article_description);
        itemImage = (ImageView) findViewById(R.id.article_image);
        Button incrementButton = (Button) findViewById(R.id.increment_quantity);
        Button decrementButton = (Button) findViewById(R.id.decrement_quantity);
        item = new ArticleModel();

        Intent intent = getIntent();

        item.setId(intent.getLongExtra("itemId", 1));
        itemUri = ContentUris.withAppendedId(InventoryContract.URI_ARTICLES, item.getId());
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
                orderMoreArticle();
                break;
            case R.id.menu_settings:
                startActivity(new Intent(this, SupplierActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void adjustItemQuantity(String order) {
        int qty = item.getQuantity();
        EditText adjustView = (EditText) findViewById(R.id.article_quantity_adjust);
        int adjust = Integer.valueOf(adjustView.getText().toString());
        switch (order) {
            case "increment":
                item.setQuantity(qty + adjust);
                break;
            case "decrement":
                if (qty > 0 && qty > adjust)
                    item.setQuantity(qty - adjust);
                break;
        }
        updateItem();
        updateUI();
    }

    public void updateItem() {
        ContentValues values = new ContentValues();
        values.put(InventoryContract.Entries.ARTICLE_QUANTITY, item.getQuantity());
        getContentResolver().update(itemUri, values, null, null);
    }

    public void deleteItem() {
        getContentResolver().delete(itemUri, null, null);
        startActivity(getParentActivityIntent());
    }

    public void confirmDeletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_delete_message)
                .setTitle(R.string.alert_title);
        builder.setPositiveButton(R.string.positive_del, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                deleteItem();
            }
        });

        builder.setNegativeButton(R.string.negative_del, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
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
            Bitmap imageBitmap = Utils.decodeItemImage(cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Entries.ARTICLE_IMAGE)));
            if (imageBitmap != null)
                itemImage.setImageBitmap(imageBitmap);
            else itemImage.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }

        cursor.close();

    }

    public void orderMoreArticle() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
        else{
            String phone = getSupplierInfo();
            if (phone!=null)
                makePhoneCall(phone);
        }
    }

    public String getSupplierInfo(){
        String projection[] = {SUPPLIER_ID,
                SUPPLIER_NAME,
                SUPPLIER_EMAIL,
                SUPPLIER_PHONE,
                SUPPLIER_WEBSITE};

        Cursor cursor = getContentResolver().query(InventoryContract.URI_SUPPLIERS, projection, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PHONE));
            cursor.close();
            return phone;
        } else {
            Toast.makeText(this, "Add supplier info in Settings", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void makePhoneCall(String phone) {
        Intent callIntent = new Intent();
        callIntent.setAction(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        Toast.makeText(this, "Calling Supplier at " + phone, Toast.LENGTH_LONG).show();
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String phone = getSupplierInfo();
                    if (phone!=null)
                        makePhoneCall(phone);
                } else {
                    Toast.makeText(this, "Permissions request Denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
