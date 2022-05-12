package com.example.task61d.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DatabaseHelper extends SQLiteOpenHelper {



    private static final String CREATE_orders = "create table orders ("
            + "id integer primary key autoincrement,"
            + "name text, "
            + "location text, "
            + "phone text, "
            + "type text, "
            + "data text,"
            + "longitude text,"
            + "latitude text, "
            + "description text)";


    private Context context;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_orders);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
