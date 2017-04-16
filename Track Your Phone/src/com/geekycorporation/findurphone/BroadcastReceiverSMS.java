package com.geekycorporation.findurphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class BroadcastReceiverSMS extends BroadcastReceiver {

	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	private static final String TAG = "SMSBroadcastReceiver";
	private double lat;
	private String lat_s;
	public static SharedPreferences mSharedPreferences;
	public void onReceive(Context context, Intent intent) {
		mSharedPreferences = SharedPreferencesBuilder
				.getSharedPreferences(context);
		Log.i(TAG, "Intent recieved: " + intent.getAction());

		if (intent.getAction().equals(SMS_RECEIVED)) {
			Bundle bundle = intent.getExtras(); // ---get the SMS message passed
												// in---
			SmsMessage[] msgs = null;
			String msg_from;
			int count = 1;

			if (bundle != null) {
				// ---retrieve the SMS message received---
				try {
					Object[] pdus = (Object[]) bundle.get("pdus");
					msgs = new SmsMessage[pdus.length];
					for (int i = 0; i < msgs.length; i++) {
						String storeNumber1 = mSharedPreferences.getString(
								Consts.filename1, "Number1");
						String storeNumber2 = mSharedPreferences.getString(
								Consts.filename2, "Number2");
						String storeNumber3 = mSharedPreferences.getString(
								Consts.filename3, "Number3");
						// Toast.makeText(context, storeNumber1,
						// Toast.LENGTH_LONG).show();
						msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						msg_from = msgs[i].getOriginatingAddress();
						// Toast.makeText(context, msg_from,
						// Toast.LENGTH_LONG).show();
						String msg_body = msgs[i].getMessageBody();
						msg_body = msg_body.trim();
						// Location gpsLocation =
						// appLocationService.getLocation(LocationManager.GPS_PROVIDER);
						boolean isChecked = mSharedPreferences.getBoolean(
								Consts.sp_ENABLE_SERVICE, false);
						if ((isChecked)
								&& (storeNumber1.equals(msg_from)
										|| storeNumber2.equals(msg_from) || storeNumber3
											.equals(msg_from))
								&& (msg_body.equalsIgnoreCase("TRACK"))) {
							Intent i1 = new Intent(context,
									ServiceForGetLocation.class);
							i1.putExtra("msg_from", msg_from);
							i1.putExtra("template", "Your phone is currently at this location");
							context.startService(i1);

						}
						// else
						// Toast.makeText(context,"Not Matched",
						// Toast.LENGTH_LONG).show();

					}
				} catch (Exception e) {
					Log.d("Exception caught", e.getMessage());
				}

			}

		}

	}
}
