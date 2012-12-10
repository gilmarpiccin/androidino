/*
Serviço Login
 */

//valida usuário e senha da URL da requisiçao
boolean validaLogin(String sAndroid){
  String sAdUsuario,sAdSenha,sArUsuario,sArSenha,sArduino;

  //Valida os caracteres obrigattórrios
  if ( (sAndroid.indexOf('$') > 0) && (sAndroid.indexOf('&') > 0) && (sAndroid.indexOf('?') > 0) ){

    //Atribui usuário e senha para variáveis
    sAdUsuario = sAndroid.substring(sAndroid.indexOf('$')+1,sAndroid.indexOf('&'));
    Serial.println("\nlendo Usuario Android:");
    Serial.println(sAdUsuario);
    
    sAdSenha = sAndroid.substring(sAndroid.indexOf('&')+1,sAndroid.indexOf("?"));
    Serial.println("lendo senha Android:");
    Serial.println(sAdSenha);  

    //procura dentro do SD um arquivo com o nome do usuario
    sArduino = lerArquivoSD(sAdUsuario + ".txt");

    // separa o usuário e a senha do arquivo SD
    sArUsuario = sArduino.substring(0,sArduino.indexOf(';'));
    Serial.println("\nlendo Usuario Arduino:");
    Serial.println(sArUsuario);
    
    sArSenha = sArduino.substring(sArduino.indexOf(';')+1,sArduino.length());
    Serial.println("lendo senha Arduino:");
    Serial.println(sArSenha);

  //valida se a senha e o usuário enviado é igual ao armazenado no SD
    if ((sAdUsuario == sArUsuario) && (sAdSenha == sArSenha)){
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
    String sUsuario = sURL.substring(sURL.indexOf('?')+1,sURL.indexOf('!'));
    String sSenha = sURL.substring(sURL.indexOf('!')+1,sURL.indexOf('='));
    String sArquivo = sUsuario;
    sArquivo.concat(".txt");
    return gravaArquivoSD(sArquivo,sUsuario + ";" + sSenha);
  }else
  return false;
}

boolean delUsuario(String sURL){

  if ( (sURL.indexOf('?') > 0) && (sURL.indexOf('=') > 0) ){
    String sUsuario = sURL.substring(sURL.indexOf('?')+1,sURL.indexOf('='));
    String sArquivo = sUsuario;
    sArquivo.concat(".txt");
    return deletaArquivoSD(sArquivo);
  }else
  return false;
}
