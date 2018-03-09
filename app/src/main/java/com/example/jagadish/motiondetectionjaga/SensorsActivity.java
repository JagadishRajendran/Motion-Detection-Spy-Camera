package com.example.jagadish.motiondetectionjaga;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jagadish on 3/17/2017.
 */

public class SensorsActivity extends Activity implements SensorEventListener {

    private static final String TAG = "SensorsActivity";
    private static final AtomicBoolean computing = new AtomicBoolean(false);

    private static final float grav[] = new float[3]; // Gravity (// accelerometer data)
    private static final float mag[] = new float[3]; // Magnetic field

    //Threshold to check whether the mobile is in motion or not
    private static final float gravThreshold = 0.5f;
    private static final float magThreshold = 1.0f;

    private static SensorManager sensorMgr = null;
    private static List<Sensor> sensors = null;
    private static Sensor sensorGravity = null;
    private static Sensor sensorMagnetic = null;

    private static float prevGrav = 0.0f;
    private static float prevMag = 0.0f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onStart() {
        super.onStart();

        try {
            sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

            sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
            if (sensors.size() > 0) sensorGravity = sensors.get(0);

            sensors = sensorMgr.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
            if (sensors.size() > 0) sensorMagnetic = sensors.get(0);

            sensorMgr.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
            sensorMgr.registerListener(this, sensorMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception ex1) {
            try {
                if (sensorMgr != null) {
                    sensorMgr.unregisterListener(this, sensorGravity);
                    sensorMgr.unregisterListener(this, sensorMagnetic);
                    sensorMgr = null;
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        try {
            try {
                sensorMgr.unregisterListener(this, sensorGravity);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                sensorMgr.unregisterListener(this, sensorMagnetic);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            sensorMgr = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent evt) {
        if (!computing.compareAndSet(false, true)) return;

        if (evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            grav[0] = evt.values[0];
            grav[1] = evt.values[1];
            grav[2] = evt.values[2];
        } else if (evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mag[0] = evt.values[0];
            mag[1] = evt.values[1];
            mag[2] = evt.values[2];
        }

        float gravity = grav[0] + grav[1] + grav[2];
        float magnetic = mag[0] + mag[1] + mag[2];

        float gravDiff = Math.abs(gravity - prevGrav);
        float magDiff = Math.abs(magnetic - prevMag);
        // Log.i(TAG, "gravDiff="+gravDiff+" magDiff="+magDiff);

        if ((Float.compare(prevGrav, 0.0f) != 0 && Float.compare(prevMag, 0.0f) != 0) && (gravDiff > gravThreshold || magDiff > magThreshold)) {
            GlobalData.setPhoneInMotion(true);
        } else {
            GlobalData.setPhoneInMotion(false);
        }

        prevGrav = gravity;
        prevMag = magnetic;

        computing.set(false);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor == null) throw new NullPointerException();

        if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD && accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            Log.e(TAG, "Compass data unreliable");
        }
    }
}

