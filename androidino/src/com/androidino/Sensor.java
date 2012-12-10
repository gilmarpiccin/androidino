package com.androidino;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Sensor extends Activity implements View.OnClickListener{

	private Button btnTemperatura;
	private ToggleButton tgbGeral, tgbMovimento, tgbfogo;
	private TextView txtLegenda;
	private WebService ws;
	private Mensagem ms;
	private SharedPreferences preferencia;
	
	//criação da classe
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor);
		inicializaComponentes();
		preferencia = getSharedPreferences("ConfigServidor",MODE_PRIVATE);
		ws = new WebService(preferencia);
		ms = new Mensagem();
		validaSensores();
	}
	
	//instancia compoentes da tela
	void inicializaComponentes(){
		
		//Botão com status
		tgbGeral = (ToggleButton) findViewById(R.id.tgbGeral);
		tgbGeral.setOnClickListener(this);
		tgbMovimento = (ToggleButton) findViewById(R.id.tgbMovimento);
		tgbMovimento.setOnClickListener(this);
		tgbfogo = (ToggleButton) findViewById(R.id.tgbFogo);
		tgbfogo.setOnClickListener(this);

		//botão
		btnTemperatura = (Button) findViewById(R.id.btnTemperatura);
		btnTemperatura.setOnClickListener(this);
		
		//label
		txtLegenda = (TextView) findViewById(R.id.txtSensorLegenda);
	}

	//Evento click dos componetes
	public void onClick(View v) {
		//Retorno para mensagem ao usuário
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
		
		case R.id.btnTemperatura:
			String sretorno = ws.DHT();
			String [] DHT = sretorno.split(Pattern.quote(";"));
			btnTemperatura.setText("Umidade: " + DHT[0] + "% \n" + "Temperatura: " + DHT[1] + " ºC");
			ms.showToast("Umidade: " + DHT[0] + "% \n" + "Temperatura: " + DHT[1] + " ºC",this) ;
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
	
	//valida o retorno do Alarme separando os Sensores
	void validaSensores(){
		try {
			// Recebe a string com os status dos sensores concatenados por ";"
			String sensor = ws.Sensor();
			
			//Separa os status em posições do array 
			String [] sensor1 = sensor.split (Pattern.quote (";"));  
			
			//seta a propriedade "checada" conforme o status do sensor
			//nesse momento os 0 e 1 já estão alterados para true ou false porém em  modo texto
			//então deve ser feito a conversão para boolean
			tgbMovimento.setChecked(Boolean.parseBoolean(sensor1[0]));
			tgbfogo.setChecked(Boolean.parseBoolean(sensor1[1]));
			tgbGeral.setChecked(Boolean.parseBoolean(sensor1[2]));	
		} catch (Exception e) {
			ms.showToast("Conexão falhou!\n O Alarme pode estar desligado!", this);
			finish();
		}
		

	}

	//Cria o menu "Escondido" função panico
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_panico, menu);
		return true;
	}

	//Clique do Menu que fica escondido
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_panico:
				ws.Panico();
			}
		return super.onOptionsItemSelected(item);
	}
	
}
