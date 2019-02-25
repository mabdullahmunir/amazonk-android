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
    private LocationCallback locationCallback = null;


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
            locationRequest.setInterval(20000);

            final LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) return;
                    Log.d("Lokashyon", "sus loc, " + locationResult.getLastLocation().distanceTo(amazonkLocation));
                    if (locationResult.getLastLocation().distanceTo(amazonkLocation) > 50) {
                        IsShopping.stopShopping();
                        // mLocationProvider.removeLocationUpdates(locationCallback); listener tetap berjalan
                        stopSelf();
                    }
                };
            };

            mLocationProvider.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null /* Looper */);


        }

//          Entah kenapa gabisa pake fake gps
//        // Acquire a reference to the system Location Manager
//        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//        // Define a listener that responds to location updates
//        LocationListener locationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                Log.d("Lokashyon", "sus g, " + location.getLatitude() + ", " + location.getLongitude());
//                Log.d("Lokashyon", "sus g, Distance: " + location.distanceTo(amazonkLocation));
//                if (location.distanceTo(amazonkLocation) > 50) {
//                    IsShopping.stopShopping();
//                    Log.d("Lokashyon", "sus g, Distance: " + location.distanceTo(amazonkLocation) + " , stopping...");
//                    locationManager.removeUpdates(this);
//                    stopSelf();
//                } else Log.d("Lokahsyon", "sus g, u in?");
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//            public void onProviderEnabled(String provider) {}
//
//            public void onProviderDisabled(String provider) {}
//        };
//
//        // Register the listener with the Location Manager to receive location updates
//        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            Log.d("Lokashyon", "sus Granted");
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
