package com.androidino;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Sensor extends Activity implements View.OnClickListener{

	private ToggleButton tgbGeral, tgbMovimento, tgbfogo, tgbTemperatura ;
	private TextView txtLegenda,txtGeral,txtMovimento,txtFogo,txtTemperatura;
	private SharedPreferences preferencia;
	private WebService ws;
	private Mensagem ms;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor);
		inicializaComponentes();
		preferencia = getSharedPreferences("ConfigSevidor",MODE_PRIVATE);
		ws = new WebService(preferencia);
		ms = new Mensagem();
		validaSensores();
	}

	public void onClick(View v) {
		String sLigaDesliga = "";
		
		switch (v.getId()) {

		case R.id.tgbGeral:
			tgbGeral.setChecked(ws.sensorDigital("ONOFF"));
			if (tgbGeral.isChecked())
				sLigaDesliga ="Ligado!";
			else
				sLigaDesliga="Desligado!";
						
			ms.showToast("Alarme " + sLigaDesliga  ,this) ;
			break;
		
		case R.id.tgbTemperatura:
			tgbTemperatura.setChecked(ws.sensorDigital("DHT"));
			if (tgbTemperatura.isChecked())
				sLigaDesliga =" Ligado!";
			else
				sLigaDesliga=" Desligado!";
			
			ms.showToast(txtLegenda.getText() + " " + txtTemperatura.getText() + "\n" + sLigaDesliga  ,this) ;
			break;
			
		case R.id.tgbMovimento:
			tgbMovimento.setChecked(ws.sensorDigital("MOVIMENTO"));
			if (tgbMovimento.isChecked())
				sLigaDesliga =" Ligado!";
			else
				sLigaDesliga=" Desligado!";
			
			ms.showToast(txtLegenda.getText() + "" + tgbMovimento.getText() + "\n" +  sLigaDesliga  ,this) ;
			break;
			
		case R.id.tgbFogo:
			tgbfogo.setChecked(ws.sensorDigital("FOGO"));
			if (tgbfogo.isChecked())
				sLigaDesliga =" Ligado!";
			else
				sLigaDesliga=" Desligado!";
			ms.showToast(txtLegenda.getText() + " " + tgbfogo.getText() + "\n" +sLigaDesliga  ,this) ;
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
		tgbTemperatura = (ToggleButton) findViewById(R.id.tgbTemperatura);
		tgbTemperatura.setOnClickListener(this);
		
		//label
		txtLegenda = (TextView) findViewById(R.id.txtSensorLegenda);
		txtGeral = (TextView) findViewById(R.id.tgbGeral);
		txtMovimento = (ToggleButton) findViewById(R.id.tgbMovimento);
		txtFogo = (TextView) findViewById(R.id.tgbFogo);
		txtTemperatura = (TextView) findViewById(R.id.tgbTemperatura);
		
		
	}
	
	void validaSensores(){
		try {
			String sensor = ws.Sensor();
			String [] sensor1 = sensor.split (Pattern.quote (";"));  
			
			tgbTemperatura.setChecked(Boolean.parseBoolean(sensor1[0]));
			tgbMovimento.setChecked(Boolean.parseBoolean(sensor1[1]));
			tgbfogo.setChecked(Boolean.parseBoolean(sensor1[2]));
			tgbGeral.setChecked(Boolean.parseBoolean(sensor1[3]));	
		} catch (Exception e) {
			ms.showToast("Conexão falhou!\n O Alarme pode estar desligado!", this);
			finish();
		}
		

	}
	
}
