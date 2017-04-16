package com.geekycorporation.findurphone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.geekycorporation.findurphone.R;

public class IntroPageActivity extends Activity {

	private Intent m_Intent;
	private Button m_Start;
	SharedPreferences m_SharedPreferences;
	private SharedPreferences.Editor m_SharedPreferencesEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intropage);
		m_SharedPreferences = SharedPreferencesBuilder
				.getSharedPreferences(this);
		m_SharedPreferencesEditor = m_SharedPreferences.edit();
		m_Intent = new Intent(IntroPageActivity.this, SignupPageActivity.class);

		if (!m_SharedPreferences.getBoolean(Consts.sp_sign_up_page_firsttime,
				true))
			startActivity(m_Intent);

		m_Start = (Button) findViewById(R.id.first_time);
		m_Start.setOnClickListener(new OnClickHandler());
	}

	public class OnClickHandler implements OnClickListener {
		public void onClick(View v) {
			if (v == m_Start) {
				startActivity(m_Intent);
			}
		}
	}
}