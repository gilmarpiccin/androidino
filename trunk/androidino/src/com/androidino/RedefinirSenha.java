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
	private Mensagem ms;
	private WebService ws;
	private SharedPreferences preferencia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.redefinirsenha);
		preferencia = getSharedPreferences("ConfigSevidor",MODE_PRIVATE);
		ms = new Mensagem();
		ws = new WebService(preferencia);
		inicializaComponentes();
		/*
		Intent intencao = getIntent();
		vl.receParamLogin(intencao);*/		
  	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnValidarSenha:
			ms.showToast( validaSenha(edtSenhAtual.getText().toString()
						  ,edtSenhaNova.getText().toString()
						  ,edtconfSenha.getText().toString())
						,this);  
			break;

		default:
			break;
		}
		
	}
 
	public String validaSenha (String SenhaAtual, String SenhaNova, String confSenha){

		//retira os espaços
		SenhaAtual = SenhaAtual.trim();
		SenhaNova = SenhaNova.trim();
		confSenha = confSenha.trim();
		
		//verifica se estão vazio
		if (SenhaAtual.equals("")){
			edtSenhAtual.requestFocus();
			return txtSenhaAtual.getText() + " não pode estar vazia!";
		}
		else if (SenhaNova.equals("")){
			edtSenhaNova.requestFocus();
			return txtSenhaNova.getText() + " não pode estar vazia!";
		}
		else if (confSenha.equals("")){
			edtconfSenha.requestFocus();
			return txtconfSenha.getText() + " não pode estar vazia!";
		}
		
		else if (SenhaAtual.equals(SenhaNova)) {
			return txtSenhaNova.getText() + " não pode ser igual a "+txtSenhaAtual.getText()+"!";
		}
		
		//valida se a senha digitada é a mesma da confirmação
		else if (!SenhaNova.equals(confSenha)){
			edtSenhaNova.requestFocus();
			return " A Senha Nova está diferente \n da senha de confirmação.";
		}
			
		//Valida se a senha atual esta errada
		else if (!ws.login(preferencia.getString("usuario",""),edtSenhAtual.getText().toString())) {
			edtSenhAtual.requestFocus();
			return txtSenhaAtual.getText() + " está errada.";
		}
		
		//redefine a senha
		else if(ws.redefineSenha(SenhaNova)){
			//grava no arquivo de controle o usuario logado e a nova senha
			ms.gravaCofUsuario(preferencia.getString("usuario",""),SenhaNova,preferencia);
			finish();
			return  "Nova Senha gravada com sucesso!";
		}else
			return "Nova Senha Não foi gravada.";
	}
	
	public void inicializaComponentes(){
		btnValidaSenha = (Button) findViewById(R.id.btnValidarSenha);
		btnValidaSenha.setOnClickListener(this);
		
		edtSenhAtual =  (EditText) findViewById(R.id.edtSenhaAtual);
		edtSenhAtual.setFocusable(true);
		edtSenhaNova =  (EditText) findViewById(R.id.edtSenhaNova);
		edtSenhaNova.setFocusable(true);
		edtconfSenha =  (EditText) findViewById(R.id.edtConfirmarSenha);
		
		txtSenhaAtual = (TextView) findViewById(R.id.txtSenhaAtual);
		txtSenhaNova  = (TextView) findViewById(R.id.txtSenhaNova);
		txtconfSenha  = (TextView) findViewById(R.id.txtConfirmarSenha);
		
	}
}
