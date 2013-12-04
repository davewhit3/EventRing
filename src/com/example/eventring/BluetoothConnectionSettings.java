package com.example.eventring;

import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BluetoothConnectionSettings extends Activity {
	private static final int REQUEST_ENABLE_BT = 1;
	public BluetoothDevice device;
	public boolean connectionState;
	OnItemClickListener itemClickListener;
	ListView listDevicesFound;
	Button btnScanDevice, call, sms, cancel;
	TextView stateBluetooth;
	BluetoothAdapter bluetoothAdapter;
	ArrayAdapter<String> btArrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnScanDevice = (Button) findViewById(R.id.scandevice);

		stateBluetooth = (TextView) findViewById(R.id.bluetoothstate);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		listDevicesFound = (ListView) findViewById(R.id.devicesfound);
		btArrayAdapter = new ArrayAdapter<String>(BluetoothConnectionSettings.this,
				android.R.layout.simple_list_item_1);
		listDevicesFound.setAdapter(btArrayAdapter);

		CheckBlueToothState();

		btnScanDevice.setOnClickListener(btnScanDeviceOnClickListener);

		registerReceiver(ActionFoundReceiver, new IntentFilter(
				BluetoothDevice.ACTION_FOUND));
		listDevicesFound.setOnItemClickListener(new OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//System.out.println(device.getUuids());
				btnScanDevice.setEnabled(true);
				unregisterReceiver(ActionFoundReceiver);
				connectBt();
				//finish();
			}
		});
		
		Button call = (Button)findViewById(R.id.call);
		Button sms = (Button)findViewById(R.id.sms);
		Button cancel = (Button)findViewById(R.id.brea);
		
		call.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				BluetoothObject bt = GenerateApplication.getBT();
				try {
					bt.sendData("1");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
sms.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v1) {
				BluetoothObject bt = GenerateApplication.getBT();
				try {
					bt.sendData("2");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
cancel.setOnClickListener(new OnClickListener() {
	
	public void onClick(View v2) {
		BluetoothObject bt = GenerateApplication.getBT();
		try {
			bt.sendData("0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
});
	}
	
	private void CheckBlueToothState() {
		if (bluetoothAdapter != null) {
			if (bluetoothAdapter.isEnabled()) {
				stateBluetooth.setText("Bluetooth jest w³¹czone.");
				if (bluetoothAdapter.isDiscovering()) {
					btnScanDevice.setEnabled(false);
				} else {
					btnScanDevice.setEnabled(true);
				}
			} else {
				stateBluetooth.setText("Bluetooth jest wy³¹czone!");
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}
	}

	private Button.OnClickListener btnScanDeviceOnClickListener = new Button.OnClickListener() {

		public void onClick(View arg0) {
			btnScanDevice.setEnabled(false);
			btArrayAdapter.clear();
			bluetoothAdapter.startDiscovery();
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			CheckBlueToothState();
		}
	}

	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				btArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress());
				btArrayAdapter.notifyDataSetChanged();
			}			
		}
	};
	
	public void connectBt(){
		BluetoothObject bt = GenerateApplication.getBT();
		GenerateApplication.setDevice(device);
		try {
			bt.openBT();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
