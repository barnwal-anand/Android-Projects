package com.geekycorporation.findurphone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class ServiceForGetLocation extends Service {
	private static final String TAG = "BOOMBOOMTESTGPS";
	private LocationManager mLocationManager = null;
	private static final int LOCATION_INTERVAL = 1000;
	private static final float LOCATION_DISTANCE = 10f;
	AppLocationService appLocationService;

	private class LocationListener implements android.location.LocationListener {
		Location mLastLocation;

		public LocationListener(String provider) {
			Log.e(TAG, "LocationListener " + provider);
			mLastLocation = new Location(provider);
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.e(TAG, "onLocationChanged: " + location);
			mLastLocation.set(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			Log.e(TAG, "onProviderDisabled: " + provider);
		}

		@Override
		public void onProviderEnabled(String provider) {
			Log.e(TAG, "onProviderEnabled: " + provider);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.e(TAG, "onStatusChanged: " + provider);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Log.e(TAG, "onStartCommand");
		super.onStartCommand(intent, flags, startId);
		String mContact = intent.getStringExtra("msg_from");
		String mTemplate = intent.getStringExtra("template");
		// Toast.makeText(getBaseContext(), mContact, Toast.LENGTH_LONG).show();
		appLocationService = new AppLocationService(ServiceForGetLocation.this);
		Location gpsLocation = appLocationService
				.getLocation(LocationManager.GPS_PROVIDER);

		if (gpsLocation != null) {
			double latitude = gpsLocation.getLatitude();
			double longitude = gpsLocation.getLongitude();
			String sLatitude = String.valueOf(latitude);
			String sLongitude = String.valueOf(longitude);
			String msg = mTemplate +"\n"
					+ "http://maps.google.de/maps?q=loc:" + sLatitude + ","
					+ sLongitude + "\nSent via TRACK YOUR PHONE";
			SmsManager sm = SmsManager.getDefault();
			sm.sendTextMessage(mContact, null, msg, null, null);
			// Toast.makeText(getBaseContext(), "Location Sent Using GPS",
			// Toast.LENGTH_LONG).show();
		} else {
			Location nwLocation = appLocationService
					.getLocation(LocationManager.NETWORK_PROVIDER);

			if (nwLocation != null) {
				double latitude = nwLocation.getLatitude();
				double longitude = nwLocation.getLongitude();
				String sLatitude = String.valueOf(latitude);
				String sLongitude = String.valueOf(longitude);
				String msg = "Your Phone is currently at this location\n"
						+ "http://maps.google.de/maps?q=loc:" + sLatitude + ","
						+ sLongitude + "\nSent via TRACK YOUR PHONE";
				SmsManager sm = SmsManager.getDefault();
				sm.sendTextMessage(mContact, null, msg, null, null);
				// Toast.makeText(getBaseContext(),
				// "Location Sent Using Network Provider",
				// Toast.LENGTH_LONG).show();

			}
		}
		ServiceForGetLocation.this.stopSelf();
		return START_NOT_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "onCreate");
	}

	public void onDestroy() {
		Log.e(TAG, "onDestroy");
		super.onDestroy();
	}

}