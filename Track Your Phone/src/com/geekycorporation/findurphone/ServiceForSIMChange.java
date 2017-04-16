package com.geekycorporation.findurphone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class ServiceForSIMChange extends Service {

	private String storedSimSerial;
	private String currentSimSerial;
	private String mContact1;
	private String mContact2;
	private String mContact3;
	private String mName;
	private boolean mIs_Service_On;
	public static SharedPreferences mSharedPreferences;
	public static SharedPreferences mSharedPreferencesContact1;
	public static SharedPreferences mSharedPreferencesContact2;
	public static SharedPreferences mSharedPreferencesContact3;
	public static SharedPreferences mSharedPreferencesSendMsg;
	SharedPreferences m_SharedPreferences_name;

	@Override
	public void onCreate() {
		super.onCreate();
		mSharedPreferencesSendMsg = SharedPreferencesBuilder
				.getSharedPreferences(this);
		mSharedPreferences = SharedPreferencesBuilder
				.getSharedPreferences(this);
		storedSimSerial = mSharedPreferences.getString(Consts.sp_sim_number,
				"SimNumber");
		mSharedPreferencesContact1 = SharedPreferencesBuilder
				.getSharedPreferences(this);
		mContact1 = mSharedPreferences.getString(Consts.filename1, "Number1");
		mSharedPreferencesContact2 = SharedPreferencesBuilder
				.getSharedPreferences(this);
		mContact2 = mSharedPreferences.getString(Consts.filename2, "Number2");
		mSharedPreferencesContact3 = SharedPreferencesBuilder
				.getSharedPreferences(this);
		mContact3 = mSharedPreferences.getString(Consts.filename3, "Number3");
		mIs_Service_On = mSharedPreferencesSendMsg.getBoolean(
				Consts.sp_ENABLE_SERVICE, false);
		m_SharedPreferences_name = SharedPreferencesBuilder
				.getSharedPreferences(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		TelephonyManager tmMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		currentSimSerial = tmMgr.getSimSerialNumber();
		mName = m_SharedPreferences_name.getString(Consts.sp_name, "Person");
		// Log.e("Current Sim Serial::",currentSimSerial);

		if (currentSimSerial.equals(storedSimSerial)) {
			// Toast.makeText(this, "Sim not changed",
			// Toast.LENGTH_LONG).show();
			// Log.e("Sim Status","Sim no changed !!!";
		} else {
			String Lmsg = "Please inform "
					+ mName
					+ "  that his sim card has been changed\nNew sim serial number : "
					+ currentSimSerial + "\nReply 'Track' to locate the phone";
			// Toast.makeText(this, "Sim changed", Toast.LENGTH_LONG).show();
			SmsManager sm = SmsManager.getDefault();
			if (mIs_Service_On) {
				// Toast.makeText(this, "Msg sent", Toast.LENGTH_LONG).show();
				if (!mContact1.equals("+91")) {
					sm.sendTextMessage(mContact1, null, Lmsg, null, null);
					// Toast.makeText(this, "Msg sent to 1", Toast.LENGTH_LONG)
					// .show();
				}
				if (!mContact2.equals("+91")) {
					sm.sendTextMessage(mContact2, null, Lmsg, null, null);
					// Toast.makeText(this, "Msg sent to 2", Toast.LENGTH_LONG)
					// .show();
				}
				if (!mContact3.equals("+91")) {
					sm.sendTextMessage(mContact3, null, Lmsg, null, null);
					// Toast.makeText(this, "Msg sent to 3", Toast.LENGTH_LONG)
					// .show();
				}
			}
			// Toast.makeText(this, "Service Started",
			// Toast.LENGTH_LONG).show();
			// Log.e("Sim Status","Sim changed !!!";
		}
		ServiceForSIMChange.this.stopSelf();
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		// Toast.makeText(this, "Service Destroy", Toast.LENGTH_LONG).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
