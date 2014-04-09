package com.rc.devkit.shakemenu;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener implements SensorEventListener
{
    private OnShakeOccuredListener onShakeOccuredListener;
    private float mAccelLast = SensorManager.GRAVITY_EARTH;
    private float mAccelCurrent = SensorManager.GRAVITY_EARTH;
    private float mAccel = 0.00f;
    private Context currentContext;

    public static ShakeListener newInstance()
    {
        ShakeListener instance = new ShakeListener();
        return instance;
    }

    public void register(Context context)
    {
        SensorManager sensorMgr = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorMgr.registerListener(this,
                sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        currentContext = context;
    }

    public void unregister(Context context)
    {
        SensorManager sensorMgr = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorMgr.unregisterListener(this);
        currentContext = null;
    }

    @Override
    public void onSensorChanged(SensorEvent se)
    {
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        if (mAccel > 8)
        {
            shakedIt();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    public void setOnShakeOccuredListener(OnShakeOccuredListener onShakeOccuredListener)
    {
        this.onShakeOccuredListener = onShakeOccuredListener;
    }

    private void shakedIt()
    {
        if (onShakeOccuredListener != null)
        {
            onShakeOccuredListener.onShakeOccured();
        }
        else
        {
            if (currentContext != null)
            {
                ShakedMenu.newInstance(currentContext).show();
            }
        }
    }

    public static interface OnShakeOccuredListener
    {
        public void onShakeOccured();
    }
}
