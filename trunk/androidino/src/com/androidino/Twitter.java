package com.androidino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Twitter extends Activity implements View.OnClickListener{
	
	EditText edtTolken;
	Button btnEnviaTolken,btnGerarTolken;
	Validacao vl = new Validacao();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter);
				
		edtTolken = (EditText) findViewById(R.id.edtTolken);
		btnEnviaTolken = (Button) findViewById(R.id.btnEnviaTolken);
		btnEnviaTolken.setOnClickListener(this);
		btnGerarTolken = (Button) findViewById(R.id.btnGerarTolken);
		btnGerarTolken.setOnClickListener(this);
		Intent intencao = getIntent();
		vl.receParamLogin(intencao);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_twitter, menu);
		return true;
	}

	public void onClick(View v) {
		
		switch (v.getId()){
			 
			case R.id.btnEnviaTolken:
				
				WebService ws = new  WebService("http://192.168.1.177:8080/$" + 
												edtTolken.getText().toString() + 
												"?TOLKEN");
				String msg = ws.getRequisicao();
				//criando uma Torrada
				Toast.makeText(Twitter.this, 
						msg,
						Toast.LENGTH_LONG).show();
	
			break;
			case R.id.btnGerarTolken:
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
	
}
