package com.example.eventring;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

public class GenerateApplication extends Application{
	public static BluetoothObject btObject;
	public boolean callRuner = false;
	@Override
	public void onCreate() {
		btObject = new BluetoothObject();
		super.onCreate();
	}
	
	
	public static void setDevice(BluetoothDevice device){
		btObject.mmDevice = device;
	}
	public static BluetoothObject getBT(){
		return btObject;
	}

}
