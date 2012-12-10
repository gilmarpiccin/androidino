package com.androidino;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Mensagem extends Activity {
	ProgressDialog pd;
	
	//cancela a execu��o do Toast caso precise
	void calncelEspera(){
		pd.cancel();
	}
	
	//Grava conf d usu�rio no arquivo de conf compartilhado entre as classes
	public void gravaCofUsuario (String prUsuario, String prSenha,SharedPreferences pref){
		SharedPreferences settings = pref;
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("usuario",prUsuario);
		editor.putString("senha",prSenha);
		editor.commit();	
		
	}
	
	//Exibe uma bara de progress�o na tela dando um feedback aou usu�rio 
	public void msgEspera(String msg,String titulo, Activity tela){
		//ProgressDialog dialog = ProgressDialog.show(tela, titulo, msg, true,true);
		pd = ProgressDialog.show(tela, titulo, msg, true,true);
	}
	
	//Mensagem curta na tela (informativa)
	public void showToast(String msg, Activity tela){
		Toast.makeText(tela, 
				msg,
				Toast.LENGTH_LONG).show();
		
	}

}
