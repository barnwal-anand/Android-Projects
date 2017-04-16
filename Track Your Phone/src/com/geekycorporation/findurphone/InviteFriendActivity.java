package com.geekycorporation.findurphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InviteFriendActivity extends Activity {

	private ImageView m_iv_contactforinvitation;
	private EditText m_et_contactforinvitation;
	private Button m_sendinvitation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invite_friends);

		m_et_contactforinvitation = (EditText) findViewById(R.id.et_contactforinvitation);
		m_iv_contactforinvitation = (ImageView) findViewById(R.id.iv_contactforinvitation);
		m_iv_contactforinvitation.setOnClickListener(new OnClickHandler());
		m_sendinvitation = (Button) findViewById(R.id.button_sendinvitation);
		m_sendinvitation.setOnClickListener(new OnClickHandler());

		m_et_contactforinvitation.setText("+91");
		Selection.setSelection(m_et_contactforinvitation.getText(),
				m_et_contactforinvitation.getText().length());
		m_et_contactforinvitation.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!s.toString().contains("+91")) {
					m_et_contactforinvitation.setText("+91");
					Selection.setSelection(m_et_contactforinvitation.getText(),
							m_et_contactforinvitation.getText().length());
				}
			}
		});

	}

	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (1):
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				Cursor c = managedQuery(contactData, null, null, null, null);
				if (c.moveToFirst()) {
					String id = c
							.getString(c
									.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
					String hasPhone = c
							.getString(c
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					if (hasPhone.equalsIgnoreCase("1")) {
						Cursor phones = getContentResolver()
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = " + id, null, null);
						phones.moveToFirst();
						String text = phones.getString(phones
								.getColumnIndex("data1"));
						String number = text.substring(text.length() - 10);
						String phn_no = "+91" + number;
						m_et_contactforinvitation.setText(phn_no);

					}
				}
			}
			break;
		}
	}

	public class OnClickHandler implements OnClickListener {
		public void onClick(View v) {

			if (v == m_iv_contactforinvitation) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, 1);
				onActivityResult(1, 1, intent);
			}

			if(v == m_sendinvitation){
				final String m_phone_number = m_et_contactforinvitation.getText().toString().trim();
				if(m_phone_number.equals("+91")){
					final Toast toast = Toast.makeText(InviteFriendActivity.this,
							"Please enter a number", Toast.LENGTH_SHORT);
					toast.show();
					Handler handler1 = new Handler();
					handler1.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast.cancel();
						}
					}, 1000);
					return;
				}
				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						InviteFriendActivity.this);
				builder1.setTitle("Are you sure ?");
				builder1.setMessage("Invitation will be send to the provided number");
				builder1.setCancelable(true);
				builder1.setPositiveButton("No",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						dialog.cancel();
					}
				});
				builder1.setNegativeButton("Yes",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Toast.makeText(InviteFriendActivity.this,
								"Invitation Sent", Toast.LENGTH_SHORT)
								.show();
						String msg = "Hey there, I just installed 'Track Your Phone'.\nIts awesome, click here to download\n"
								+"http://slideme.org/application/track-your-phone" + "\nsent via TRACK YOUR PHONE" ;
									SmsManager sm = SmsManager.getDefault();
						sm.sendTextMessage(m_phone_number, null, msg, null, null);
						dialog.cancel();

					}
				});

				AlertDialog alert11 = builder1.create();
				alert11.show();

			}
		}
	}
}
