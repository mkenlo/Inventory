package com.mkenlo.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mkenlo.inventory.data.InventoryContract;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {

    private TextView itemName;
    private TextView itemDescription;
    private TextView itemQuantity;
    private TextView itemPrice;
    private ImageButton itemImage;
    private String itemImageEncode;
    private static final int REQUEST_IMAGE_PICK = 1;

    private final TextWatcher validator = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                TextView required = (TextView) findViewById(R.id.required);
                required.setText("All fields are required");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        itemName = (TextView) findViewById(R.id.article_name);
        itemDescription = (TextView) findViewById(R.id.article_description);
        itemQuantity = (TextView) findViewById(R.id.article_quantity);
        itemPrice = (TextView) findViewById(R.id.article_price);
        itemImage = (ImageButton) findViewById(R.id.article_image);
        Button itemSave = (Button) findViewById(R.id.save_item);

        itemName.addTextChangedListener(validator);
        itemDescription.addTextChangedListener(validator);
        itemQuantity.addTextChangedListener(validator);
        itemPrice.addTextChangedListener(validator);

        itemImageEncode = null;

        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto(v);
            }
        });

        itemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = !itemName.getText().toString().isEmpty() &&
                        !itemDescription.getText().toString().isEmpty() &&
                        !itemQuantity.getText().toString().isEmpty() &&
                        !itemPrice.getText().toString().isEmpty();
                if (itemImageEncode == null) {
                    Toast.makeText(v.getContext(), "Select a photo", Toast.LENGTH_LONG).show();
                } else if (!valid) {
                    Toast.makeText(v.getContext(), "All fields are required", Toast.LENGTH_LONG).show();
                } else if (addNewItem() != null) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), "Oops!! Error occurred", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private Uri addNewItem() {

        String qty = itemQuantity.getText().toString();
        String price = itemPrice.getText().toString();

        ContentValues values = new ContentValues();
        values.put(InventoryContract.Entries.ARTICLE_NAME, itemName.getText().toString());
        values.put(InventoryContract.Entries.ARTICLE_DESCRIPTION, itemDescription.getText().toString());
        values.put(InventoryContract.Entries.ARTICLE_QUANTITY, Integer.valueOf(qty));
        values.put(InventoryContract.Entries.ARTICLE_PRICE, Double.valueOf(price));
        values.put(InventoryContract.Entries.ARTICLE_IMAGE, itemImageEncode);

        return getContentResolver().insert(InventoryContract.URI_ARTICLES, values);

    }

    public void pickPhoto(View v) {
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePictureIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            Bitmap imageBitmap;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                itemImage.setImageBitmap(imageBitmap);
                itemImageEncode = Utils.encodeItemImage(imageBitmap);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
