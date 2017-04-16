package com.geekycorporation.findurphone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geekycorporation.findurphone.R;

public class ChangePasswordActivity extends Activity {

	public static SharedPreferences mSharedPreferences_password;
	private SharedPreferences.Editor mSharedPreferencesEditor;
	private TextView mTextView_change_pass_page ;
	private TextView mTextView_new_password ;
	private EditText mEditText_new_password ;
	private TextView mTextView_con_password ;
	private EditText mEditText_con_password ;
	private Button mButton_proceed ;
	private Intent mIntent ;
	private String mNewpassword;
	private String mConfirmpassword;

	protected void onCreate(Bundle savedInstanceState)
	{
		setTheme(android.R.style.Theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		mIntent = new Intent(ChangePasswordActivity.this,LoginPageActivity.class);
		mTextView_change_pass_page = (TextView)findViewById(R.id.text_change_pass_page);
		//	mTextView_new_password = (TextView)findViewById(R.id.text_new_password);
		mEditText_new_password = (EditText)findViewById(R.id.editText_new_password);
		//	mTextView_con_password = (TextView)findViewById(R.id.text_con_password);
		mEditText_con_password = (EditText)findViewById(R.id.editText_con_password);
		mButton_proceed = (Button)findViewById(R.id.button_change_pass);
		mButton_proceed.setOnClickListener(new OnClickHandler());

		mSharedPreferences_password = SharedPreferencesBuilder.getSharedPreferences(this);
		mSharedPreferencesEditor = mSharedPreferences_password.edit();

	}

	public class OnClickHandler implements OnClickListener
	{
		public void onClick(View v)
		{
			if(v == mButton_proceed)
			{
				mNewpassword = mEditText_new_password.getText().toString().trim();
				mConfirmpassword = mEditText_con_password.getText().toString().trim();
				if(mNewpassword.equals(""))
				{
					//Toast.makeText(Change_password.this, "Password is Empty",Toast.LENGTH_SHORT).show();
					final Toast toast = Toast.makeText(ChangePasswordActivity.this, "Password is Empty", Toast.LENGTH_SHORT);
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
				if(!mNewpassword.equals(mConfirmpassword))
				{
					//Toast.makeText(Change_password.this, "Password do not match",Toast.LENGTH_SHORT).show();
					final Toast toast = Toast.makeText(ChangePasswordActivity.this, "Password do not match", Toast.LENGTH_SHORT);
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

				mSharedPreferencesEditor.putString(Consts.sp_password, mNewpassword).commit();
				Toast.makeText(ChangePasswordActivity.this, "Your Password has been changed successfully",Toast.LENGTH_SHORT).show();
				startActivity(mIntent);
			}
		}
	}

}
