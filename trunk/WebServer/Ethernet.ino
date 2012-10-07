/**
  Serviço Ethernet
  Recebe por o resultado da função maintain() que renova o DHCP
**/
 String trocaDHCP(byte byDHCP){
   String sRetorno;
   switch (byDHCP){
   case 0:
     sRetorno = "Nada ocorreu.";
     break;
   case 1:
     sRetorno = "Renovação falhou.";
     break;
   case 2:
     sRetorno = "Renovação com Sucesso.";
     break;
   case 3:
     sRetorno = "Falha ao Realigar.";
     break;
   case 4:
     sRetorno = "Sucesso ao Religar";
     break;
   default:
      sRetorno = "";
   } 
   
   return sRetorno;
 }
 
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
