package com.androidino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RedefinirSenha extends Activity implements View.OnClickListener{

	private Button btnValidaSenha;
	private EditText edtSenhAtual,edtSenhaNova,edtconfSenha;
	private Mensagem ms;
	private SharedPreferences preferencia;
	private TextView txtSenhaAtual,txtSenhaNova,txtconfSenha;
	private WebService ws;
	
	public void inicializaComponentes(){
		btnValidaSenha = (Button) findViewById(R.id.btnRedConfirmar);
		btnValidaSenha.setOnClickListener(this);
		
		edtSenhAtual =  (EditText) findViewById(R.id.edtRedSenhaAtual);
		edtSenhAtual.setFocusable(true);
		edtSenhaNova =  (EditText) findViewById(R.id.edtRedSenhaNova);
		edtSenhaNova.setFocusable(true);
		edtconfSenha =  (EditText) findViewById(R.id.edtRedConfirmar);
		edtconfSenha.setFocusable(true);
		
		txtSenhaAtual = (TextView) findViewById(R.id.txtRedSenhaAtual);
		txtSenhaNova  = (TextView) findViewById(R.id.txtRedSenhaNova);
		txtconfSenha  = (TextView) findViewById(R.id.txtRedConfirmar);
		
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnRedConfirmar:
			ms.showToast( validaSenha(edtSenhAtual.getText().toString()
						  ,edtSenhaNova.getText().toString()
						  ,edtconfSenha.getText().toString())
						,this);  
			break;

		default:
			break;
		}
		
	}
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.redefinirsenha);
		preferencia = getSharedPreferences("ConfigServidor",MODE_PRIVATE);
		ms = new Mensagem();
		ws = new WebService(preferencia);
		inicializaComponentes();
	
  	}
	
	public String validaSenha (String SenhaAtual, String SenhaNova, String confSenha){
		String [] carcterInvalido = {"?","&","!","=","$"};
		//retira os espa�os
		SenhaAtual = SenhaAtual.trim();
		SenhaNova = SenhaNova.trim();
		confSenha = confSenha.trim();
		
		//verifica se est�o vazio
		if (SenhaAtual.equals("")){
			edtSenhAtual.requestFocus();
			return txtSenhaAtual.getText() + " n�o pode estar vazia!";
		}
		else if (SenhaNova.equals("")){
			edtSenhaNova.requestFocus();
			return txtSenhaNova.getText() + " n�o pode estar vazia!";
		}
		else if (confSenha.equals("")){
			edtconfSenha.requestFocus();
			return txtconfSenha.getText() + " n�o pode estar vazia!";
		}
		
		for (int i = 0; i < carcterInvalido.length; i++) {
			if (SenhaAtual.contains(carcterInvalido[i])){
				edtSenhAtual.requestFocus();
				return " N�o � permitodo o caractere: "+carcterInvalido[i];
			}
			else if (SenhaNova.contains(carcterInvalido[i])){
				edtSenhaNova.requestFocus();
				return " N�o � permitodo o caracter: "+carcterInvalido[i];
			}
			else if (confSenha.contains(carcterInvalido[i])){
				edtconfSenha.requestFocus();
				return " N�o � permitodo o caracteres: "+carcterInvalido[i];
			}	
		}
		
		if (SenhaAtual.equals(SenhaNova)) {
			return txtSenhaNova.getText() + " n�o pode ser igual a "+txtSenhaAtual.getText()+"!";
		}
		
		//valida se a senha digitada � a mesma da confirma��o
		else if (!SenhaNova.equals(confSenha)){
			edtSenhaNova.requestFocus();
			return " A Senha Nova est� diferente \n da senha de confirma��o.";
		}
			
		//Valida se a senha atual esta errada
		else if (!ws.login(preferencia.getString("usuario",""),edtSenhAtual.getText().toString())) {
			edtSenhAtual.requestFocus();
			return txtSenhaAtual.getText() + " est� errada.";
		}
		
		//redefine a senha
		else if(ws.redefineSenha(SenhaNova)){
			//grava no arquivo de controle o usuario logado e a nova senha
			ms.gravaCofUsuario(preferencia.getString("usuario",""),SenhaNova,preferencia);
			finish();
			return  "Nova Senha gravada com sucesso!";
		}else
			return "Nova Senha N�o foi gravada.";
	}
}
