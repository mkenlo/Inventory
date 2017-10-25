package com.mkenlo.inventory;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mkenlo.inventory.data.ArticleModel;
import com.mkenlo.inventory.data.InventoryContract;
import com.mkenlo.inventory.data.InventoryDbHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ArticleModel> itemList;
    ArticleAdapter mAdapter;
    InventoryDbHelper mDbHelper;
    ListView itemListView;
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
                Intent intent = new Intent(view.getContext(), EditorActivity.class);
                startActivity(intent);
            }
        });


        Cursor cursor = getContentResolver().query(InventoryContract.CONTENT_URI, projection, null, null, null);


        itemListView =(ListView) findViewById(R.id.article_list);
        mAdapter = new ArticleAdapter(this, cursor);
        itemListView.setAdapter(mAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(view.getContext(), DetailActivity.class);
                startActivity(detail);

            }
        });

//        cursor.close();
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
