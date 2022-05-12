package com.example.task61d;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.task61d.Database.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.task61d.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public List<LatLng>List2;
    private GoogleMap mMap;
private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding = ActivityMapsBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List2=getlist();
        for (int i = 0; i <List2.size(); i++) {
            LatLng item = List2.get(i);
            mMap.addMarker(new MarkerOptions().position(item).title("lost and found thing"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(item));
        }
    }
    public List<LatLng> getlist() {
        List<LatLng> list = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(this, "db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("orders", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Double Longitude = Double.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("longitude")));
                Double Latitude = Double.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("latitude")));
                LatLng item = new LatLng(Latitude,Longitude);
                list.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return list;
    }
}