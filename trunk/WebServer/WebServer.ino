#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };// MAC do Arduino
IPAddress ip(192,168,1,177);// IP FIXO
boolean bpresenca,btemperatura,bfogo;

EthernetServer server(8080);
void setup() {
  Serial.begin(9600);//Inicia a comunicação Serial
  while (!Serial) {
    ; //Fica no laço enquanto não estabelece a comunicação Serial
  }

//  if (!Ethernet.begin(mac))//Usar o DHCP para procurar IP
//  {
    Serial.println("Requisição de DHCP falhou.");
    Ethernet.begin(mac,ip);
//  }
  server.begin();//Iniciando o servidor
  Serial.print("Servidor localizando em: ");
  Serial.println(Ethernet.localIP());

 //Sensor DHT
  btemperatura = inicDHT();    
  //Sensor Presença
  bpresenca = false;
  pinMode(15,INPUT);//Entrada
  pinMode(40,OUTPUT);digitalWrite(40,LOW);
  //Sensor fogo
  bfogo = false;
  pinMode(16,INPUT);
  pinMode(40,OUTPUT);digitalWrite(40,LOW);

}
void loop() {
  if (bpresenca)
    digitalWrite(40,digitalRead(15)); 
  if (btemperatura)
    sensorTemperatura();
  if (bfogo)
    digitalWrite(40,digitalRead(16)); 
    
  EthernetClient client = server.available();// Criando um Cliente
  if (client) {
    Serial.println("Novo cliente");
    boolean bLinhaBranco = true; //Solicitação http termina com uma linha em branco
    String sURL;
    byte byOpcao;
    
    while (client.connected()) {
      if (client.available()) {
        char cAux = client.read();
        Serial.write(cAux);
        sURL.concat(cAux);//Concatenando caracter a caracter na String
        
        if (sURL.endsWith("?LOGIN"))
          byOpcao = 1;
        else if (sURL.endsWith("?TWITTER"))
          byOpcao = 2;
        else if (sURL.endsWith("?TOLKEN")) 
          byOpcao = 3;
        else if (sURL.endsWith("?REDESENHA")) 
          byOpcao = 4;  
        else if (sURL.endsWith("?DHT")) 
          byOpcao = 5;  
        else if (sURL.endsWith("?PRESENCA")) 
          byOpcao = 6;  
        else if (sURL.endsWith("?FOGO")) 
          byOpcao = 7;  
          
        //Se Chegou for quebra de linha E a linha esta em branco
        if (cAux == '\n' && bLinhaBranco) {
          //Monta o cabeçalho de retorno para o browser
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connnection: close");
          client.println();
          
           switch (byOpcao) {
            case 1://Login
              client.print(validaLogin(sURL,lerArquivoSD("login.txt")));
              break;
              
            case 2://posta no twitter
              enviaTwitter("Teste com arduino");//Enviado Mensagem para Twitter
              break;
              
            case 3://grava o tolken do twitter no SD
              client.print(gravaTolken(sURL));
              break;
            case 4://Redefinir Senha
              client.print(redefineSenha(sURL));
              break;
            case 5://Sensor Tempeatura
              btemperatura =!btemperatura;
              client.print(sensorTemperatura());
              break;
            case 6://Sensor Presenca
              bpresenca = !bpresenca;
              client.print("");
              break;
            case 7://Sensor Fogo
              bfogo = !bfogo;
              break;
            default: 
              client.print("<h1>Seja Bem Vindo ao Web Service AlarmeDuino.</h1>");
           }
          break;
        }
        if (cAux == '\n')
          bLinhaBranco = true;
        else if (cAux != '\r')
          bLinhaBranco = false;
      }
    }
    delay(2000);// Da tempo para o browser receber a resposta
    client.stop();
    Serial.println("Cliente Desconectado");
  }
}



