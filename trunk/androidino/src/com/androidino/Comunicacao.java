package com.androidino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Comunicacao extends Activity implements View.OnClickListener{
	private Button btnConfirmar;
	private EditText edtEndIP,edtPorta;
	private Mensagem ms;
	private SharedPreferences preferencia;
	private WebService ws;

	//evento Click dos componetes da tela
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnConfimarComu:
			salvarIPPorta(edtEndIP.getText().toString(),edtPorta.getText().toString(),preferencia);
			finish();
			break;

		default:
			break;
		}
		
	}

	//Criação da classe 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comunicacao);
		
		//Instancia dos compoentes da tela
		btnConfirmar = (Button) findViewById(R.id.btnConfimarComu);
		btnConfirmar.setOnClickListener(this);
		edtEndIP = (EditText) findViewById(R.id.edtEnderecoIP);
		edtPorta = (EditText) findViewById(R.id.edtPorta);
		
		//seta informações do arquivo de conf no campos da tela
		preferencia = getSharedPreferences("ConfigServidor",MODE_PRIVATE);
		edtEndIP.setText(preferencia.getString("ip",""));
		edtPorta.setText(preferencia.getString("porta",""));
		
		ws = new WebService(preferencia);
		ms = new Mensagem();
	}
	
	//Metodo usado para salvar as conf de porta e IP
	void salvarIPPorta(String IP , String Porta, SharedPreferences pref){
		SharedPreferences settings = pref;
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("ip",IP);
		editor.putString("porta",Porta);
		editor.commit();
	}
}
