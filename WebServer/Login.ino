/*
Serviço Login
 */

String validaLogin(String sAndroid){
  String sAdUsuario,sAdSenha,sArUsuario,sArSenha,sArduino;
  //Valida os caracteres obrigatÃ³rios
  if ( (sAndroid.indexOf('$') > 0) && (sAndroid.indexOf('&') > 0) && (sAndroid.indexOf('?') > 0) ){

    sAdUsuario = sAndroid.substring(sAndroid.indexOf('$')+1,sAndroid.indexOf('&'));
    Serial.println("\nlendo Usuario Android:");
    Serial.println(sAdUsuario);
    sAdSenha = sAndroid.substring(sAndroid.indexOf('&')+1,sAndroid.indexOf("?"));
    Serial.println("lendo senha Android:");
    Serial.println(sAdSenha);  

    //procura dentro do diretorio User o arquivo com o nome do usuario
    sArduino = lerArquivoSD("USER/"+ sAdUsuario + "/" +sAdUsuario + ".txt");

    sArUsuario = sArduino.substring(0,sArduino.indexOf(';'));
    Serial.println("\nlendo Usuario Arduino:");
    Serial.println(sArUsuario);
    sArSenha = sArduino.substring(sArduino.indexOf(';')+1,sArduino.length());
    Serial.println("lendo senha Arduino:");
    Serial.println(sArSenha);

    if ((sAdUsuario == sArUsuario) && (sAdSenha == sArSenha)){
      Serial.println("True");
      return "True";
    }
    else{
      Serial.println("False");
      return "False";
    }
  }
  else{
    return "False";
  }
}

String redefineSenha(String sURL){
  String sUsuario = sURL.substring(sURL.indexOf('$')+1,sURL.indexOf('&'));
  String sNovaSenha = sURL.substring(sURL.indexOf('&')+1,sURL.indexOf('?'));
  //  grava a nova senha concatennado o usuario;senha
  String sArquivo = sUsuario;
  sArquivo.concat(".txt");
  return gravaArquivoSD(sArquivo,sUsuario + ";" + sNovaSenha);

}


String cadUsuario(String sURL){

  if ( (sURL.indexOf('?') > 0) && (sURL.indexOf('=') > 0) && (sURL.indexOf('!') > 0) ){
    String sUsuario = sURL.substring(sURL.indexOf('?')+1,sURL.indexOf('!'));
    String sSenha = sURL.substring(sURL.indexOf('!')+1,sURL.indexOf('='));
    String sArquivo = sUsuario;
    sArquivo.concat(".txt");
    return gravaArquivoSD("USER/" + sUsuario + "/" + sArquivo + "/",sUsuario + ";" + sSenha);
  }else
  return "caracter inválido";
}

