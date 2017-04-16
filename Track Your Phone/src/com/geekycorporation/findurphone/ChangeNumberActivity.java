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
import android.widget.Toast;

import com.geekycorporation.findurphone.R;

public class ChangeNumberActivity extends Activity {

	private EditText mEditText_change_num;
	private Button mButton_proceed;
	private Intent mIntent ;
	SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mSharedPreferencesEditor;

	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_number);
		mIntent = new Intent(ChangeNumberActivity.this,MainActivity.class);
		mSharedPreferences = SharedPreferencesBuilder
				.getSharedPreferences(this);
		mSharedPreferencesEditor = mSharedPreferences.edit();

		mEditText_change_num = (EditText) findViewById(R.id.editText_new_number);
		mButton_proceed = (Button) findViewById(R.id.button_change_num);
		mButton_proceed.setOnClickListener(new OnClickHandler());

		mEditText_change_num.setText("+91");
		Selection.setSelection(mEditText_change_num.getText(),
				mEditText_change_num.getText().length());
		mEditText_change_num.addTextChangedListener(new TextWatcher() {
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
					mEditText_change_num.setText("+91");
					Selection.setSelection(mEditText_change_num.getText(),
							mEditText_change_num.getText().length());
				}
			}
		});
	}

	public class OnClickHandler implements OnClickListener {
		public void onClick(View v) {
			if (v == mButton_proceed) {
				String altNumber = mEditText_change_num.getText().toString();
				if (altNumber.length() != 13) {
					// Toast.makeText(Sign_up_page.this,
					// "Enter the 10 digit Phone Number",Toast.LENGTH_SHORT).show();
					final Toast toast = Toast.makeText(ChangeNumberActivity.this,
							"Enter the correct 10 digit Phone Number",
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
				} else {
					mSharedPreferencesEditor.putString(Consts.sp_phone_number,
							altNumber).commit();
					final Toast toast = Toast.makeText(ChangeNumberActivity.this,
							"Number saved",
							Toast.LENGTH_SHORT);
					toast.show();
					Handler handler4 = new Handler();
					handler4.postDelayed(new Runnable() {
						@Override
						public void run() {
							toast.cancel();
						}
					}, 1000);
					startActivity(mIntent);
				}
			}
		}
	}
}
