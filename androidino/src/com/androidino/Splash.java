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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	
		MinhaMusica = MediaPlayer.create(Splash.this, R.raw.splash);//onde está a musica
		MinhaMusica.start();// executa a musica
		//Thread criada para executar a musica e carregar a aplicação ao mesmo tempo
		Thread timer = new Thread(){
			//Implementa o que a thead irá fazer.
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
					//Abre a tela login através de uma intenção criada no arquivo manifesto 
					Intent abreAtividadeInicial = new Intent("android.intent.action.LOGIN");
					startActivity(abreAtividadeInicial);
					
				}
			}
		};
		timer. start();// executa a thread
	 
	}

	@Override
	protected void onPause() {
		super.onPause();
		//fecha o tela 
		finish();
		//libera a musica da memória
		MinhaMusica.release();
	}	
}
