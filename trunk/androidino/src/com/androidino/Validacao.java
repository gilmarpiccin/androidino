package com.androidino;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class Validacao extends Activity {
private String usuario, senha;
	
	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void receParamLogin (Intent intencao){
		Bundle parametro = intencao.getExtras();
		setUsuario(parametro.getString("usuario"));
		setSenha(parametro.getString("senha"));
		
	}
	
	public void enviarParametro(Intent intencao ){
		Bundle parametro = new Bundle();
		parametro.putString("usuario",getUsuario());
		parametro.putString("senha",getSenha());
		intencao.putExtras(parametro);
	}
	public void carregaCampos(String usuario,String senha){
		setUsuario(usuario);
		setSenha(senha);
	}
	
	public void msgEspera(String msg,String titulo, Activity tela){
	ProgressDialog dialog = ProgressDialog.show(tela, titulo, msg, true);
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
