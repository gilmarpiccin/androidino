package com.androidino;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidino.R;


public class CadUsuario extends Activity implements View.OnClickListener{

	private Button btnConfirmar,btnDeletar;
	private EditText edtSenha,edtConSenha,edtUsuario;
	private TextView txtSenha,txtConfSenha,txtUsuario;
	private WebService ws;
	private Mensagem ms;
	private SharedPreferences preferencia;
	
	public void inicializaComponentes(){
		btnConfirmar = (Button) findViewById(R.id.btnCadConfirmar);
		btnConfirmar.setOnClickListener(this);
		btnDeletar = (Button) findViewById(R.id.btnCadDeleta);
		btnDeletar.setOnClickListener(this);
		
		edtUsuario =  (EditText) findViewById(R.id.edtCadUsuario);
		edtUsuario.setFocusable(true);
		edtSenha =  (EditText) findViewById(R.id.edtCadSenha);
		edtSenha.setFocusable(true);
		edtConSenha =  (EditText) findViewById(R.id.edtCadConSenha);
		edtConSenha.setFocusable(true);
		
		txtSenha = (TextView) findViewById(R.id.txtCadSenha);
		txtConfSenha  = (TextView) findViewById(R.id.txtCadConSenha);
		txtUsuario  = (TextView) findViewById(R.id.txtCadUsuario);
		
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnCadConfirmar:
			ms.showToast(validaNovoUser(edtUsuario.getText().toString()
	  									,edtSenha.getText().toString()
	  									,edtConSenha.getText().toString())
						,this);			
			break;
			
		case R.id.btnCadDeleta:
			if ((edtUsuario.getText().toString().equals(""))) {//se for diferente de vazio
				ms.showToast("Usuario preceisa ser informado!", this);
			}
			else
			ms.showToast(ws.delUsuario(edtUsuario.getText().toString()),this);
			
		default:
			break;
		}
		
	}
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caduser);
		preferencia = getSharedPreferences("ConfigServidor",MODE_PRIVATE);
		ms = new Mensagem();
		ws = new WebService(preferencia);
		inicializaComponentes();	
  	}
	
	public String validaNovoUser(String Usuario, String Senha, String ConfSenha){
		String [] carcterInvalido = {"?","&","!","=","$"};
		//retira os espaços
		Senha = Senha.trim();
		ConfSenha = ConfSenha.trim();
		Usuario = Usuario.trim();
		
	
		if (Usuario.equals("")){
			edtUsuario.requestFocus();
			return txtUsuario.getText() + " não pode estar vazio!";
		}
		else if (Senha.equals("")){
			edtSenha.requestFocus();
			return txtSenha.getText() + " não pode estar vazia!";
		}
		else if (ConfSenha.equals("")){
			edtConSenha.requestFocus();
			return txtConfSenha.getText() + " não pode estar vazia!";
		}
		
		//validação dos caracteres 
		for (int i = 0; i < carcterInvalido.length; i++) {
			if (Usuario.contains(carcterInvalido[i])){
				edtUsuario.requestFocus();
				return " Não é permitodo o caractere: "+carcterInvalido[i];
			}
			
			if (Senha.contains(carcterInvalido[i])){
				edtSenha.requestFocus();
				return " Não é permitodo o caractere: "+carcterInvalido[i];
			}
			if (ConfSenha.contains(carcterInvalido[i])){
				edtConSenha.requestFocus();
				return " Não é permitodo os caractere: "+carcterInvalido[i];
			}	
		}
		
		
		//valida se a senha digitada é a mesma da confirmação
		if (!Senha.equals(ConfSenha)){
			edtConSenha.requestFocus();
			return "A " + txtConfSenha.getText() + " está diferente do campo: "+ txtSenha.getText();
		}		

		else if(ws.cadastroUsuario(Usuario,Senha)){
			finish();
			return  "Novo usuário gravado com sucesso!";
		}else
			return "Novo usuário não pode ser gravado.";
	}
}
