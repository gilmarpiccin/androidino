#include <SPI.h>
#include <Ethernet.h>

byte DHT = 14;
byte PRES = 15;
byte FOGO = 16;
byte SIRE = 40;

boolean OnOFF = true; //Alarme Ligado
boolean bOnOffMov = false; //Sensor Movimento
boolean bOnOffFogo = false; //Sensor Fogo


byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};// MAC do Arduino
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
  pinMode(SIRE,OUTPUT);
  delay(10000);//tempo para estabilizar os sensores
  digitalWrite(SIRE,HIGH);
  delay(500);
  digitalWrite(SIRE,LOW);

  Serial.println("Alarme pronto para uso.");
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
          //Só faz algum comando se o usuário for válido
          if (validaLogin(sURL)){
            switch (byOpcao) {
            case 1://Login
              client.print(true);
              break;

            case 2://posta no twitter
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
              client.print(bOnOffMov);
              digitalWrite(SIRE,LOW);
              break;

            case 6://Sensor Fogo
              bOnOffFogo = !bOnOffFogo; //Seigual a False, seta como True e vice-versa
              client.print(bOnOffFogo);
              digitalWrite(SIRE,LOW);
              break;

            case 7:// Liga/desliga Alarme
              OnOFF= !OnOFF ;      
              client.print(OnOFF);
              digitalWrite(SIRE,LOW);
              break;

            case 8://Cadastro usuario
              client.print(cadUsuario(sURL));
              break;

            case 9://estatus dos Sensores
              client.print(bOnOffMov);
              client.print(";");
              client.print(bOnOffFogo);               
              client.print(";");
              client.print(OnOFF);
              break;             
            case 10://PANICO
              digitalWrite(SIRE,!digitalRead(SIRE));
              break;  
            case 11://DELETA
             client.print(delUsuario(sURL));
             break; 
            default: 
              client.print("<h1>Seja Bem Vindo ao Web Server AndroiDino!</h1>");
            }
          }else{
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
    delay(2000);// Da tempo para o browser receber a resposta
    client.stop();//desconecta o cliente
    Serial.println("Cliente Desconectado");
  }
}

void Alarme(){
  if (bOnOffMov){ //Se o sensor estiver ativo 
  Serial.print("Mov: ");  
  Serial.println(digitalRead(PRES));
    if ((digitalRead(PRES))&& (digitalRead(SIRE)==0)){ //se o Sensor de Movimento for aciona e a Sirene esiver desligada.
      digitalWrite(SIRE, HIGH); 
      enviaTwitter("Sensor de Presença disparou!!");
    }
    pinMode(PRES,OUTPUT);
    digitalWrite(PRES,LOW);
    delay(100);
    pinMode(PRES,INPUT);
  }
  if (bOnOffFogo){ 
    Serial.print("Fogo: ");  
    Serial.println(digitalRead(FOGO));
    if ((digitalRead(FOGO)) && (digitalRead(SIRE)==0)){ //Se o sensor estiver ativo e a sirene estiver desligada
      digitalWrite(SIRE, HIGH); //Verifica se recebeu True p/ disparar a Sirene e o Twitter
      enviaTwitter("Sensor de Fogo disparou!");    
    }
  }
}




