/**
 Ethernet
**/
//retorna a porta conf no arquivo no cartão SD
int retornaPorta(){
 int iPorta =8080;
 char cPorta[5] ;
 String sPorta = lerArquivoSD("porta.txt");
 sPorta.toCharArray(cPorta,5);//Converte String para Char

 if (cPorta != "")
   iPorta = atoi(cPorta);//converte char para int
   
 return iPorta;
 }

