/*
Serviço Cartão SD
*/

#include <SD.h>

void iniciaSD(){
  pinMode(53, OUTPUT);//no Mega porta 53 no outros 10
  if (!SD.begin(4)) 
    Serial.println("Inicialização SD Falhou!");
  else  
    Serial.println("SD Inincializado com exito.");
}

String gravaArquivoSD(char cArquivo[],String sTexto){
  File flFile;
  iniciaSD();
  if (!SD.exists(cArquivo)){
    Serial.println("Não existe arquivo.");
    return"Não Existe Arquivo.";
  }
  flFile = SD.open(cArquivo, FILE_WRITE);
  
  if (flFile) {
    Serial.print("Escrevendo no arquivo: ");
    Serial.print(cArquivo);
    flFile.print(sTexto);
    flFile.close();
    Serial.println("Gravado com Sucesso.");
    return "Garavado com Sucesso.";
  } else {
    Serial.println("Erro ao Abrir: "  );
    Serial.print(cArquivo);
    return "Erro ao Abrir.";
  }
}

String lerArquivoSD (char cArquivo[]){
  iniciaSD();
  if (!SD.exists(cArquivo)){
    Serial.println("não existe arquivo");
    return"";
  }
  
  Serial.println("Lendo Arquivo do SD");
  File flFile = SD.open(cArquivo);
  String sTexto;
  if (flFile) {
    while (flFile.available()) {
      char cAux = flFile.read();
      Serial.write(cAux);    
      sTexto.concat(cAux);
    }    
    Serial.println("\n Fechando o arquivo");
    Serial.println(sTexto);
    flFile.close();
  } else {
    Serial.println('Erro ao Abrir' + cArquivo);
    sTexto = "";
  }
  return sTexto;
}

