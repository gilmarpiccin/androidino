package com.androidino;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	
	public void msgSimNao (String msg, final Activity tela){
		
		AlertDialog.Builder dial = new AlertDialog.Builder(this);
		
		dial.setMessage("Deseja sair desta activity ???");
		dial.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
						
			}
		});
	
		dial.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
						
			}
		});
	
		dial.setTitle("Aviso");
		dial.show();
	}
}
