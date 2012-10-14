package com.androidino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ip, menu);
		return true;
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
					this.finish();
				}else{
					ms.showToast("Login ou Senha incorreta!",this);//menssagem rápida na tela.
					ms.msCancEspera();// fecha a msgEspera;
				break;
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_ip:
			LayoutInflater li = LayoutInflater.from(this);
			View ipRemoto = li.inflate(R.layout.comunicacao, null);

			//Deixa invivisel o botão do layout Comunicação
			final Button btnConfirmar = (Button)ipRemoto.findViewById(R.id.btnConfimarComu);
			btnConfirmar.setVisibility(ipRemoto.INVISIBLE);
			final EditText edtIP = (EditText) ipRemoto.findViewById(R.id.edtEnderecoIP);
			final EditText edtPorta = (EditText) ipRemoto.findViewById(R.id.edtPorta);
	

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setView(ipRemoto);
			alertDialogBuilder.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	//Ao clicar em OK salva o IP e a PORTA para comunicação com arduino
			    	SharedPreferences settings = getSharedPreferences("ConfigSevidor",MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("ip",edtIP.getText().toString());
					editor.putString("porta",edtPorta.getText().toString());
					editor.commit();				
			    }
			  })
			.setNegativeButton("Cancelar",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    	dialog.cancel();
			    }
			  });

			// Cria alertDialog
			AlertDialog alertDialog = alertDialogBuilder.create();
	
			// Exibe
			alertDialog.show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void inicializandoComponentes()
	{
		 //fazendo a referencia dos compoentes da tela (XML) para a classe 
	      btnLogin 	 = (Button)findViewById(R.id.btnLogin);
	      btnLogin.setOnClickListener(this);
	      
	      edtUsuario = (EditText)findViewById(R.id.edtUsuario);
	      edtSenha   = (EditText)findViewById(R.id.edtSenha);
	      
	      
	}	
	
}
