// Biblioteca para uso do Sensor DHT11 - Sensor de Temperatura e Humidade Digital
// Criado por Guilherme Bacellar Moralez em 17/07/2012
// Versao: 1.0.0

// Area de Inclusao de Dependencias
#include "DHT11.h"

// Declara as Variaveis
int _SensorPin;

// CTOR
DHT11::DHT11(int sensorPin)
{
	_SensorPin = sensorPin;
}

// Metodos
void DHT11::Initialize()
{
	pinMode(_SensorPin, OUTPUT);
	digitalWrite(_SensorPin, HIGH); 
}


int DHT11::Read(double sensorData[2])
{
  byte dht11_dat[5];   
  byte dht11_in;
  byte dht11_check_sum;
  byte i;
  
  // Inicializacao da Comunicacao (Envio)
  digitalWrite(_SensorPin, LOW);
  delay(18); // 18 ms
  digitalWrite(_SensorPin, HIGH);
  delayMicroseconds(30); // 30 탎 
  
  // Recupera Resposta de Inicializacao
  pinMode(_SensorPin, INPUT);
  delayMicroseconds(40); // 40 탎 (1/2 do Tempo Necessario)
  
  // Le a Resposta
  if (digitalRead(_SensorPin)) // Se o Sinal for (HIGH esta OK)
  {
    return -1;
  }
  
  // Aguarda 80 탎
  delayMicroseconds(80);
  if (!digitalRead(_SensorPin)) // Se o Sinal por LOW esta OK
  {
    return -2;
  }
    
  // Aguarda 80 탎 para Receber os Dados
  delayMicroseconds(80);
  
  // Recebe os 40 Bytes (5x8bytes) da Resposta
  for (i=0; i<5; i++)
  {  
    dht11_dat[i] = ReadSensorData();
  } 
  
  // Reseta o Sensor
  pinMode(_SensorPin, OUTPUT);
  digitalWrite(_SensorPin, HIGH);
  
  // Calcula o CheckSum
  dht11_check_sum = dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3]; 
  
  // Verifica o CheckSum (Para Garantir o Retorno dos Dados)
  if(dht11_dat[4]!= dht11_check_sum)
  {
    return -3;
  }
  
  // Monta o Resultado
  sensorData[0] = dht11_dat[0] + dht11_dat[1];
  sensorData[1] = dht11_dat[2] + dht11_dat[3];
  
  // Retorna Sucesso
  return 0;
}


byte DHT11::ReadSensorData()
{
  byte i = 0;
  byte result=0;
  for(i=0; i< 8; i++)
  {
    while (!digitalRead(_SensorPin));
    delayMicroseconds(30);
    if (digitalRead(_SensorPin) != 0 )
      bitSet(result, 7-i);
    while (digitalRead(_SensorPin));
  }
  return result;
}