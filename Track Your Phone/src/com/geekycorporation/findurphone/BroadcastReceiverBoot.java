package com.geekycorporation.findurphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastReceiverBoot extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//TODO Auto-generated method stub
		//Toast.makeText(context, "BOOT SERVICE STARTED",Toast.LENGTH_LONG).show();

		Intent in=new Intent(context,ServiceForSIMChange.class);
		context.startService(in);

	}
}
