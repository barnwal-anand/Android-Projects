package com.geekycorporation.findurphone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geekycorporation.findurphone.R;

public class SignupPageActivity extends Activity {

	SharedPreferences m_SharedPreferences;
	private SharedPreferences.Editor m_SharedPreferencesEditor;

	private TextView mTextView_sign_up_page;

	private EditText mEditText_name;
	private EditText mEditText_username;
	private EditText mEditText_password;
	private EditText mEditText_con_password;
	private EditText mEditText_phone_no;

	private Button mButton_sign_up;
	private Intent mIntent;
	private String mPassword;
	private String mUsername;
	private String mCon_Password;
	private String mName;
	private String mPhone_number;

	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_page);

		m_SharedPreferences = SharedPreferencesBuilder
				.getSharedPreferences(this);
		m_SharedPreferencesEditor = m_SharedPreferences.edit();

		mIntent = new Intent(SignupPageActivity.this, LoginPageActivity.class);
		mTextView_sign_up_page = (TextView) findViewById(R.id.text_sign_up_page);
		mEditText_name = (EditText) findViewById(R.id.editText_name);
		mEditText_username = (EditText) findViewById(R.id.editText_username);
		mEditText_password = (EditText) findViewById(R.id.editText_password);
		mEditText_con_password = (EditText) findViewById(R.id.editText_con_password);
		mEditText_phone_no = (EditText) findViewById(R.id.editText_phone_no);
		mButton_sign_up = (Button) findViewById(R.id.button_sign_up);

		mEditText_phone_no.setText("+91");
		Selection.setSelection(mEditText_phone_no.getText(), mEditText_phone_no
				.getText().length());
		mEditText_phone_no.addTextChangedListener(new TextWatcher() {
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
					mEditText_phone_no.setText("+91");
					Selection.setSelection(mEditText_phone_no.getText(),
							mEditText_phone_no.getText().length());
				}
			}
		});

		if (!m_SharedPreferences.getBoolean(Consts.sp_sign_up_page_firsttime,
				true)) {

			startActivity(mIntent);
		}

		mButton_sign_up.setOnClickListener(new OnClickHandler());
	}

	public class OnClickHandler implements OnClickListener {
		public void onClick(View v) {
			if (v == mButton_sign_up) {
				mName = mEditText_name.getText().toString().trim();
				mPhone_number = mEditText_phone_no.getText().toString().trim();
				mPassword = mEditText_password.getText().toString().trim();
				mUsername = mEditText_username.getText().toString().trim();
				mCon_Password = mEditText_con_password.getText().toString()
						.trim();
				if (mName.matches("")) {
					// Toast.makeText(Sign_up_page.this,
					// "Please Enter Your Name",Toast.LENGTH_SHORT).show();
					final Toast toast = Toast.makeText(SignupPageActivity.this,
							"Please Enter Your Name", Toast.LENGTH_SHORT);
					toast.show();
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast.cancel();
						}
					}, 1000);
					return;
				}
				if (mUsername.matches("")) {
					{
						// Toast.makeText(Sign_up_page.this,
						// "Username is empty",Toast.LENGTH_SHORT).show();
						final Toast toast = Toast.makeText(
								SignupPageActivity.this, "Username is empty",
								Toast.LENGTH_SHORT);
						toast.show();
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								toast.cancel();
							}
						}, 1000);
					}
					if (mPassword.matches("")) {
						// Toast.makeText(Sign_up_page.this,
						// "Password is empty",Toast.LENGTH_SHORT).show();
						final Toast toast = Toast.makeText(
								SignupPageActivity.this, "Password is empty",
								Toast.LENGTH_SHORT);
						toast.show();
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								toast.cancel();
							}
						}, 1000);
					}
					return;
				}
				if (mPassword.matches("")) {
					// Toast.makeText(Sign_up_page.this,
					// "Password is empty",Toast.LENGTH_SHORT).show();
					final Toast toast = Toast.makeText(SignupPageActivity.this,
							"Password is empty", Toast.LENGTH_SHORT);
					toast.show();
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast.cancel();
						}
					}, 1000);
					return;
				}
				if (!mPassword.equals(mCon_Password)) {
					// Toast.makeText(Sign_up_page.this,
					// "Password do not match",Toast.LENGTH_SHORT).show();
					final Toast toast = Toast.makeText(SignupPageActivity.this,
							"Password do not match", Toast.LENGTH_SHORT);
					toast.show();
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast.cancel();
						}
					}, 1000);
					return;
				}
				if (mPhone_number.length() != 13) {
					// Toast.makeText(Sign_up_page.this,
					// "Enter the 10 digit Phone Number",Toast.LENGTH_SHORT).show();
					final Toast toast = Toast.makeText(SignupPageActivity.this,
							"Enter the 10 digit Phone Number",
							Toast.LENGTH_SHORT);
					toast.show();
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast.cancel();
						}
					}, 1000);
					return;
				}
			//	mPhone_number = "+91" + mPhone_number;
				m_SharedPreferencesEditor.putString(Consts.sp_user_name,
						mUsername).commit();
				m_SharedPreferencesEditor.putString(Consts.sp_password,
						mPassword).commit();
				m_SharedPreferencesEditor.putString(Consts.sp_name, mName)
						.commit();
				m_SharedPreferencesEditor.putString(Consts.sp_phone_number,
						mPhone_number).commit();
				Toast.makeText(
						SignupPageActivity.this,
						"Welcome " + mName + "\n"
								+ "Your Account has been successfully created",
						Toast.LENGTH_LONG).show();
				m_SharedPreferencesEditor.putBoolean(
						Consts.sp_sign_up_page_firsttime, false).commit();
				startActivity(mIntent);
			}
		}
	}

}
