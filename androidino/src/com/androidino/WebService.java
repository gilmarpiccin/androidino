package com.androidino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.SharedPreferences;
import android.util.Log;

public class WebService {
	 private static final int TAM_MAX_BUFFER = 10240; // 10Kbytes
	    private static final int TIMEOUT_CONEXAO = 10000; // 10 segundos
	    private static final int TIMEOUT_SOCKET = 15000; // 15 segundos
	    private String IP;
	    private String porta;
	    private String senha;
	    private SharedPreferences serverSettings;
	    private String url;
	    private String usuario;

	    public WebService(SharedPreferences preferencias) {
	        atualizaPreferencia(preferencias);
	    }

	    //Atualiza os atributos com as configurações do arquivo de preferencia
	    public void atualizaPreferencia(SharedPreferences settings){
	    		serverSettings = settings;
	    		usuario = serverSettings.getString("usuario","");
	    		senha = serverSettings.getString("senha", "");
	    		porta = serverSettings.getString("porta", "");
	    	    IP = serverSettings.getString("ip", "");
	    }
	    
	    //Envia o novo usuário do para o Alarme
	    public boolean cadastroUsuario (String prUsuario, String prSenha){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+prUsuario+"!"+prSenha+"=CADUSER";
	    		return trocaRetorno();  	
	    }
	    
	    //Solicita ao sensor de Temperatura DHT a Umidade e temperatura do ambiente 
	    public String DHT(){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?DHT";
	    	return getRequisicao();
	    }
	    
	    //função panico
	    public String PANICO(){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?PANICO";
	    	return getRequisicao();
	    }
	    
	    //deleta o usuario
	    public String delUsuario (String prUsuario){
	    	if (prUsuario.equals(usuario)) {
				return "Não pode excluir o usuario logado!";
	    	}

	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+prUsuario+"=DELETA";
	    	if (trocaRetorno()) {
	    		return "Usuario excluido com sucesso!";
	    	}else
	    		return "Usuário não encontrado ou já excluido!" ;  	
	    }
	    
	    //Envia a requisção para o Alarme
	    public String getRequisicao(){
	        String parserbuilder = "";
	        
	        try{
	            HttpParams httpParameters = new BasicHttpParams();
	            
	            // Configura o timeout da conexão em milisegundos até que a conexão
	            // seja estabelecida
	            HttpConnectionParams.setConnectionTimeout(httpParameters, 
	                    TIMEOUT_CONEXAO);
	            
	            // Configura o timeout do socket em milisegundos do tempo 
	            // que será utilizado para aguardar os dados
	            HttpConnectionParams.setSoTimeout(httpParameters, 
	                    TIMEOUT_SOCKET);   
	            
	            HttpClient httpclient = new DefaultHttpClient(httpParameters);
	            HttpPost httppost = new HttpPost(url);
	    
	            HttpResponse response = httpclient.execute(httppost);
	            
	            BufferedReader reader = new BufferedReader(
	                    new InputStreamReader(response.getEntity().getContent()), TAM_MAX_BUFFER);
	            
	            StringBuilder builder = new StringBuilder();
	            
	            for (String line = null ; (line = reader.readLine())!= null;) {
	                builder.append(line).append("\n");
	            }
	            
	            parserbuilder = builder.toString();
	            
	        }catch(ClientProtocolException e){
	            Log.e("WebService", e.toString());
	        }
	        catch(IOException e){
	            Log.e("WebService", e.toString());
	        }
	        
	        parserbuilder = parserbuilder.replaceAll("\n", "");
	        return parserbuilder;    
	    }
	    
	    //Solicita o Login para o Alarme
	    public Boolean login(String prUsuario, String prSenha){
	    	this.url = "http://"+IP+":"+porta+"/$"+prUsuario+"&"+prSenha+"?LOGIN";
	    	return trocaRetorno();
	    }
	    
	    //Envia nova senha
	    public Boolean redefineSenha(String prSenha){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+usuario+"!"+prSenha+"=CADUSER";  	
	    		return trocaRetorno();
	    }
	    
	    //Solicita o Status dos Sensores do alarme retorno é uma string separando sensores por ";"
	    public String Sensor(){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?SENSOR";
	    	String retorno = getRequisicao();
	    	//Troca o status 1 ou 0 dos sensores por True ou False
	    	retorno = retorno.replaceAll("1","true");
	    	retorno = retorno.replaceAll("0","false");
	    	return retorno;
	    }
	    
	    //Envia comando para ativar/inativar sensor do alarme
	    public boolean sensorDigital(String prSensor){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+prSensor;
	    	return trocaRetorno();
	    }
	    
	    //Envia o Token do Twiter para o Alarme
	    public String token(String sTolken){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+sTolken+"=TOKEN";
	    	return getRequisicao();
	    }

	    //Valida o retorno do alarme se for 1 é True se 0 é false
	    boolean trocaRetorno(){
	    	if (getRequisicao().equals("1")){
	    		return true;
	    	}else
	    		return false ; 
	    }
	    
	
}
