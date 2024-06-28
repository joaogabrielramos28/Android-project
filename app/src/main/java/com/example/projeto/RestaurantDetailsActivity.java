package com.example.projeto;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RestaurantDetailsActivity extends AppCompatActivity implements  OnMapReadyCallback {

    private TextView name,description,rating,date,hour;
    private ImageView image;
    private RatingBar ratingBar;
    private MapView map;
    private FloatingActionButton fab;
    private Restaurant restaurant;
    private Dialog dialog;
    private Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurant_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        restaurant = getIntent().getParcelableExtra("restaurant");
        fab = findViewById(R.id.fab);
        fab.setColorFilter(R.color.white);
        date = findViewById(R.id.Data);
        hour = findViewById(R.id.Hour);
        calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                hour.setText(format.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date.setText(format.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });







        name = findViewById(R.id.restaruantDetailsName);
        ratingBar = findViewById(R.id.ratingBar2);
        description = findViewById(R.id.arestaruantDetailsDesc);
        image = findViewById(R.id.arestaruantDetailsImage);
        rating = findViewById(R.id.restaruantDetailsRating);
        Picasso.get().load(restaurant.getPhoto()).into(image);
        name.setText(restaurant.getName());
        description.setText(restaurant.getDescriptionDetails());
        rating.setText(restaurant.getRate().toString());
        ratingBar.setRating(restaurant.getRate().floatValue());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(savedInstanceState);
            }
        });

    }

    private void onGoBack(View view){
        this.finish();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if(map != null){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                LatLng location = new LatLng(restaurant.getLat(),restaurant.getLongi());
                float zoomLevel = 15.0f;
                googleMap.addMarker(new MarkerOptions().position(location)).setTitle(restaurant.getName());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
            }
        });
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) {
            map.onResume();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (map != null) {
            map.onStart();
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (map != null) {
            map.onStop();
        }

    }
    @Override
    protected void onPause() {
        map.onPause();
        if (map != null) {
            super.onPause();
        }

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (map != null) {
            map.onDestroy();
        }

    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
            map.onDestroy();

    }
    public void showDialog(Bundle savedInstanceState) {
        dialog = new Dialog(RestaurantDetailsActivity.this);
        dialog.setContentView(R.layout.map_layout);
        dialog.setOnShowListener(dialogInterface -> {
            map = dialog.findViewById(R.id.mapView);
            if (map != null) {
                map.onCreate(savedInstanceState);
                map.getMapAsync(this::onMapReady);
                map.onResume();
            } else {
                Log.e("MapView", "MapView is null in the dialog layout");
            }
        });
        dialog.show();
    }
}