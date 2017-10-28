package com.mkenlo.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    Button itemSave;

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
        itemImage = (ImageView) findViewById(R.id.article_image);
        itemSave = (Button) findViewById(R.id.save_item);

        itemName.addTextChangedListener(validator);
        itemDescription.addTextChangedListener(validator);
        itemQuantity.addTextChangedListener(validator);
        itemPrice.addTextChangedListener(validator);

        itemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = validateInput(itemName.getText().toString()) &&
                        validateInput(itemDescription.getText().toString()) &&
                        validateInput(itemQuantity.getText().toString()) &&
                        validateInput(itemPrice.getText().toString());

                if (valid && addNewItem(v) != null) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                } else if (!valid){
                    TextView required = (TextView) findViewById(R.id.required);
                    required.setText("All fields are required");
                }else{
                    Toast.makeText(v.getContext(), "Oops!! Error occurred", Toast.LENGTH_LONG).show();
                }

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

    public boolean validateInput(String value) {
        if (value.isEmpty())
            return false;
        return true;
    }


}
