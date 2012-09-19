/*
Serviço Twitter
*/

#if defined(ARDUINO) && ARDUINO > 18
#endif
#include <Twitter.h>

void enviaTwitter(char sMsg[]){
  String sTolken = lerArquivoSD("twitter.txt");// Lendo Tolken gravado no arquivo twitter.txt
  int iTamVar= sTolken.length();
  char cTolken [iTamVar];

  sTolken.toCharArray(cTolken,iTamVar);//Converte String para Char
  
  Serial.println(cTolken);
  Twitter twitter(cTolken);  

  Serial.println("connecting ...");
  if (twitter.post(sMsg)) {
    int status = twitter.wait(&Serial);
    if (status == 200) {
      Serial.println("OK.");
    } else {
      Serial.print("failed : code ");
      Serial.println(status);
    }
  } else {
    Serial.println("connection failed.");
  }
}

String gravaTolken (String sURL){
  //$dnakjn?GravaTolken
  String sTolken = sURL.substring(sURL.indexOf('$')+1,sURL.indexOf("?"));
  return gravaArquivoSD("twitter.txt",sTolken);
}
