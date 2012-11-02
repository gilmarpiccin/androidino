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
	private WebService ws;
	private SharedPreferences preferencia;
	private Mensagem ms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comunicacao);
		
		btnConfirmar = (Button) findViewById(R.id.btnConfimarComu);
		btnConfirmar.setOnClickListener(this);
		edtEndIP = (EditText) findViewById(R.id.edtEnderecoIP);
		edtPorta = (EditText) findViewById(R.id.edtPorta);
		
		preferencia = getSharedPreferences("ConfigSevidor",MODE_PRIVATE);
		edtEndIP.setText(preferencia.getString("IP", ""));
		edtPorta.setText(preferencia.getString("porta", "8080"));
		
		ws = new WebService(preferencia);
		ms = new Mensagem();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnConfimarComu:
			salvarIPPorta(edtEndIP.getText().toString(),edtPorta.getText().toString(),getSharedPreferences("ConfigSevidor",MODE_PRIVATE));
			finish();
			break;

		default:
			break;
		}
		
	}
	
	void salvarIPPorta(String IP , String Porta, SharedPreferences pref){
		SharedPreferences settings = pref;
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("ip",IP);
		editor.putString("porta",Porta);
		editor.commit();
	}
}
