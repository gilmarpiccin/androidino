package com.androidino;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuInicial extends ListActivity {
	//vetor do MENU
	String classes[] = {"Sensor","Configuração"};
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(
				new ArrayAdapter<String>(MenuInicial.this,
						android.R.layout.simple_expandable_list_item_1,
						classes)//layout interno do android 
		);

		//parametros vindos da tela de Login
		receParamLogin();	
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String classeSelecionada = classes [position];
		//tratamento de acentuação
		if (classeSelecionada =="Configuração")
			classeSelecionada = "Configuracao";
		
		try 
		{	
			Class NossaClasse = Class.forName("com.androidino."+ classeSelecionada);
			Intent  ClasseParaAbrir = new Intent(MenuInicial.this,NossaClasse);
			startActivity(ClasseParaAbrir);
			enviarParametro(ClasseParaAbrir);
			
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		}
		
	}
	
	public void receParamLogin (){
		Intent intecao = getIntent();
		Bundle parametro = intecao.getExtras();
		setUsuario(parametro.getString("usuario"));
		setSenha(parametro.getString("senha"));
		
	}
	
	public void enviarParametro(Intent intencao ){
		Bundle parametro = new Bundle();
		parametro.putString("usuario",getUsuario());
		parametro.putString("senha",getSenha());
		intencao.putExtras(parametro);
		startActivity(intencao);
	}
	
	
}