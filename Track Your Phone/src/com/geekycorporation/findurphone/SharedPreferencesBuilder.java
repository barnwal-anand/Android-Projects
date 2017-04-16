package com.geekycorporation.findurphone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

//Only one instance of SharedPreferences will persist throughout the app
public class SharedPreferencesBuilder {
	private static SharedPreferences sharedPreferences;
	
	public static SharedPreferences getSharedPreferences(Context context) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(Consts.SharedPreferncesName, Activity.MODE_PRIVATE);
		}
		return sharedPreferences;
	}
}