/*
Serviço Login
*/

String validaLogin(String sAndroid, String sArduino){
  String sAdUsuario,sAdSenha,sArUsuario,sArSenha;

  sAdUsuario = sAndroid.substring(sAndroid.indexOf('$')+1,sAndroid.indexOf('&'));
  Serial.println("\nlendo Usuario Android:");
  Serial.println(sAdUsuario);
  sAdSenha = sAndroid.substring(sAndroid.indexOf('&')+1,sAndroid.indexOf("?"));
  Serial.println("lendo senha Android:");
  Serial.println(sAdSenha);
  
  sArUsuario = sArduino.substring(0,sArduino.indexOf(';'));
  Serial.println("\nlendo Usuario Arduino:");
  Serial.println(sArUsuario);
  sArSenha = sArduino.substring(sArduino.indexOf(';')+1,sArduino.length());
  Serial.println("lendo senha Arduino:");
  Serial.println(sArSenha);

  if ((sAdUsuario == sArUsuario) && (sAdSenha == sArSenha)){
    Serial.println("True");
    return "True";
  }else{
    Serial.println("False");
    return "False";
  }
}
