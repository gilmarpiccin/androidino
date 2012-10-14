/*
Serviço Twitter
*/

#if defined(ARDUINO) && ARDUINO > 18
#endif
#include <Twitter.h>

void enviaTwitter(char sMsg[]){
  String sToken = lerArquivoSD("twitter.txt");// Lendo Tolken gravado no arquivo twitter.txt
  int iTamVar= sToken.length();
  char cToken [iTamVar];

  sToken.toCharArray(cToken,iTamVar);//Converte String para Char
  
  Serial.println(cToken);
  Twitter twitter(cToken);  

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

String gravaToken (String sURL){
  //$dnakjn?GravaTolken
  String sToken = sURL.substring(sURL.indexOf('?')+1,sURL.indexOf("#"));
  return gravaArquivoSD("twitter.txt",sToken);
}