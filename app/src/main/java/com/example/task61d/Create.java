package com.example.task61d;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task61d.Database.DatabaseHelper;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Create extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    EditText name;
    EditText phone;
    EditText data;
    EditText location;
    EditText description;
    Button Create,found,lost;
    Button Getloctaion;
    private static String provider;
    String name1,location1,phone1,data1,description1,type="Lost";
    String longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createorder2);
        findId();
        dbHelper = new DatabaseHelper(this, "db", null, 1);
        Places.initialize(getApplicationContext(),"AIzaSyDOoEC0olIAHCMYSBI2TPt5gGzx43LVB-s");
        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            type="Found";
            }
        });
        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Lost";
            }
        });
        location.setFocusable(false);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent2=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(Create.this);
                startActivityForResult(intent2,100);
            }
        });
        Getloctaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Create.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(Create.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Create.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }
                if (ActivityCompat.checkSelfPermission(Create.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Create.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Location location2 = getMyLocation();
                    longitude = String.valueOf(location2.getLongitude());
                    latitude = String.valueOf(location2.getLatitude());
                       List<Address> addresses;
                       Geocoder geocoder = new Geocoder(Create.this, Locale.getDefault());
                       try {
                           addresses = geocoder.getFromLocation(location2.getLatitude(), location2.getLongitude(), 1);
                           if (addresses != null && addresses.size() > 0) {
                               location1 = addresses.get(0).getFeatureName()+addresses.get(0).getSubAdminArea()+addresses.get(0).getLocality();
                               location.setText(location1);
                           }
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                }
            }
        });
        Create.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                name1= name.getText().toString();
                phone1=phone.getText().toString();
                data1=data.getText().toString();
                description1=description.getText().toString();
                if(name1!=null&&phone1!=null&&data1!=null&&location1!=null& description1!=null&location1!=null){
                    db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    putvalue(values);
                    long result = db.insert("orders", null, values);
                    db.close();
                    dbHelper.close();
                    if (result > 0) {
                        Toast.makeText(com.example.task61d.Create.this, "Order created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(com.example.task61d.Create.this, MainActivity.class);
                        startActivity(intent);
                        finish(); }
                }
                else{
                    Toast.makeText(com.example.task61d.Create.this, "Bad", Toast.LENGTH_SHORT).show();
                }
                 }
            });
    }
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
      {
          super.onActivityResult(requestCode,resultCode,data);
          if(requestCode==100&&resultCode==RESULT_OK) {
              Place place = Autocomplete.getPlaceFromIntent(data);
              location1 = place.getName();
              location.setText(location1);
              latitude= String.valueOf(place.getLatLng().latitude);
              longitude= String.valueOf(place.getLatLng().longitude);
              }

          else if(resultCode== AutocompleteActivity.RESULT_ERROR)
          {
              Status status=Autocomplete.getStatusFromIntent(data);
              Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_LONG).show();
          }
      }

      public void findId(){
          name=findViewById(R.id.name);
          phone=findViewById(R.id.phone);
          location=findViewById(R.id.location);
          description=findViewById(R.id.description);
          found=findViewById(R.id.btnfind);
          lost=findViewById(R.id.btnlost);
          Create=findViewById(R.id.Create);
          data=findViewById(R.id.data);
          Getloctaion=findViewById(R.id.Getlocation);
      }

    public Location getMyLocation() {
        LocationManager locationManager = (LocationManager) Create.this.getSystemService(Context.LOCATION_SERVICE);
        List<String> list = locationManager.getProviders(true);
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);
        return lastKnownLocation;
    }
    public void putvalue(ContentValues values){
        values.put("name",name1);
        values.put("phone",phone1);
        values.put("data",data1);
        values.put("location",location1);
        values.put("description",description1);
        values.put("type",type);
        values.put("latitude",latitude);
        values.put("longitude",longitude);
    }
}