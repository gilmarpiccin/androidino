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
		
		//Cria��o da classe
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			//Cria uma lista (menu)
			setListAdapter(	new ArrayAdapter<String>(MenuConfiguracao.this,
							android.R.layout.simple_expandable_list_item_1,
							classes) 
			);
		}
		
		//Click das op��es de menu
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			//recebe o nome da classe da posi��o clicada 
			String classeSelecionada = classes [position];

			//Tratamento para chamar a classe correta retirando acentua��o  
			if (classeSelecionada =="Redefinir Senha" )
				classeSelecionada = "RedefinirSenha";	
			else if (classeSelecionada == "Comunica��o")
				classeSelecionada = "Comunicacao";
			
			try 
			{	
				//Referenciando o nome selecionado a classe existente no projeto
				Class NossaClasse = Class.forName("com.androidino."+ classeSelecionada);
				
				Intent  ClasseParaAbrir = new Intent(MenuConfiguracao.this,NossaClasse);
				startActivity(ClasseParaAbrir);
				
			} catch (ClassNotFoundException  e) {
				e.printStackTrace();
			}
		}

}
