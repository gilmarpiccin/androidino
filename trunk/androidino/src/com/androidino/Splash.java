package com.androidino;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity{
	//Variável para receber a musica
	MediaPlayer MinhaMusica;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	
		MinhaMusica = MediaPlayer.create(Splash.this, R.raw.splash);
		MinhaMusica.start();
		
		Thread timer = new Thread(){
	
			//evento que executa midias
			@Override
			public void run() {
				
				try 
				{
					sleep(3000);
				} catch (InterruptedException e) {
					e.getStackTrace();
				}
				finally
				{
					//O que será executado após a execução da midia 
					Intent abreAtividadeInicial = new Intent("android.intent.action.LOGIN");
					startActivity(abreAtividadeInicial);
					
				}
			}
			
			
		};
		timer. start();
	 
	}
	//Evento de Pausa
	@Override
	protected void onPause() {

		super.onPause();
		finish();
		MinhaMusica.release();
		
	}	
}
