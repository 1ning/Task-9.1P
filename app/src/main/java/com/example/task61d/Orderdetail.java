package com.example.task61d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.task61d.Database.DatabaseHelper;

public class Orderdetail extends AppCompatActivity {
    TextView name,phone,location,data,description,type;
    Button Remove;
    String name1,phone1,location1,data1,description1,type1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oderdetail);
        findid();
        DatabaseHelper dbHelper = new DatabaseHelper(this, "db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //创建游标
        Cursor cursor = db.query("orders", null, "id=?", new String[]{Global.id}, null, null, null);
        cursor.moveToFirst();
        type1 = cursor.getString(cursor.getColumnIndexOrThrow("type"));
        name1 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        phone1 = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
        data1 = cursor.getString(cursor.getColumnIndexOrThrow("data"));
        location1 = cursor.getString(cursor.getColumnIndexOrThrow("location"));
        description1 = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        setvalue();
        cursor.close();
        db.close();
        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(Orderdetail.this, "db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("orders","id=?",new String[]{Global.id});
                Intent intent=new Intent(Orderdetail.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void findid(){
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        location=findViewById(R.id.location);
        data=findViewById(R.id.data);
        description=findViewById(R.id.description);
        type=findViewById(R.id.type);
        Remove=findViewById(R.id.Remove);
    }
    public void setvalue(){
        type.setText(type1);
        name.setText("name: "+name1);
        phone.setText("phone: "+phone1);
        description.setText("description: "+description1);
        data.setText("data: "+data1);
        location.setText(" location: "+location1);
    }
}


