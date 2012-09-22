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

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

public class WebService {
	 private static final int TIMEOUT_CONEXAO = 20000; // 20 segundos
	    private static final int TIMEOUT_SOCKET = 30000; // 30 segundos
	    private static final int TAM_MAX_BUFFER = 10240; // 10Kbytes
	    private String url;
	    
	    public WebService(String url) {
	        this.url = url;
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
	        
	        return parserbuilder;    
	    }
	    
		public void msgEspera(String msg, Activity tela){
			ProgressDialog dialog = ProgressDialog.show(tela, "", msg, true);
			}
	
}
