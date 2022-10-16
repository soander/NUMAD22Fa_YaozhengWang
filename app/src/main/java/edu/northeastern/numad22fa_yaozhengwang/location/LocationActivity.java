package edu.northeastern.numad22fa_yaozhengwang.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.text.MessageFormat;

import edu.northeastern.numad22fa_yaozhengwang.R;

public class LocationActivity extends AppCompatActivity {

    private double latitude = 0.0;
    private double longitude = 0.0;
    private float total = 0;
    private boolean doCalculate = false;
    private TextView latitudeLocation;
    private TextView longitudeLocation;
    private TextView totalDistance;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latitudeLocation = findViewById(R.id.latitudeText);
        longitudeLocation = findViewById(R.id.longitudeText);
        totalDistance = findViewById(R.id.totalDistance);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Button resetLocation = findViewById(R.id.resetLocation);
        resetLocation.setOnClickListener(l -> {
            total = 0;
            totalDistance.setText(MessageFormat.format(
                    "Total Distance: {0} meters", total));
        });

        Button shareLocation = findViewById(R.id.shareLocation);
        shareLocation.setOnClickListener(l -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "My location latitude is "
                    + latitude + " and longitude is " + longitude + ".");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        getCurrLocation();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble("latitude", latitude);
        outState.putDouble("longitude", longitude);
        outState.putFloat("total", total);
        outState.putBoolean("doCalculate", doCalculate);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        latitude = savedInstanceState.getDouble("latitude");
        longitude = savedInstanceState.getDouble("longitude");
        total = savedInstanceState.getFloat("total", 0);
        doCalculate = savedInstanceState.getBoolean("doCalculate", false);
        latitudeLocation.setText(String.format("Latitude: %s", latitude));
        longitudeLocation.setText(String.format("Longitude: %s", longitude));
        totalDistance.setText(MessageFormat.format(
                "Total Distance: {0} meters", total));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.setPositiveButton("Yes", (dialog, which) -> finish());
        alertDialog.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalDistance.setText(MessageFormat.format(
                "Total Distance: {0} meters", total));
        getCurrLocation();
    }

    public void getCurrLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, "Please turn on your Gps!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LocationActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(LocationActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            } else {
                LocationRequest locationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(2000)
                        .setFastestInterval(1000);

                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        }
    }

    public LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            double la = lastLocation.getLatitude();
            double lo = lastLocation.getLongitude();
            latitudeLocation.setText(String.format("Latitude Location: %s", latitude));
            longitudeLocation.setText(String.format("Longitude Location: %s", longitude));
            if(doCalculate) {
                total += getDistance(latitude, longitude, la, lo);
                totalDistance.setText(String.format("Total Distance: %s meters", total));
            } else {
                doCalculate = true;
            }
            latitude = la;
            longitude = lo;
        }
    };

    public float getDistance(double la1, double lo1, double la2, double lo2) {
        Location locationA = new Location("point A");
        locationA.setLatitude(la1);
        locationA.setLongitude(lo1);
        Location locationB = new Location("point B");
        locationB.setLatitude(la2);
        locationB.setLongitude(lo2);
        return locationA.distanceTo(locationB);
    }

}