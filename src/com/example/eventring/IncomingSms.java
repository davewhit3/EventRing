package com.example.eventring;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle extras = intent.getExtras();
		if (extras == null) {
			return;
		}
		
		// Object[] pdus = (Object[]) extras.get("pdus");
		/*Toast.makeText(context.getApplicationContext(),
				"Przekazano do kontrolera", Toast.LENGTH_SHORT).show();
		*/
		BluetoothObject bt = GenerateApplication.getBT();
		try {
			bt.sendData("2");
			System.out.println("Odebrano wiadomoœæ i przes³ano do kontrolera.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
