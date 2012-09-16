
#include <SPI.h>
#include <Ethernet.h>
#include <SD.h>
#if defined(ARDUINO) && ARDUINO > 18
#endif
#include <Twitter.h>

byte mac[] = { 
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };

IPAddress ip(192,168,1,177);// 
IPAddress gateway(192,168,1, 1);
IPAddress subnet(255, 255, 255, 0);
IPAddress Dns(0, 0, 0, 0);
EthernetServer server(80);
void setup() {

  Serial.begin(9600);
  while (!Serial) {
    ; 
  }

  if (!Ethernet.begin(mac))
  {
    Serial.println("Requisição de DHCP falhou.");
    Ethernet.begin(mac,ip,Dns,gateway,subnet);
  }
  server.begin();
  Serial.print("Servidor localizando em: ");
  Serial.println(Ethernet.localIP());

  pinMode(53, OUTPUT);
  if (!SD.begin(4)) {
    Serial.println("Inicialização SD Falhou!");
  }  
  Serial.println("SD Inincializado com exito.");

}


void loop() {

  EthernetClient client = server.available();
  if (client) {
    Serial.println("novo cliente");
   
    boolean currentLineIsBlank = true; //Solicitação http termina com uma linha em branco
    String vars;
    byte varOnOff;

    pinMode(13,OUTPUT);
    pinMode(15,INPUT);
    
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        Serial.write(c);
        vars.concat(c);
        
        if (vars.endsWith("?Login")){
          varOnOff=1;
        }
        else if (vars.endsWith("?TWITTER"))
          varOnOff=2;
        else if (vars.endsWith("/led")) 
          varOnOff=3;
        else if (vars.endsWith("/on")) 
          varOnOff=4;        
        //Se Chegou for quebra de linha E a linha esta em branco
        if (c == '\n' && currentLineIsBlank) {
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connnection: close");
          client.println();
          
           switch (varOnOff) {
            case 1:
              client.println(validaLogin(vars,lerArquivo("login.txt")));
              break;
            case 2:
              enviaTwitter("teste arduino");
              break;
            case 3:
              client.println("<h1>Lendo LED..</h1>");
              digitalWrite(13,!digitalRead(15));            
              break;
            default: 
              client.println("<h1>Seja Bem Vindo ao Web Service AlarmeDuino.</h1>");
           }
          break;
        }
        if (c == '\n') {
          currentLineIsBlank = true;
        } 
        else if (c != '\r') {
          currentLineIsBlank = false;
        }
      }
    }
    delay(2000);
    client.stop();
    Serial.println("Cliente Desconectado");
  }
}



void gravaArquivo(char arquivo[],char texto []){
  File myFile;
  myFile = SD.open(arquivo, FILE_WRITE);
  
  if (myFile) {
    Serial.print("Escrevendo no arquivo: ");
    Serial.print(arquivo);

    myFile.println(texto);
    myFile.close();
    Serial.println("Gravado com Sucesso.");
  } else {
    Serial.println("Erro ao Abrir: "  );
    Serial.print(arquivo);
  }

}

String lerArquivo (char arquivo[]){
  Serial.println("Lendo Arquivo do SD");
  File myFile = SD.open(arquivo);
  String texto;
  if (myFile) {
      while (myFile.available()) {
       char a = myFile.read();
       Serial.write(a);    
       texto.concat(a);
    }
    
    Serial.println("Fechando o arquivo");
    delay(3000);
    Serial.println(texto);
    myFile.close();
  } else {
    Serial.println('Erro ao Abrir' + arquivo);
    return "";
  }
  texto.trim();
  return texto;
}

String validaLogin(String Android, String Arduino){
  String AdUsuario,AdSenha,ArUsuario,ArSenha;

  AdUsuario = Android.substring(Android.indexOf('$')+1,Android.indexOf('&'));
  Serial.println("\nlendo Usuario Android:");
  Serial.println(AdUsuario);
  AdSenha = Android.substring(Android.indexOf('&')+1,Android.indexOf("?"));
  Serial.println("lendo senha Android:");
  Serial.println(AdSenha);
  
  ArUsuario = Arduino.substring(0,Arduino.indexOf(';'));
  Serial.println("\nlendo Usuario Arduino:");
  Serial.println(ArUsuario);
  ArSenha = Arduino.substring(Arduino.indexOf(';')+1,Arduino.length());
  Serial.println("lendo senha Arduino:");
  Serial.println(ArSenha);

  if ((AdUsuario == ArUsuario) && (AdSenha== ArSenha)){
    Serial.println("True");
    return "True";
  }else{
    Serial.println("False");
    return "False";
  }
}

void enviaTwitter(char msg[]){
  Twitter twitter("63860592-pOWE5ipMXcLyV8077glcdTe7oWAXMJjZoC7PjZz6l");  

  Serial.println("connecting ...");
  if (twitter.post(msg)) {
    int status = twitter.wait(&Serial);
    if (status == 200) {
      Serial.println("OK.");
    } else {
      Serial.print("failed : code ");
      Serial.println(status);
    }
  } else {
    Serial.println("connection failed.");
  }


}
