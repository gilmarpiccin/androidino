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
	Validacao vl = new Validacao();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(
				new ArrayAdapter<String>(MenuInicial.this,
						android.R.layout.simple_expandable_list_item_1,
						classes)//layout interno do android 
		);

		//parametros vindos da tela de Login
		Intent intencao = getIntent();
		vl.receParamLogin(intencao);
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String classeSelecionada = classes [position];
		//tratamento de acentuação
		if (classeSelecionada =="Configuração")
			classeSelecionada = "MenuConfiguracao";
		
		try 
		{	
			Class NossaClasse = Class.forName("com.androidino."+ classeSelecionada);
			Intent  ClasseParaAbrir = new Intent(MenuInicial.this,NossaClasse);
			vl.enviarParametro(ClasseParaAbrir);
			startActivity(ClasseParaAbrir);
			
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}
}