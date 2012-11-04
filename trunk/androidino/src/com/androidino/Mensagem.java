package com.androidino;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Mensagem extends Activity {
	ProgressDialog pd;
	
	
	void calncelEspera(){
		pd.cancel();
	}
	
	public void gravaCofUsuario (String prUsuario, String prSenha,SharedPreferences pref){
		SharedPreferences settings = pref;
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("usuario",prUsuario);
		editor.putString("senha",prSenha);
		editor.commit();	
		
	}
	
	public void msgEspera(String msg,String titulo, Activity tela){
		//ProgressDialog dialog = ProgressDialog.show(tela, titulo, msg, true,true);
		pd = ProgressDialog.show(tela, titulo, msg, true,true);
	}
	
	public void showToast(String msg, Activity tela){
		Toast.makeText(tela, 
				msg,
				Toast.LENGTH_LONG).show();
		
	}

}
