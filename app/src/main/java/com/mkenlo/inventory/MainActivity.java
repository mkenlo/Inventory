package com.mkenlo.inventory;

import android.content.Intent;
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
import android.widget.TextView;

import com.mkenlo.inventory.data.ArticleModel;
import com.mkenlo.inventory.data.InventoryDbHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ArticleModel> itemList;
    ArticleRecyclerAdapter mAdapter;
    InventoryDbHelper mDbHelper;
    RecyclerView itemListView;


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

        //mDbHelper.onCreate();
        itemListView =(RecyclerView) findViewById(R.id.article_list);

        /*itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(view.getContext(), DetailArticleActivity.class);
                startActivity(detail);

            }
        });*/
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

    public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder> {

        public ArticleRecyclerAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
            return new ArticleRecyclerAdapter.ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(ArticleRecyclerAdapter.ViewHolder holder, int position) {
            ArticleModel item = itemList.get(position);
            holder.article_name.setText(item.getName());
            holder.article_quantity.setText(item.getQuantity());

        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView article_name;
            TextView article_quantity;
            ImageView article_image;

            public ViewHolder(View v) {
                super(v);

                article_name = (TextView) v.findViewById(R.id.article_name);
                article_quantity = (TextView) v.findViewById(R.id.article_quantity);
                article_image = (ImageView) v.findViewById(R.id.article_image);

            }
        }
    }
}
