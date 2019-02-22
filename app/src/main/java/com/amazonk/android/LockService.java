package com.amazonk.android;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class LockService extends Service implements SensorEventListener {
    private static final String TAG = LockService.class.getSimpleName();

    private SensorManager sensorManager;
    private Sensor mGravitySensor;
    private Sensor mProximitySensor;

    private float prevGravityX, prevGravityY, prevGravityZ;

    public LockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "hehe");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mGravitySensor) {
            // TODO : Check setting, Wake up phone
        } else if (event.sensor == mProximitySensor) {
            if (event.values[0] < mProximitySensor.getMaximumRange()*0.1) {
//                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
//                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
//                wakeLock.acquire();
//
//                //and release again
//                wakeLock.release();
            }
            // TODO : Check setting, Turn of screen
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service Killed");
    }
}
