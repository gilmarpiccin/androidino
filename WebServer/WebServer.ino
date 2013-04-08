#include <SPI.h>
#include <Ethernet.h>
#include <Wire.h>
#include "RTClib.h"

#if defined(ARDUINO) && ARDUINO > 18
#endif
#include <Twitter.h>

//Portas dos sensores
byte DHT = 14;
byte MOV = 15;
byte FOGO = 16;
byte SIRE = 40;

//status dos Sensore. Iniciam ligados
boolean OnOFF = true; //Alarme Ligado
boolean bOnOffMov = false; //Sensor Movimento
boolean bOnOffFogo = true; //Sensor Fogo

//Instancia o relógio
RTC_DS1307 RTC;

byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};// MAC do Arduino
IPAddress ip(192,168,1,177);// IP FIXO
EthernetServer server(8080);// Porta default

void setup() {
  Serial.begin(9600);//Inicia a comunicação Serial

  //Fica no laço enquanto não estabelece a comunicação Serial
  while (!Serial) {
    ; 
  }
  //Usa o DHCP para procurar IP
  if (!Ethernet.begin(mac))
  {
    Serial.println("Requisição de DHCP falhou.");
    //Se falhar atribui IP fixo
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
  //Sensor Movimento
  iniciaSensor(MOV);
  //Sensor fogo
  iniciaSensor(FOGO);

  //Energiza o Relógio
  pinMode(18, OUTPUT);
  digitalWrite(18,LOW);
  pinMode(19, OUTPUT);
  digitalWrite(19, HIGH);
  Wire.begin();
  RTC.begin();
  
  //inicia sirene  
  pinMode(SIRE,OUTPUT);
  delay(1000);//tempo para estabilizar os sensores
  digitalWrite(SIRE,HIGH);
  delay(500);
  digitalWrite(SIRE,LOW);

  Serial.println("Alarme pronto para uso.");
}

void loop() {
  //validando sensores do ALARME 
  if (OnOFF)
    Alarme();

  // Criando um Cliente
  EthernetClient client = server.available();
  if (client) {
    Serial.println("Novo cliente");
    boolean bLinhaBranco = true; //Solicitação http termina com uma linha em branco
    String sURL;//URL enciada na requisição
    int byOpcao;//opção de requisiçãoS

    while (client.connected()) {
      if (client.available()) {
        char cAux = client.read();
        Serial.write(cAux);
        sURL.concat(cAux);//Concatenando caracter a caracter na String

        //Atribui numero para a requisição
        if (sURL.endsWith("?LOGIN"))
          byOpcao = 1;
        else if (sURL.endsWith("?TWITTER"))
          byOpcao = 2;
        else if (sURL.endsWith("=TOKEN")) 
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
        else if (sURL.endsWith("?PANICO"))
          byOpcao = 10;       
        else if (sURL.endsWith("=DELETA"))
          byOpcao = 11;       
          
        //Se for quebra de linha E a linha esta em branco
        if (cAux == '\n' && bLinhaBranco) {

          //Monta o cabeçalho de retorno para o browser
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connnection: close");
          client.println();
          
          //Só faz algum comando se o usuário for válido (SEGURANÇA)
          if (validaLogin(sURL)){
            switch (byOpcao) {
            case 1://Login
              client.print(true);
              break;

            case 2:// no twitter
              enviaTwitter("Teste com arduino");//Enviado Mensagem para Twitter
              break;

            case 3://grava o tolken do twitter no SD
              client.print(gravaToken(sURL));
              break;

            case 4://Sensor Tempeatura
              client.print(SensorHumidade());
              client.print(";");
              client.print(SensorTemperatura());
              digitalWrite(SIRE,LOW);
              break;

            case 5://Sensor Movimento
              bOnOffMov = !bOnOffMov; //Se igual a False, seta como True e vice-versa
              client.print(bOnOffMov);//Retorna a requisição
              digitalWrite(SIRE,LOW);//Desliga a sirene
              break;

            case 6://Sensor Fogo
              bOnOffFogo = !bOnOffFogo; //Sei gual a False, seta como True e vice-versa
              client.print(bOnOffFogo);//Retorna a requisição
              digitalWrite(SIRE,LOW);//desliga a sirene
              break;

            case 7:// Liga/desliga Alarme
              OnOFF= !OnOFF ;      //Sei gual a False, seta como True e vice-versa
              client.print(OnOFF);//Retorna a requisiçao
              digitalWrite(SIRE,LOW);//desliga a sirene
              if (OnOFF){//habilita os sensores quando liga o alarme 
                bOnOffFogo = true;
                bOnOffMov = true;
              }                
              break;

            case 8://Cadastro usuario
              client.print(cadUsuario(sURL));
              break;

            case 9://status dos Sensores ()
              client.print(bOnOffMov);
              client.print(";");
              client.print(bOnOffFogo);               
              client.print(";");
              client.print(OnOFF);
              break;             
              
            case 10://Função PANICO
              digitalWrite(SIRE,!digitalRead(SIRE));
              break;  
              
            case 11://DELETA usuário
              client.print(delUsuario(sURL));
              break; 
              
            default: 
              client.print("<h1>Seja Bem Vindo ao Web Server AndroiDino!</h1>");
            }
          }
          else{//Valiação do usuário falhar retorno negativo
            client.print(false);
          }
          //sai da val. que verifica se a URL terminou
          break;
        }

        //setando variáveis de controle que identificam o fim da URL
        if (cAux == '\n')
          bLinhaBranco = true;
        else if (cAux != '\r')
          bLinhaBranco = false;
      }
    }
    delay(2000);//tempo para o browser receber a resposta
    client.stop();//desconecta o cliente
    Serial.println("Cliente Desconectado");
  }
}

void Alarme(){
  if (bOnOffMov){ //Se o sensor estiver ativo 
    Serial.print("Mov: ");  //DEBUG
    Serial.println(digitalRead(MOV));//DEBUG
    if ((digitalRead(MOV))&& (digitalRead(SIRE)==0)){ //se o Sensor de Movimento for aciona e a Sirene esiver desligada.
      digitalWrite(SIRE, HIGH); 
      enviaTwitter("Sensor de Presenca disparou!!");
    }
    //recurso para não travar o alarme
    pinMode(MOV,OUTPUT);    digitalWrite(MOV,LOW);    delay(100);    pinMode(MOV,INPUT);
  }
  
  if (bOnOffFogo){ //Sensor de Fogo estiver ativado
    Serial.print("Fogo: ");  
    Serial.println(digitalRead(FOGO));
    if ((digitalRead(FOGO)) && (digitalRead(SIRE)==0)){ //Se o sensor estiver ativo e a sirene estiver desligada
      digitalWrite(SIRE, HIGH); //Verifica se recebeu True p/ disparar a Sirene e o Twitter
      enviaTwitter("Sensor de Fogo disparou!");    
    }
  }
  Serial.println(Tempo());
  delay(1000);
}





