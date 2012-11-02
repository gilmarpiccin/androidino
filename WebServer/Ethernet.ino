/**
  ServiÃ§o Ethernet
  Recebe por o resultado da funÃ§Ã£o maintain() que renova o DHCP
**/

int retornaPorta(){
 int iPorta =8080;
 char cPorta[5] ;
 String sPorta = lerArquivoSD("porta.txt");
 
 for (int i=0 ; i< sPorta.length();i++)
  cPorta[i] =  sPorta.charAt(i);

 if (cPorta != "")
   iPorta = atoi(cPorta);//converte char para int
   
 return iPorta;
 }

