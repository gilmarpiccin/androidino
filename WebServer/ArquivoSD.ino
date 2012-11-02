/*
ServiÃ§o CartÃ£o SD
 */

#include <SD.h>

void iniciaSD(){
  pinMode(53, OUTPUT);//no Mega porta 53 no outros 10
  if (!SD.begin(4)) 
    Serial.println("Inicialização SD Falhou!");
  else  
    Serial.println("SD Inincializado com exito.");
}

boolean gravaArquivoSD(String sArquivo,String sTexto){
  File flFile;
  iniciaSD();
  char cArquivo [50]="";
  String sDiretorio = sArquivo;
  for (int i=0 ; i< sArquivo.length();i++)
    cArquivo[i] =  sArquivo.charAt(i);
    
  if(sDiretorio.indexOf("/") > 0){
    String sAux ="";
    char cDir[]="";

    //  conta quantos diretorios serÃ£o criados
    while (sDiretorio.indexOf("/") > 0){
      //atribuindo a um auxiliar diretÃ³rio a diretÃ³rio para ser trasformado em char
      sAux = sAux + sDiretorio.substring(0,sDiretorio.indexOf("/")+1);

      //Recortando da barra para frente
      sDiretorio = sDiretorio.substring(sDiretorio.indexOf("/")+1,sDiretorio.length());
    }
    //convertendo para char "apenas os diretÃ³rios"
    sAux.toCharArray(cDir,sAux.length()+1);
    if (!SD.exists(cDir))
      SD.mkdir(cDir);
  }

  SD.remove(cArquivo);//Apaga o arquivo para gravar em cima sempre 1 registro
  flFile = SD.open(cArquivo, FILE_WRITE);//Se nÃ£o existe cria o arqruivo

  if (flFile) {
    Serial.println("Escrevendo no arquivo: ");
    Serial.println(cArquivo);
    flFile.print(sTexto);
    flFile.close();
    Serial.println("Gravado com Sucesso.");
    return true;
  } 
  else {
    Serial.println("Erro ao Abrir: "  );
    Serial.print(cArquivo);
    return false;
  }
}

String lerArquivoSD (String sArquivo){
  iniciaSD();
  char cArquivo [50]="";
  
  for (int i=0 ; i< sArquivo.length();i++)
    cArquivo[i] =  sArquivo.charAt(i);
  Serial.println(sArquivo);      
  if (!SD.exists(cArquivo)){
    Serial.println("não existe arquivo");
    return"";
  }

  Serial.println("\n Lendo Arquivo do SD");
  File flFile = SD.open(cArquivo);
  String sTexto;
  
  if (flFile) {
    while (flFile.available()) {
      char cAux = flFile.read();
      Serial.write(cAux);    
      sTexto.concat(cAux);
    }    
    
    Serial.println("\n Fechando o arquivo:");
    Serial.println(sTexto);
    flFile.close();
  }else{
    Serial.println('Erro ao Abrir' + cArquivo);
    sTexto = "";
  }
  return sTexto;
}

