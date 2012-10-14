package com.androidino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RedefinirSenha extends Activity implements View.OnClickListener{

	private Button btnValidaSenha;
	private EditText edtSenhAtual,edtSenhaNova,edtconfSenha;
	private TextView txtSenhaAtual,txtSenhaNova,txtconfSenha;
	private Mensagem vl;
	private WebService ws;
	private SharedPreferences preferencia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.redefinirsenha);
		preferencia = PreferenceManager.getDefaultSharedPreferences(this);
		vl = new Mensagem();
		ws = new WebService(preferencia);
		inicializaComponentes();
		/*
		Intent intencao = getIntent();
		vl.receParamLogin(intencao);*/		
  	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnValidarSenha:
			vl.showToast( validaSenha(edtSenhAtual.getText().toString()
						  ,edtSenhaNova.getText().toString()
						  ,edtconfSenha.getText().toString())
						,this);  
			break;

		default:
			break;
		}
		
	}
 
	public String validaSenha (String SenhaAtual, String SenhaNova, String confSenha){
		String sRetorno ="";
		//retira os espaços
		SenhaAtual = SenhaAtual.trim();
		SenhaNova = SenhaNova.trim();
		confSenha = confSenha.trim();
		//verifica se estão vazio
		if (SenhaAtual.equals("")) 
			return txtSenhaAtual.getText() + " não pode estar vazia!";
		else if (SenhaAtual.equals(""))
			return txtSenhaNova.getText() + " não pode estar vazia!";
		else if (confSenha.equals(""))
			return txtconfSenha.getText() + " não pode estar vazia!";
		
		//Seta a Senha atual digitada para verificar se é igual a cadastrada atravez do service ?LOGIN 
		/*WebService ws = new WebService("http://192.168.1.177:8080/$"+
									   vl.getUsuario() +
									   "&" + SenhaAtual +
									   "?LOGIN");
		//Envia para o Web service a requisição setada que ira retornar um Status neste caso 'true' ou 'false' 
		String sValidacao = ws.getRequisicao();
		//Retira a quebra de linha
		sValidacao = sValidacao.replaceAll("\n","");
		//verifica se o retorno do web service foi verdadeiro*/
		if (ws.login()) {
			
			if (SenhaNova == confSenha){
				//seta a nova senha para o Service ?REDESENHA
				/*WebService ws2 = new WebService("http://192.168.1.177:8080/$"+
												 vl.getUsuario() +
												 "&"+ SenhaNova +
												 "?REDESENHA");
				//Envia a nova senha setada e o web service que irá retornar se foi redefinda ou não
				sRetorno = ws2.getRequisicao();*/
				sRetorno = ws.redefineSenha(SenhaNova);
			}else{
				edtSenhaNova.requestFocus();
				txtSenhaNova.setBackgroundColor(R.color.Vermelho);
				txtconfSenha.setBackgroundColor(R.color.Vermelho);
				sRetorno = " A Senha Nova está diferente \n da senha de confirmação.";
			}
			
		}else{
			edtSenhAtual.requestFocus();
			sRetorno = txtSenhaAtual.getText() + " está errada.";
		}
		
		return sRetorno;
	}
	
	public void inicializaComponentes(){
		btnValidaSenha = (Button) findViewById(R.id.btnValidarSenha);
		btnValidaSenha.setOnClickListener(this);
		
		edtSenhAtual =  (EditText) findViewById(R.id.edtSenhaAtual);
		edtSenhaNova =  (EditText) findViewById(R.id.edtSenhaNova);
		edtconfSenha =  (EditText) findViewById(R.id.edtConfirmarSenha);
		
		txtSenhaAtual = (TextView) findViewById(R.id.txtSenhaAtual);
		txtSenhaNova  = (TextView) findViewById(R.id.txtSenhaNova);
		txtconfSenha  = (TextView) findViewById(R.id.txtConfirmarSenha);
		
	}
}
