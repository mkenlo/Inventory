package com.mkenlo.inventory;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mkenlo.inventory.data.InventoryContract;

import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_EMAIL;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_ID;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_NAME;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_PHONE;
import static com.mkenlo.inventory.data.InventoryContract.Entries.SUPPLIER_WEBSITE;

public class SupplierActivity extends AppCompatActivity {

    private TextView supplierName;
    private TextView supplierEmail;
    private TextView supplierPhone;
    private TextView supplierWebsite;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.add_supplier);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (add() != null) {
                    Toast.makeText(view.getContext(), "Information saved!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "Error Occured!", Toast.LENGTH_LONG).show();
                }
                updateUI();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        supplierName = (TextView) findViewById(R.id.supplier_name);
        supplierEmail = (TextView) findViewById(R.id.supplier_email);
        supplierPhone = (TextView) findViewById(R.id.supplier_phone);
        supplierWebsite = (TextView) findViewById(R.id.supplier_website);

        updateUI();

    }

    private Uri add() {

        boolean valid = !supplierName.getText().toString().isEmpty() &&
                !supplierEmail.getText().toString().isEmpty() &&
                !supplierPhone.getText().toString().isEmpty();

        if (!valid) {
            Toast.makeText(this, "Name, Phone and Email required!", Toast.LENGTH_LONG).show();
            return null;
        } else {
            ContentValues values = new ContentValues();
            values.put(InventoryContract.Entries.SUPPLIER_NAME, supplierName.getText().toString());
            values.put(InventoryContract.Entries.SUPPLIER_EMAIL, supplierEmail.getText().toString());
            values.put(InventoryContract.Entries.SUPPLIER_PHONE, supplierPhone.getText().toString());
            values.put(InventoryContract.Entries.SUPPLIER_WEBSITE, supplierWebsite.getText().toString());
            return getContentResolver().insert(InventoryContract.URI_SUPPLIERS, values);
        }
    }

    public void updateUI() {
        String projection[] = {SUPPLIER_ID,
                SUPPLIER_NAME,
                SUPPLIER_EMAIL,
                SUPPLIER_PHONE,
                SUPPLIER_WEBSITE};

        Cursor cursor = getContentResolver().query(InventoryContract.URI_SUPPLIERS, projection, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            supplierName.setText(cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_NAME)));
            supplierEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_EMAIL)));
            supplierPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PHONE)));
            supplierWebsite.setText(cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_WEBSITE)));

            fab.setClickable(false);
            fab.setEnabled(false);
        }
    }
}
