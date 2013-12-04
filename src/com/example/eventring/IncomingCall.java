package com.example.eventring;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class IncomingCall extends BroadcastReceiver {
	public Context pContext;

	@Override
	public void onReceive(final Context context, Intent intent) {
		pContext = context.getApplicationContext();
			
			TelephonyManager tmr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			PhoneStateListener PhoneListener = new PhoneStateListener() {
				public void onCallStateChanged(int state, String incomingNumber) {
					if (state == TelephonyManager.CALL_STATE_RINGING) {
						/*Toast.makeText(pContext, "Przekazano do kontrolera",
									Toast.LENGTH_SHORT).show();
						*/
						System.out.println("Odbierano po³¹czenie i przekazano do kontrolera.");
						
					    BluetoothObject bt = GenerateApplication.getBT();
						try {
							bt.sendData("1");
							System.out.println("Odbierano po³¹czenie i przekazano do kontrolera.");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			};

			tmr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
			context.unregisterReceiver(this);
	}

}
