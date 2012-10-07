package com.androidino;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements View.OnClickListener{
	private Button btnLogin;
	private EditText edtUsuario,edtSenha;
	private Mensagem ms;
	private SharedPreferences preferencia;
	private WebService ws;
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ms = new Mensagem();
		preferencia = PreferenceManager.getDefaultSharedPreferences(this);
		ws = new WebService(preferencia);
        inicializandoComponentes();
  }

	public void onClick(View v) {
		
		switch (v.getId()){
 
			case R.id.btnLogin:
				ms.msgEspera("Carregando...","Aguarde",this);
				
				//validação do login (retorno é boolano).
				if(ws.login()){
					Intent AbrirMenu = new Intent("android.intent.action.MENUINICIAL");
					
					SharedPreferences settings = getSharedPreferences("ConfigSevidor",MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("usuario",edtUsuario.getText().toString());
					editor.putString("senha",edtSenha.getText().toString());
					editor.commit();
					startActivity(AbrirMenu);
				}else{
					ms.showToast("Login ou Senha incorreta!",this);//menssagem rápida na tela.
					ms.msCancEspera();// fecha a msgEspera;
				break;
			}
		}
	}

	public void inicializandoComponentes()
	{
		 //fazendo a referencia dos compoentes da tela (XML) para a classe 
	      btnLogin 	 = (Button)findViewById(R.id.btnLogin);
	      btnLogin.setOnClickListener(this);
	      
	      edtUsuario = (EditText)findViewById(R.id.edtUsuario);
	      edtSenha   = (EditText)findViewById(R.id.edtSenha);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}	
	
}
