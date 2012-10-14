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

String gravaArquivoSD(String sArquivo,String sTexto){
  File flFile;
  iniciaSD();
  char cArquivo [50];
  for (int i=0 ; i< sArquivo.length();i++)
    cArquivo[i] =  sArquivo.charAt(i);
    
  SD.remove(cArquivo);//Apaga o arquivo para gravar em cima sempre 1 registro
  flFile = SD.open(cArquivo, FILE_WRITE);//Se não existe cria o arqruivo
  
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

String lerArquivoSD (String sArquivo){
  iniciaSD();
  char cArquivo [50];
  for (int i=0 ; i< sArquivo.length();i++)
    cArquivo[i] =  sArquivo.charAt(i);
    
  if (!SD.exists(cArquivo)){
    Serial.println("não existe arquivo");
    return"";
  }
  Serial.println(sArquivo);  
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

