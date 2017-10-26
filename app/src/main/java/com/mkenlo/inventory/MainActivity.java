package com.mkenlo.inventory;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.mkenlo.inventory.data.InventoryContract;


public class MainActivity extends AppCompatActivity {


    ArticleAdapter mAdapter;
    ListView itemListView;
    Button shopItem;
    final static String[] projection = InventoryContract.Entries.PROJECTION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(view.getContext(), AddActivity.class);
                startActivity(add);
            }
        });


        Cursor cursor = getContentResolver().query(InventoryContract.CONTENT_URI, projection, null, null, null);
        itemListView = (ListView) findViewById(R.id.article_list);
        mAdapter = new ArticleAdapter(this, cursor);
        itemListView.setAdapter(mAdapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(view.getContext(), DetailActivity.class);
                detail.putExtra("itemId", id);
                startActivity(detail);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
