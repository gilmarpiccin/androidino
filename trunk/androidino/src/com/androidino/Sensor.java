package com.androidino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Sensor extends Activity implements View.OnClickListener{

	private ToggleButton tgbGeral, tgbMovimento, tgbfogo, tgbTemperatura ;
	private SharedPreferences preferencia;
	private WebService ws;
	private Mensagem ms;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor);
		inicializaComponentes();
		preferencia = PreferenceManager.getDefaultSharedPreferences(this);
		ws = new WebService(preferencia);
		ms = new Mensagem();
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.tgbTemperatura:
			ms.showToast(ws.sensorDigital("DHT"),this) ;
			break;
		case R.id.tgbGeral:
			ms.showToast(ws.sensorDigital("GERAL"),this);
			break;
		case R.id.tgbMovimento:
			ms.showToast(ws.sensorDigital("MOVIMENTO"),this);
			break;
		case R.id.tgbFogo:
			ms.showToast(ws.sensorDigital("FOGO"),this);
			break;
		default:
			break;
		}
		
	}
	
	void inicializaComponentes(){
		tgbGeral = (ToggleButton) findViewById(R.id.tgbGeral);
		tgbGeral.setOnClickListener(this);
		tgbMovimento = (ToggleButton) findViewById(R.id.tgbMovimento);
		tgbMovimento.setOnClickListener(this);
		tgbfogo = (ToggleButton) findViewById(R.id.tgbFogo);
		tgbfogo.setOnClickListener(this);
		
	}
}
