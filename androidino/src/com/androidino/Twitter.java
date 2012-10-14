package com.androidino;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Twitter extends Activity implements View.OnClickListener{
	
	private EditText edtToken;
	private Button btnEnviaToken,btnGerarToken;
	private Mensagem ms;
	private WebService ws;
	private SharedPreferences preferencia;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter);
		ms = new Mensagem();
		ws = new WebService(preferencia);
		iniciaComponetes();
	}
	

	public void onClick(View v) {
		
		switch (v.getId()){
			 
			case R.id.btnEnviaToken:
				String msg = ws.token(edtToken.getText().toString());
				ms.showToast(msg,this);	
			break;
			case R.id.btnGerarToken:
				final WebView pagina = new WebView(this);//(WebView)findViewById(R.id.pagina);
				//setContentView(pagina);
				//Habilita o JavaScript nas paginas web
				pagina.getSettings().setJavaScriptEnabled(true);
				 
				//Habilita o zoom nas páginas
				pagina.getSettings().setSupportZoom(true);
				//pagina.setScrollContainer(true);
				pagina.loadUrl("http://arduino-tweet.appspot.com/oauth/twitter/login");
				
			break;
		}	
	}
	
	public void iniciaComponetes(){
		edtToken = (EditText) findViewById(R.id.edtToken);
		btnEnviaToken = (Button) findViewById(R.id.btnEnviaToken);
		btnEnviaToken.setOnClickListener(this);
		btnGerarToken = (Button) findViewById(R.id.btnGerarToken);
		btnGerarToken.setOnClickListener(this);		
	}
}
