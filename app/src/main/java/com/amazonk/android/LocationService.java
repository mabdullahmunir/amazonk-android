package com.amazonk.android;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.amazonk.android.model.IsShopping;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationService extends Service {
    private static double TOKO_LONGITUDE = 107.609775;
    private static double TOKO_LATITUDE = -6.890569;
    private static Location amazonkLocation;
    private FusedLocationProviderClient mLocationProvider;
    private LocationCallback locationCallback;


    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        amazonkLocation = new Location("");
        amazonkLocation.setLatitude(TOKO_LATITUDE);
        amazonkLocation.setLongitude(TOKO_LONGITUDE);
        Log.d("Lokashyon", "sus Started");

        mLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Lokashyon", "sus Granted");

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(30000);

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) return;
                    Log.d("Lokashyon", "sus loc, " + locationResult.getLastLocation().distanceTo(amazonkLocation));
                    if (locationResult.getLastLocation().distanceTo(amazonkLocation) > 50) {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                            }
//                        }).start();
                        IsShopping.stopShopping();
                        Log.d("Lokashyon", "sus Stopped");
                        stopSelf(); // Race sama stopShopping
                    }
                };
            };

            mLocationProvider.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null /* Looper */);
        }
    }

    @Override
    public void onDestroy() {
        mLocationProvider.removeLocationUpdates(locationCallback);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
