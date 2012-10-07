package com.androidino;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuConfiguracao extends ListActivity {
		//Setando o que ir� apararecer no menu.
		private String classes[] = {"Redefinir Senha","Twitter","Comunica��o"};
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//Cria um menu de lista
			setListAdapter(	new ArrayAdapter<String>(MenuConfiguracao.this,
							android.R.layout.simple_expandable_list_item_1,
							classes) 
			);
		}
		
		//a��o da lista (clique)
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			//recebe o nome da classe da posi��o clicada 
			String classeSelecionada = classes [position];

			//Tratamento para chamar a classe correta  
			if (classeSelecionada =="Redefinir Senha" )
				classeSelecionada = "RedefinirSenha";	
			else if (classeSelecionada == "Comunica��o")
				classeSelecionada = "Comunicacao";
			
			try 
			{	
				//referenciando o nome selecionado a classe existente
				Class NossaClasse = Class.forName("com.androidino."+ classeSelecionada);
				
				Intent  ClasseParaAbrir = new Intent(MenuConfiguracao.this,NossaClasse);
				startActivity(ClasseParaAbrir);
				
			} catch (ClassNotFoundException  e) {
				e.printStackTrace();
			}
			
		}

}
