package com.androidino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.util.Log;

public class WebService {
	 private static final int TIMEOUT_CONEXAO = 10000; // 10 segundos
	    private static final int TIMEOUT_SOCKET = 15000; // 15 segundos
	    private static final int TAM_MAX_BUFFER = 10240; // 10Kbytes
	    private SharedPreferences serverSettings;
	    private String url;
	    private String senha;
	    private String usuario;
	    private String IP;
	    private String porta;
	    
	    public WebService(SharedPreferences preferencias) {
	        atualizaPreferencia(preferencias);
	    }
	    
	    public void atualizaPreferencia(SharedPreferences settings){
	    		serverSettings = settings;
	    		usuario = serverSettings.getString("usuario","");
	    		senha = serverSettings.getString("senha", "");
	    		porta = serverSettings.getString("porta", "");
	    	    IP = serverSettings.getString("ip", "");
	    	    	
	    }
	    
	    public Boolean login(String prUsuario, String prSenha){
	    	this.url = "http://"+IP+":"+porta+"/$"+prUsuario+"&"+prSenha+"?LOGIN";
	    	return validaRquisicao();
	    }
	    
	    public String cadastroUsuario (String prUsuario, String prSenha){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+prUsuario+"!"+prSenha+"=CADUSER";
	    		if (validaRquisicao()) 
	    			return "Usuário Cadastrado com Sucesso!";
	    		else
	    			return "Usuário não foi Cadastrado!";	    	
	    }
	    
	    public Boolean redefineSenha(String prSenha){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+usuario+"!"+prSenha+"=CADUSER";  	
	    		return validaRquisicao();
	    }
	    
	    public String token(String sTolken){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+sTolken+"#TOKEN";
	    	return getRequisicao();
	    }
	    
	    public boolean sensorDigital(String prSensor){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?"+prSensor;
	    	return validaRquisicao();
	    }
	    
	    public String Sensor(){
	    	this.url = "http://"+IP+":"+porta+"/$"+usuario+"&"+senha+"?SENSOR";
	    	String retorno = getRequisicao();
	    	retorno = retorno.replaceAll("1","true");
	    	retorno = retorno.replaceAll("0","false");
	    	return retorno;
	    }
	    
	    
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
	    
	    boolean validaRquisicao(){
	    	if (getRequisicao().equals("1")){
	    		return true;
	    	}else
	    		return false ; 
	    }
	    
	
}
