package com.geekycorporation.findurphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geekycorporation.findurphone.R;

public class LoginPageActivity extends Activity {

	public static SharedPreferences mSharedPreferences;
	private TextView mTextView_login_page;
	private EditText mEditText_username;
	private EditText mEditText_password;
	private Button mButton_sign_in;
	private Button mButton_forgot_password;
	private Intent mIntent;
	private String mPassword;
	private String mUsername;
	private String m_sp_name;
	private String m_sp_Password;
	private String m_sp_Username;
	private String m_phone_number;

	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		mIntent = new Intent(LoginPageActivity.this, MainActivity.class);
		mTextView_login_page = (TextView) findViewById(R.id.text_login_page);
		// mTextView_username = (TextView)findViewById(R.id.text_username);
		mEditText_username = (EditText) findViewById(R.id.editText_username);
		// mTextView_password = (TextView)findViewById(R.id.text_password);
		mEditText_password = (EditText) findViewById(R.id.editText_password);
		mButton_sign_in = (Button) findViewById(R.id.button_sign_in);
		mButton_sign_in.setOnClickListener(new OnClickHandler());
		mButton_forgot_password = (Button) findViewById(R.id.button_forgot_password);
		mButton_forgot_password.setOnClickListener(new OnClickHandler());

		mSharedPreferences = SharedPreferencesBuilder
				.getSharedPreferences(this);
		// mSharedPreferencesEditor = mSharedPreferences.edit();
		m_sp_Username = mSharedPreferences.getString(Consts.sp_user_name,
				"Name");
		m_sp_name = mSharedPreferences.getString(Consts.sp_name, "Person");
		m_sp_Password = mSharedPreferences.getString(Consts.sp_password,
				"Password");
		m_phone_number = mSharedPreferences.getString(Consts.sp_phone_number,
				"Number");

	}

	public class OnClickHandler implements OnClickListener {
		@SuppressWarnings("deprecation")
		public void onClick(View v) {

			boolean isChecked = mSharedPreferences.getBoolean(
					Consts.sp_ENABLE_SERVICE, false);
			if (v == mButton_sign_in) {
				mPassword = mEditText_password.getText().toString().trim();
				mUsername = mEditText_username.getText().toString().trim();

				if (!mUsername.equals(m_sp_Username)) {
					// Toast.makeText(Login_page.this,
					// "Username Incorrect",Toast.LENGTH_SHORT).show();
					final Toast toast1 = Toast.makeText(LoginPageActivity.this,
							"Username Incorrect", Toast.LENGTH_SHORT);
					toast1.show();
					Handler handler1 = new Handler();
					handler1.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast1.cancel();
						}
					}, 1000);
					if (!mPassword.equals(m_sp_Password)) {
						// Toast.makeText(Login_page.this,
						// "Password Incorrect",Toast.LENGTH_SHORT).show();
						final Toast toast2 = Toast.makeText(
								LoginPageActivity.this, "Password Incorrect",
								Toast.LENGTH_SHORT);
						toast2.show();
						Handler handler2 = new Handler();
						handler2.postDelayed(new Runnable() {
							@Override
							public void run() {
								toast2.cancel();
							}
						}, 1000);
						return;
					}
					return;
				}
				if (!mPassword.equals(m_sp_Password)) {
					// Toast.makeText(Login_page.this,
					// "Password Incorrect",Toast.LENGTH_SHORT).show();
					final Toast toast3 = Toast.makeText(LoginPageActivity.this,
							"Password Incorrect", Toast.LENGTH_SHORT);
					toast3.show();
					Handler handler3 = new Handler();
					handler3.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast3.cancel();
						}
					}, 1000);
					return;
				}
				startActivity(mIntent);
			}

			if (v == mButton_forgot_password) {
				mUsername = mEditText_username.getText().toString().trim();
				if (!mUsername.equals(m_sp_Username)) {
					// Toast.makeText(Login_page.this,
					// "Please enter correct Username",Toast.LENGTH_SHORT).show();
					final Toast toast4 = Toast
							.makeText(LoginPageActivity.this,
									"Please enter correct Username",
									Toast.LENGTH_SHORT);
					toast4.show();
					Handler handler4 = new Handler();
					handler4.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast4.cancel();
						}
					}, 1000);
					return;
				}

				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						LoginPageActivity.this);
				builder1.setTitle("Are you sure ?");
				builder1.setMessage("Your password will be sent to your alternate number");
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
								Toast.makeText(LoginPageActivity.this,
										"Password sent", Toast.LENGTH_SHORT)
										.show();
								String msg = "Please inform " + m_sp_name
										+ "\n" + "Username : " + m_sp_Username
										+ "\n" + "Password : " + m_sp_Password
										+ "\n" + "Sent from\nTRACK YOUR PHONE";
								SmsManager sm = SmsManager.getDefault();
								sm.sendTextMessage(m_phone_number, null, msg,
										null, null);

								dialog.cancel();
								
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();
			}
		}
	}

}
