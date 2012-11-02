package com.androidino;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuInicial extends ListActivity {
	//vetor do MENU
	String classes[] = {"Sensor","Cad. Usuário","Configurações"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(
				new ArrayAdapter<String>(MenuInicial.this,
						android.R.layout.simple_expandable_list_item_1,
						classes)//layout interno do android 
		);
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String classeSelecionada = classes [position];

		//Tratamento para chamar a classe correta
		if (classeSelecionada =="Configurações")
			classeSelecionada = "MenuConfiguracao";
		else if (classeSelecionada == "Cad. Usuário")
			classeSelecionada = "CadUsuario";
		try 
		{	
			Class NossaClasse = Class.forName("com.androidino."+ classeSelecionada);
			Intent  ClasseParaAbrir = new Intent(MenuInicial.this,NossaClasse);
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