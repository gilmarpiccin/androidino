/*
Serviço Twitter
*/


void enviaTwitter(char sMsg[]){
/*  char cToken [150];
  lerArquivoSD("twitter.txt").toCharArray(cToken,150);//Le o Aquivo e Converte String para Char

  Serial.println(cToken);
  Twitter twitter(cToken);  */
  Twitter twitter("63860592-pOWE5ipMXcLyV8077glcdTe7oWAXMJjZoC7PjZz6l");  
  char cAux[20];
  Tempo().toCharArray(cAux,20);//Converte Strinf para Char
  Serial.println(strcat(sMsg,cAux));
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

//grava token do twitter no SD
boolean gravaToken (String sURL){
  //$dnakjn?GravaTolken
  String sToken = sURL.substring(sURL.indexOf('?')+1,sURL.indexOf("="));
  return gravaArquivoSD("twitter.txt",sToken);
}

