#include "DHT11.h" // Importa a Biblioteca

byte dhtGND =40;//GND
byte dht5v =42;//Alimentação 5v
#define dhtpin 14// Pino Digital 2

boolean inicDHT(){
//  pinMode(dht5v,OUTPUT); digitalWrite(dht5v,HIGH);//5v
//  pinMode(dhtGND,OUTPUT); digitalWrite(dhtGND,LOW); //GND
  return false;
}

// Cria Instancia da Classe
DHT11 sensor1(dhtpin);
      
String sensorTemperatura()    
{
 
  String sSensor;
  // Inicializa o Sensor
   sensor1.Initialize();

   Serial.println("Lendo...");

  // Cria Estrutura para os Dados de Retorno
  double sensorData[2];
  
  
  // Cria Variavel para o Resultado da Leitura
  int result;
  
  // Le os Dados do Sensor
  result = sensor1.Read(sensorData);
  
  // Realiza o Parser da Resposta
  switch(result)
  {
    case 0: 
    {
        Serial.print("Humidade = ");
        Serial.print(sensorData[0], DEC);
        Serial.print("%  ");
        Serial.print("Temperatura = ");
        Serial.print(sensorData[1], DEC);
        Serial.println("C  ");
/*        sSensor.concat("Humidade = ");
        sSensor.concat(foStr(sensorData[0]));
        sSensor.concat("% ");
        sSensor.concat("Temperatura = ");
        sSensor.concat(sensorData[1]);
        sSensor.concat("ºC");*/
        break;
    }
    case -1: Serial.println("Sensor nao Encontrado"); break;
    case -2: Serial.println("Sensor nao Encontrado"); break;
    case -3: Serial.println("Erro de Checksum"); break;
  }
  delay(2000);
 return sSensor;
}
