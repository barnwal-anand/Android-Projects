package com.geekycorporation.findurphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static SharedPreferences mSharedPreferences;
	public SharedPreferences.Editor mSharedPreferencesEditor;

	private Switch m_switch;
	private Button mReset;
	private Button mSave;
	private Button mChange_pass;
	private Button mChange_num;

	private ImageView m_iv_iamhere;
	private ImageView m_iv_contact1;
	private ImageView m_iv_contact2;
	private ImageView m_iv_contact3;
	private EditText mEditText1;
	private EditText mEditText2;
	private EditText mEditText3;

	private TextView mTextView_dis;
	private Intent m_Intent1;
	private Intent m_Intent2;

	private View m_Viewline1;
	private View m_Viewline2;
	private View m_Viewline3;
	private View m_Viewline4;
	AppLocationService appLocationService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_WithActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		m_Intent1 = new Intent(MainActivity.this, ChangeNumberActivity.class);
		m_Intent2 = new Intent(MainActivity.this, ChangePasswordActivity.class);
		mSharedPreferences = SharedPreferencesBuilder
				.getSharedPreferences(this);
		mSharedPreferencesEditor = mSharedPreferences.edit();

		mEditText1 = (EditText) findViewById(R.id.editText1);
		mEditText2 = (EditText) findViewById(R.id.editText2);
		mEditText3 = (EditText) findViewById(R.id.editText3);
		mTextView_dis = (TextView) findViewById(R.id.textView_display);

		m_Viewline1 = (View) findViewById(R.id.view_line1);
		m_Viewline2 = (View) findViewById(R.id.view_line2);
		m_Viewline3 = (View) findViewById(R.id.view_line3);
		m_Viewline4 = (View) findViewById(R.id.view_line4);
		m_switch = (Switch) findViewById(R.id.switch_on_off);

		m_iv_iamhere = (ImageView) findViewById(R.id.iv_iamhere);
		m_iv_iamhere.setOnClickListener(new OnClickHandler());
		m_iv_contact1 = (ImageView) findViewById(R.id.iv_contact1);
		m_iv_contact1.setOnClickListener(new OnClickHandler());
		m_iv_contact2 = (ImageView) findViewById(R.id.iv_contact2);
		m_iv_contact2.setOnClickListener(new OnClickHandler());
		m_iv_contact3 = (ImageView) findViewById(R.id.iv_contact3);
		m_iv_contact3.setOnClickListener(new OnClickHandler());

		mSave = (Button) findViewById(R.id.button_save);
		mSave.setOnClickListener(new OnClickHandler());
		mReset = (Button) findViewById(R.id.button_reset);
		mReset.setOnClickListener(new OnClickHandler());
		mChange_pass = (Button) findViewById(R.id.button_change_pass);
		mChange_pass.setOnClickListener(new OnClickHandler());
		mChange_num = (Button) findViewById(R.id.button_change_alt_num);
		mChange_num.setOnClickListener(new OnClickHandler());
		mSharedPreferences = SharedPreferencesBuilder
				.getSharedPreferences(this);
		mSharedPreferencesEditor = mSharedPreferences.edit();
		TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String getSimSerialNumber = telemamanger.getSimSerialNumber();
		mSharedPreferencesEditor.putString(Consts.sp_sim_number,
				getSimSerialNumber).commit();
		// Toast.makeText(State_on.this,
		// getSimSerialNumber,Toast.LENGTH_SHORT).show();

		boolean isChecked = mSharedPreferences.getBoolean(
				Consts.sp_ENABLE_SERVICE, false);
		m_switch.setChecked(isChecked);

		if (isChecked) {
			appLocationService = new AppLocationService(MainActivity.this);
			Location gpsLocation = appLocationService
					.getLocation(LocationManager.GPS_PROVIDER);

			Location nwLocation = appLocationService
					.getLocation(LocationManager.NETWORK_PROVIDER);
			if (gpsLocation == null)
				showSettingsAlert("GPS");
			if (nwLocation == null)
				showSettingsAlert("NETWORK");
			m_Viewline1.setVisibility(View.VISIBLE);
			m_Viewline2.setVisibility(View.VISIBLE);
			m_Viewline3.setVisibility(View.VISIBLE);
			m_Viewline4.setVisibility(View.VISIBLE);
			mEditText1.setVisibility(View.VISIBLE);
			mEditText2.setVisibility(View.VISIBLE);
			mEditText3.setVisibility(View.VISIBLE);
			m_iv_contact1.setVisibility(View.VISIBLE);
			m_iv_contact2.setVisibility(View.VISIBLE);
			m_iv_contact3.setVisibility(View.VISIBLE);
			mChange_pass.setVisibility(View.VISIBLE);
			mSave.setVisibility(View.VISIBLE);
			mReset.setVisibility(View.VISIBLE);
			mChange_num.setVisibility(View.VISIBLE);
			mTextView_dis.setVisibility(View.GONE);
			String storeNumber1 = mSharedPreferences.getString(
					Consts.filename1, "Number1");
			mEditText1.setText(storeNumber1);

			String storeNumber2 = mSharedPreferences.getString(
					Consts.filename2, "Number2");
			mEditText2.setText(storeNumber2);

			String storeNumber3 = mSharedPreferences.getString(
					Consts.filename3, "Number3");
			mEditText3.setText(storeNumber3);

		} else {
			m_Viewline1.setVisibility(View.GONE);
			m_Viewline2.setVisibility(View.GONE);
			m_Viewline3.setVisibility(View.GONE);
			m_Viewline4.setVisibility(View.GONE);
			mEditText1.setVisibility(View.GONE);
			mEditText2.setVisibility(View.GONE);
			mEditText3.setVisibility(View.GONE);
			m_iv_contact1.setVisibility(View.GONE);
			m_iv_contact2.setVisibility(View.GONE);
			m_iv_contact3.setVisibility(View.GONE);
			mChange_pass.setVisibility(View.GONE);
			mSave.setVisibility(View.GONE);
			mReset.setVisibility(View.GONE);
			mChange_num.setVisibility(View.GONE);
			mTextView_dis.setVisibility(View.VISIBLE);
		}

		// attach a listener to check for changes in state
		m_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton View, boolean isChecked) {

				if (isChecked) {
					appLocationService = new AppLocationService(
							MainActivity.this);
					Location gpsLocation = appLocationService
							.getLocation(LocationManager.GPS_PROVIDER);

					Location nwLocation = appLocationService
							.getLocation(LocationManager.NETWORK_PROVIDER);
					if (gpsLocation == null)
						showSettingsAlert("GPS");
					if (nwLocation == null)
						showSettingsAlert("NETWORK");

					final Toast toast3 = Toast.makeText(getBaseContext(),
							"Track Your Phone Has Been Activated",
							Toast.LENGTH_SHORT);
					toast3.show();
					Handler handler4 = new Handler();
					handler4.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast3.cancel();
						}
					}, 1000);

					m_Viewline1.setVisibility(View.VISIBLE);
					m_Viewline2.setVisibility(View.VISIBLE);
					m_Viewline3.setVisibility(View.VISIBLE);
					m_Viewline4.setVisibility(View.VISIBLE);
					mEditText1.setVisibility(View.VISIBLE);
					mEditText2.setVisibility(View.VISIBLE);
					mEditText3.setVisibility(View.VISIBLE);
					m_iv_contact1.setVisibility(View.VISIBLE);
					m_iv_contact2.setVisibility(View.VISIBLE);
					m_iv_contact3.setVisibility(View.VISIBLE);
					mChange_pass.setVisibility(View.VISIBLE);
					mSave.setVisibility(View.VISIBLE);
					mReset.setVisibility(View.VISIBLE);
					mChange_num.setVisibility(View.VISIBLE);
					mTextView_dis.setVisibility(View.GONE);
					String storeNumber1 = mSharedPreferences.getString(
							Consts.filename1, "Number1");
					mEditText1.setText(storeNumber1);

					String storeNumber2 = mSharedPreferences.getString(
							Consts.filename2, "Number2");
					mEditText2.setText(storeNumber2);

					String storeNumber3 = mSharedPreferences.getString(
							Consts.filename3, "Number3");
					mEditText3.setText(storeNumber3);

					// Toast.makeText(getBaseContext(), "App Service Is ON",
					// Toast.LENGTH_SHORT).show();
					mSharedPreferencesEditor.putBoolean(
							Consts.sp_ENABLE_SERVICE, isChecked).commit();
					return;
				} else {
					final Toast toast4 = Toast.makeText(getBaseContext(),
							"Track Your Phone Has Been Deactivated",
							Toast.LENGTH_SHORT);
					toast4.show();
					Handler handler4 = new Handler();
					handler4.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast4.cancel();
						}
					}, 1000);
					m_Viewline1.setVisibility(View.GONE);
					m_Viewline2.setVisibility(View.GONE);
					m_Viewline3.setVisibility(View.GONE);
					m_Viewline4.setVisibility(View.GONE);
					mEditText1.setVisibility(View.GONE);
					mEditText2.setVisibility(View.GONE);
					mEditText3.setVisibility(View.GONE);
					m_iv_contact1.setVisibility(View.GONE);
					m_iv_contact2.setVisibility(View.GONE);
					m_iv_contact3.setVisibility(View.GONE);
					mChange_pass.setVisibility(View.GONE);
					mSave.setVisibility(View.GONE);
					mReset.setVisibility(View.GONE);
					mChange_num.setVisibility(View.GONE);
					mTextView_dis.setVisibility(View.VISIBLE);

					mSharedPreferencesEditor.putBoolean(
							Consts.sp_ENABLE_SERVICE, isChecked).commit();
					return;
				}

			}
		});

		mEditText1.setText("+91");
		Selection.setSelection(mEditText1.getText(), mEditText1.getText()
				.length());
		mEditText1.addTextChangedListener(new TextWatcher() {
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
					mEditText1.setText("+91");
					Selection.setSelection(mEditText1.getText(), mEditText1
							.getText().length());
				}
			}
		});

		mEditText2.setText("+91");
		Selection.setSelection(mEditText2.getText(), mEditText2.getText()
				.length());
		mEditText2.addTextChangedListener(new TextWatcher() {
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
					mEditText2.setText("+91");
					Selection.setSelection(mEditText2.getText(), mEditText2
							.getText().length());
				}
			}
		});

		mEditText3.setText("+91");
		Selection.setSelection(mEditText3.getText(), mEditText3.getText()
				.length());
		mEditText3.addTextChangedListener(new TextWatcher() {
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
					mEditText3.setText("+91");
					Selection.setSelection(mEditText3.getText(), mEditText3
							.getText().length());
				}
			}
		});

		String storeNumber1 = mSharedPreferences.getString(Consts.filename1,
				"Number1");
		mEditText1.setText(storeNumber1);

		String storeNumber2 = mSharedPreferences.getString(Consts.filename2,
				"Number2");
		mEditText2.setText(storeNumber2);

		String storeNumber3 = mSharedPreferences.getString(Consts.filename3,
				"Number3");
		mEditText3.setText(storeNumber3);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.help:
			Intent intent = new Intent(this, HelpActivity.class);
			startActivity(intent);
			return true;
		case R.id.feedback:
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "the2crazygeeks@gmail.com" });
			i.putExtra(Intent.EXTRA_SUBJECT, "Feedback on TRACK YOUR PHONE");
			i.putExtra(Intent.EXTRA_TEXT, "please write your feedback here ...");
			try {
				startActivity(Intent.createChooser(i, "Send mail..."));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(MainActivity.this,
						"There are no email clients installed.",
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.invitation:
			Intent invite = new Intent(this, InviteFriendActivity.class);
			startActivity(invite);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private Cursor queryPhoneNumbers(long contactId) {
	    ContentResolver cr = getContentResolver();
	    Uri baseUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
	            contactId);
	    Uri dataUri = Uri.withAppendedPath(baseUri,
	            ContactsContract.Contacts.Data.CONTENT_DIRECTORY);

	    Cursor c = cr.query(dataUri, new String[] { Phone._ID, Phone.NUMBER,
	            Phone.IS_SUPER_PRIMARY, RawContacts.ACCOUNT_TYPE, Phone.TYPE,
	            Phone.LABEL }, Data.MIMETYPE + "=?",
	            new String[] { Phone.CONTENT_ITEM_TYPE }, null);
	    if (c != null && c.moveToFirst()) {
	        return c;
	    }
	    return null;
	}
	
	
	public void showSettingsAlert(String provider) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MainActivity.this);

		alertDialog.setTitle(provider + " SETTINGS");

		alertDialog.setMessage(provider
				+ " is not enabled! Want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						MainActivity.this.startActivity(intent);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();
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
						mEditText1.setText(phn_no);
						// Toast.makeText(this, "contact info : "+
						// phn_no+"\n"+name, Toast.LENGTH_LONG).show();
					}
				}
			}
			break;
		case (2):
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
						mEditText2.setText(phn_no);
					}
				}
			}
			break;
		case (3):
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
						mEditText3.setText(phn_no);
					}
				}
			}
		break;
		}
		
	}

	public class OnClickHandler implements OnClickListener {
		public void onClick(View v) {

			if (v == m_iv_iamhere) {
				Intent intent = new Intent(MainActivity.this,
						SendMyLocationActivity.class);
				startActivity(intent);
			}
			if (v == m_iv_contact1) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, 1);
				onActivityResult(1, 1, intent);
			}

			if (v == m_iv_contact2) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, 2);
				onActivityResult(2, 1, intent);
			}

			if (v == m_iv_contact3) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, 3);
				onActivityResult(3, 1, intent);
			}

			if (v == mReset) {
				mEditText1.getText().clear();
				mEditText2.getText().clear();
				mEditText3.getText().clear();
			}

			if (v == mSave) {
				String stringData1 = mEditText1.getText().toString();
				String stringData2 = mEditText2.getText().toString();
				String stringData3 = mEditText3.getText().toString();

				if ((stringData1.equals("+91")) && (stringData2.equals("+91"))
						&& (stringData3.equals("+91"))) {
					// Toast.makeText(State_on.this,
					// "Please Enter at least 1 number",Toast.LENGTH_SHORT).show();
					final Toast toast4 = Toast.makeText(MainActivity.this,
							"Please Enter at least 1 number",
							Toast.LENGTH_SHORT);
					toast4.show();
					Handler handler4 = new Handler();
					handler4.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast4.cancel();
						}
					}, 1000);

					String storeNumber1 = mSharedPreferences.getString(
							Consts.filename1, "Number1");
					mEditText1.setText(storeNumber1);

					String storeNumber2 = mSharedPreferences.getString(
							Consts.filename2, "Number2");
					mEditText2.setText(storeNumber2);

					String storeNumber3 = mSharedPreferences.getString(
							Consts.filename3, "Number3");
					mEditText3.setText(storeNumber3);
				} else {
					mSharedPreferencesEditor.putString(Consts.filename1,
							stringData1).commit();
					mSharedPreferencesEditor.putString(Consts.filename2,
							stringData2).commit();
					mSharedPreferencesEditor.putString(Consts.filename3,
							stringData3).commit();
					// Toast.makeText(State_on.this,
					// "Contact has been saved",Toast.LENGTH_SHORT).show();
					final Toast toast4 = Toast.makeText(MainActivity.this,
							"Contact has been saved", Toast.LENGTH_SHORT);
					toast4.show();
					Handler handler4 = new Handler();
					handler4.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast4.cancel();
						}
					}, 1000);
				}

			}

			if (v == mChange_pass) {
				startActivity(m_Intent2);
				finish();
			}
			if (v == mChange_num) {
				startActivity(m_Intent1);
				finish();
			}

		}

	}

}
