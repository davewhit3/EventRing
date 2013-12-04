package com.example.eventring;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BluetoothObject {
	
	public BluetoothSocket mmSocket;
	public BluetoothDevice mmDevice;
	public OutputStream mmOutputStream;
	public InputStream mmInputStream;
	public Thread workerThread;
	public byte[] readBuffer;
	public int readBufferPosition, counter;
	public double przeliczenieWartosci;
	public String msg = "";
	public volatile boolean stopWorker;
	public boolean wlaczoneBluetooth = false;

	public void openBT() throws IOException {
		
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
																				// //SerialPortService
																				// ID
		mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
		mmSocket.connect();
		mmOutputStream = mmSocket.getOutputStream();
		mmInputStream = mmSocket.getInputStream();
		wlaczoneBluetooth = true;
		
		showMessage("Po³aczono z EventRing");
	}
/*
	public void beginListenForData() {
		final Handler handler = new Handler();
		final byte delimiter = 10; // This is the ASCII code for a newline
									// character

		stopWorker = false;
		readBufferPosition = 0;
		readBuffer = new byte[1024];
		workerThread = new Thread(new Runnable() {
			public void run() {
				while (!Thread.currentThread().isInterrupted() && !stopWorker) {
					try {
						int bytesAvailable = mmInputStream.available();
						if (bytesAvailable > 0) {
							byte[] packetBytes = new byte[bytesAvailable];
							mmInputStream.read(packetBytes);
							for (int i = 0; i < bytesAvailable; i++) {
								byte b = packetBytes[i];
								if (b == delimiter) {
									byte[] encodedBytes = new byte[readBufferPosition];
									System.arraycopy(readBuffer, 0,
											encodedBytes, 0,
											encodedBytes.length);
									final String data = new String(
											encodedBytes, "US-ASCII");
									readBufferPosition = 0;

									handler.post(new Runnable() {
										public void run() {
											System.out.println(data);
										}
									});
								} else {
									readBuffer[readBufferPosition++] = b;
								}
							}
						}
					} catch (IOException ex) {
						stopWorker = true;
					}
				}
			}
		});

		workerThread.start();
	}
*/
	public void sendData(String message) throws IOException {
		if (wlaczoneBluetooth == true) {
			char[] msgChar = message.toCharArray();
			for (int i = 0; i <= message.length() - 1; i++) {
				mmOutputStream.write(msgChar[i]);
			}
			showMessage("Dane zosta³y wys³ane:"+message);
		}

	}

	public void closeBT() throws IOException {
		if (wlaczoneBluetooth == true) {
			stopWorker = true;
			mmOutputStream.close();
			mmInputStream.close();
			mmSocket.close();
			showMessage("Roz³¹czono");
		}
	}

	public void showMessage(String log) {
		System.out.println("EventRingLOG: "+log);
	}
}
