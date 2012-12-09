package com.androidino;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuInicial extends ListActivity {
	//vetor do MENU
	String classes[] = {"Sensor","Cad. Usuário","Configurações"};
	private SharedPreferences preferencia;
	private WebService ws;
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(
				new ArrayAdapter<String>(MenuInicial.this,
						android.R.layout.simple_expandable_list_item_1,
						classes)//layout interno do android 
		);
		preferencia = getSharedPreferences("ConfigServidor",MODE_PRIVATE);
		ws = new WebService(preferencia);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_panico, menu);
		return true;
	}

	//Clique do Menu que fica escondido
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.menu_panico:
				ws.PANICO();
			}
			return super.onOptionsItemSelected(item);
		}
}