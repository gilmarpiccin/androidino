/*
Serviço Login
 */

//valida usuário e senha da URL da requisiçao
boolean validaLogin(String sAndroid){

  //Valida os caracteres obrigattórrios
  if ( (sAndroid.indexOf('$') > 0) && (sAndroid.indexOf('&') > 0) && (sAndroid.indexOf('?') > 0) ){

    //procura dentro do SD um arquivo com o nome do usuario
    String sArduino = lerArquivoSD(sAndroid.substring(sAndroid.indexOf('$')+1,sAndroid.indexOf('&')) + ".txt");

  //valida se a senha e o usuário enviado é igual ao armazenado no SD
    if (sAndroid.substring(sAndroid.indexOf('$')+1,sAndroid.indexOf('&')) == sArduino.substring(0,sArduino.indexOf(';')) && 
	sAndroid.substring(sAndroid.indexOf('&')+1,sAndroid.indexOf("?")) == sArduino.substring(sArduino.indexOf(';')+1,sArduino.length())){
      Serial.println("True");
      return true;
    }
    else{//se for diferente retorno negativo
      Serial.println("False");
      return false;
    }
  }
  else{
    return false;
  }
}

boolean cadUsuario(String sURL){

  if ( (sURL.indexOf('?') > 0) && (sURL.indexOf('=') > 0) && (sURL.indexOf('!') > 0) ){
	return gravaArquivoSD(sURL.substring(sURL.indexOf('?')+1,sURL.indexOf('!')) + '.txt'
			     ,sURL.substring(sURL.indexOf('?')+1,sURL.indexOf('!')) + ";" + sURL.substring(sURL.indexOf('!')+1,sURL.indexOf('=')));
  }else
  return false;
}

boolean delUsuario(String sURL){

  if ( (sURL.indexOf('?') > 0) && (sURL.indexOf('=') > 0) ){
    return deletaArquivoSD(sURL.substring(sURL.indexOf('?')+1,sURL.indexOf('='))+".txt");
  }else
  return false;
}
