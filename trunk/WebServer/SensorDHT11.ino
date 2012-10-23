#include "DHT11.h" // Importa a Biblioteca
byte byPino;

void iniciaDHT(byte Pin){
  pinMode(Pin,INPUT);//Entrada
  digitalWrite(Pin,LOW);
  byPino = Pin;
}


// Cria Instancia da Classe
DHT11 sensor1(byPino);

String sensorTemperatura()    
{
  String sSensor= "DHT Desligado";
  if (digitalRead(byPino))
  {
    
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

          break;
      }
      case -1: Serial.println("Sensor nao Encontrado"); break;
      case -2: Serial.println("Sensor nao Encontrado"); break;
      case -3: Serial.println("Erro de Checksum"); break;
    }
    delay(2000);
  }
 return sSensor;
}

