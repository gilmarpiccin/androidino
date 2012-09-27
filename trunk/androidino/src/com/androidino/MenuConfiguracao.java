package com.androidino;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuConfiguracao extends ListActivity {
	//vetor do MENU
		String classes[] = {"Redefinir Senha","Twitter","Comunicação"};
		
		Mensagem vl = new Mensagem();
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setListAdapter(	new ArrayAdapter<String>(MenuConfiguracao.this,
							android.R.layout.simple_expandable_list_item_1,
							classes)//layout interno do android 
			);
		}
		
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			String classeSelecionada = classes [position];
						
			if (classeSelecionada =="Redefinir Senha" )
				classeSelecionada = "RedefinirSenha";	
			else if (classeSelecionada == "Comunicação")
				classeSelecionada = "Comunicacao";
			
			try 
			{	
				Class NossaClasse = Class.forName("com.androidino."+ classeSelecionada);
				Intent  ClasseParaAbrir = new Intent(MenuConfiguracao.this,NossaClasse);
				startActivity(ClasseParaAbrir);
				
			} catch (ClassNotFoundException  e) {
				e.printStackTrace();
			}
			
		}

}
