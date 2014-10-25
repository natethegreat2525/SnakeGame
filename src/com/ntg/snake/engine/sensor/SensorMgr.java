package com.ntg.snake.engine.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorMgr implements SensorEventListener {
	
	public static SensorMgr instance = null;
	public static Integer rand = 929321;
	public double x = 0,y = 0,z = 0;
	
	public static SensorMgr getInstance(Activity main) {
		Log.i("1","1");
		if(instance != null)
			return instance;
		Log.i("1","2");
		synchronized (rand) {
			Log.i("1","3");
			if(instance == null) {
				instance = new SensorMgr(main);
				Log.i("1","4");
			}
		}
		return instance;
	}
	
	public static SensorMgr getInstance() {
		return instance;
	}
	
	public SensorManager sensorMgr;
	public Sensor accelerometer;
	
	public SensorMgr(Activity main) {
		sensorMgr = (SensorManager) main.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMgr.registerListener(this, accelerometer , SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void onPause() {
		sensorMgr.unregisterListener(this);
	}
	
	public void onResume() {
		sensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor mySensor = event.sensor;
		 
	    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	        x = event.values[0];
	        y = event.values[1];
	        z = event.values[2];
	 
	        //Log.i("ACC", x + " " + y + " " + z);
	    }
	}

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
}
