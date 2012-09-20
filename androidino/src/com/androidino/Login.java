package com.androidino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	

	public void onClick(View v) {
		// com este método é possivel organivar todos s OnClick
		//faz um switch pelo id da view 
		switch (v.getId()){
		 
		case R.id.btnLogin:
			
			WebService ws = new  WebService("http://192.168.1.177:8080/$" + 
											edtUsuario.getText().toString() + 
											"&" + 
											edtSenha.getText().toString() + 
											"?LOGIN");
			String login = ws.getRequisicao();
			login = login.replaceAll("\n","");
			if(Boolean.parseBoolean(login)){
				
				Intent AbrirMenu = new Intent("android.intent.action.MENUINICIAL");
				startActivity(AbrirMenu);
				enviarParametro(AbrirMenu);
				
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
	
	public void enviarParametro(Intent intencao ){
		Bundle parametro = new Bundle();
		parametro.putString("usuario",edtUsuario.getText().toString());
		parametro.putString("senha",edtSenha.getText().toString());
		intencao.putExtras(parametro);
		startActivity(intencao);
	}
}
