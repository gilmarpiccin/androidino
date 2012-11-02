package com.androidino;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Mensagem extends Activity {
	ProgressDialog pd;
	
	
	public void msgEspera(String msg,String titulo, Activity tela){
		//ProgressDialog dialog = ProgressDialog.show(tela, titulo, msg, true,true);
		pd = ProgressDialog.show(tela, titulo, msg, true,true);
	}
	
	void calncelEspera(){
		pd.cancel();
	}
	
	public void showToast(String msg, Activity tela){
		Toast.makeText(tela, 
				msg,
				Toast.LENGTH_LONG).show();
		
	}
	
	public void gravaCofUsuario (String prUsuario, String prSenha,SharedPreferences pref){
		SharedPreferences settings = pref;
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("usuario",prUsuario);
		editor.putString("senha",prSenha);
		editor.commit();	
		
	}

}
