#include <SPI.h>
#include <Ethernet.h>

byte DHT = 14;
byte PRES = 15;
byte FOGO = 16;
byte SIRE = 40;

boolean OnOFF = false;

byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };// MAC do Arduino
IPAddress ip(192,168,1,177);// IP FIXO
EthernetServer server(8080);// porta default

void setup() {
  Serial.begin(9600);//Inicia a comunicação Serial
  while (!Serial) {
    ; //Fica no laço enquanto não estabelece a comunicação Serial
  }

  if (!Ethernet.begin(mac))//Usar o DHCP para procurar IP
  {
    Serial.println("Requisição de DHCP falhou.");
    Ethernet.begin(mac,ip);
  }
  //busca a porta no cartão SD
  EthernetServer server2(retornaPorta());
  server = server2;

  delay(1000);
  server.begin();//Iniciando o servidor
  Serial.print("Servidor localizando em: ");
  Serial.println(Ethernet.localIP());

  //Sensor DHT
  iniciaDHT(DHT);
  //Sensor Presença
  iniciaSensor(PRES);
  //Sensor fogo
  iniciaSensor(FOGO);
  //inicia sirene  
  iniciaSensor(SIRE);

}

void loop() {
  //Varrendo As portas do ALARME 
  if (OnOFF)
    Alarme();

  EthernetClient client = server.available();// Criando um Cliente
  if (client) {
    Serial.println("Novo cliente");
    boolean bLinhaBranco = true; //Solicitação http termina com uma linha em branco
    String sURL;
    int byOpcao;

    while (client.connected()) {
      if (client.available()) {
        char cAux = client.read();
        Serial.write(cAux);
        sURL.concat(cAux);//Concatenando caracter a caracter na String

        if (sURL.endsWith("?LOGIN"))
          byOpcao = 1;
        else if (sURL.endsWith("?TWITTER"))
          byOpcao = 2;
        else if (sURL.endsWith("TOKEN")) 
          byOpcao = 3;
        else if (sURL.endsWith("?DHT")) 
          byOpcao = 4;  
        else if (sURL.endsWith("?MOVIMENTO")) 
          byOpcao = 5;  
        else if (sURL.endsWith("?FOGO")) 
          byOpcao = 6;
        else if (sURL.endsWith("?ONOFF")) 
          byOpcao = 7;    
        else if (sURL.endsWith("=CADUSER")) 
          byOpcao = 8;    
        else if (sURL.endsWith("?SENSOR")) 
          byOpcao = 9;    

        //Se Chegou for quebra de linha E a linha esta em branco
        if (cAux == '\n' && bLinhaBranco) {

          //Monta o cabeçalho de retorno para o browser
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connnection: close");
          client.println();

          switch (byOpcao) {
          case 1://Login
            client.print(validaLogin(sURL));
            break;

          case 2://posta no twitter

            enviaTwitter("Teste com arduino");//Enviado Mensagem para Twitter
            break;

          case 3://grava o tolken do twitter no SD
            if (validaLogin(sURL))
              client.print(gravaToken(sURL));
            else
              client.print(false);
            break;

          case 4://Sensor Tempeatura
            if (validaLogin(sURL))
               client.print(sensorOnOFF(DHT));
             else
               client.print(false);
            break;

          case 5://Sensor Presenca
            if (validaLogin(sURL))
              client.print(sensorOnOFF(PRES));
            else
              client.print(false);
            break;

          case 6://Sensor Fogo
            if (validaLogin(sURL))
              client.print(sensorOnOFF(FOGO));
            else
              client.print(false);
            break;

          case 7:// Liga/desliga Alarme
             if (validaLogin(sURL)){
               OnOFF= !OnOFF ;      
               client.print(OnOFF);
             }
             else
               client.print(false);
            break;
          case 8://Cadastro usuario
             if (validaLogin(sURL))
               client.print(cadUsuario(sURL));
             else
               client.print(false);
             break;
          case 9://estatus dos Sensores
             if (validaLogin(sURL)){
               client.print(digitalRead(DHT)); 
               client.print(";");
               client.print(digitalRead(PRES));
               client.print(";");
               client.print(digitalRead(FOGO));               
               client.print(";");
               client.print(OnOFF);
             }else
               client.print(false);
             break;             
          default: 
            client.print("<h1>Seja Bem Vindo ao Web Server AndroiDino!</h1>");
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

void Alarme(){
  sensorTemperatura();
  digitalWrite(SIRE,digitalRead(PRES));   
  digitalWrite(SIRE,digitalRead(FOGO));
}



