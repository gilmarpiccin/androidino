package com.androidino;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements View.OnClickListener{
	Button btnLogin;
	EditText edtUsuario,edtSenha;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

      
        //inicializando componentes
        inicializandoComponentes();
             
        //inserindo os evento
        btnLogin.setOnClickListener(this);
        		
  }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (event.getAction() == KeyEvent.KEYCODE_ENTER) {
			Toast.makeText(Login.this,  
					Integer.toString(keyCode),
					Toast.LENGTH_LONG).show();
		}
		
		
		Toast.makeText(Login.this,  
				Integer.toString(keyCode),
				Toast.LENGTH_LONG).show();
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyUp(keyCode, event);
	}
		


	public void onClick(View v) {
		// com este método é possivel organivar todos s OnClick
		//faz um switch pelo id da view 
		switch (v.getId()){
		 
		case R.id.btnLogin:
			
			WebService ws = new  WebService("http://192.168.1.177/$" + 
											edtUsuario.getText().toString() + 
											"&" + 
											edtSenha.getText().toString() + 
											"?Login");
			String login = ws.getRequisicao();
			login = login.replaceAll("\n","");
			if(Boolean.parseBoolean(login)){
				Intent AbrirMenu = new Intent("android.intent.action.MENUINICIAL");
				startActivity(AbrirMenu);
				
			}else{
			//criando uma Torrada
			Toast.makeText(Login.this, 
					"Login ou Senha incorreta!",
					Toast.LENGTH_LONG).show();
	
			
			break;
			}
		
		}
		
	}

	public void inicializandoComponentes()
	{
		 //fazendo a referencia aos compoentes da tela
	      btnLogin 	 = (Button)findViewById(R.id.btnLogin);
	      edtUsuario = (EditText)findViewById(R.id.edtUsuario);
	      edtSenha   = (EditText)findViewById(R.id.edtSenha);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}	
	
	
}
