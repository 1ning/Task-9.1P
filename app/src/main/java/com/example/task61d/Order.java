package com.example.task61d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.task61d.Database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    Button create;
    private SQLiteDatabase db;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);
        dbHelper = new DatabaseHelper(this, "db", null, 1);
        db = dbHelper.getWritableDatabase();
        mRecyclerView = findViewById(R.id.recycler_view);
        create=findViewById(R.id.Create);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<item> list = queryAllData();
        if (this != null) {
            MyorderAdapter myorderAdapter = new MyorderAdapter(this, list);
            mRecyclerView.setAdapter(myorderAdapter);
        }

    }


    public List<item> queryAllData(){
        Cursor cursor = db.query("orders",null,null,null,null,null,null);
        List<item> list = new ArrayList<>();
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String thing=type+" "+name;
                item item=new item();
                item.thing=thing;
                item.id=id;
                list.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return list;
    }

}
