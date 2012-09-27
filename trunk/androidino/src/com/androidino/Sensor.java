package com.androidino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Sensor extends Activity implements View.OnClickListener{

	ToggleButton tgbGeal;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor);
		
		tgbGeal = (ToggleButton) findViewById(R.id.tgbGeral);
		tgbGeal.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (tgbGeal.isChecked()) {
			SharedPreferences settings = getSharedPreferences("ConfServidor", 0);
			String ip = settings.getString("ip", "").toString();
			String porta = settings.getString("porta", "").toString();
			
			Toast.makeText(this, 
					ip,
					Toast.LENGTH_LONG).show();
			
			Toast.makeText(this, 
					porta,
					Toast.LENGTH_LONG).show();
		
			
		}
		
	}

}
