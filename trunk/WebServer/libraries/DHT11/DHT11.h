// Biblioteca para uso do Sensor DHT11 - Sensor de Temperatura e Humidade Digital
// Criado por Guilherme Bacellar Moralez em 17/07/2012
// Versao: 1.0.0

#ifndef __DHT11__
#define __DHT11__

// Inclui as Referencias Necessarias
#include <inttypes.h>
#if defined(ARDUINO) && ARDUINO >= 100
  #include <Arduino.h>
#else
  #include <WProgram.h>
#endif

// Classe Principal
class DHT11
{
public:
	
	// Ctor
	DHT11(int sensorPin);

	// Inicializa o Sensor
	void Initialize();
	
	// Retorno
	//  0  -> Sucesso
	//  -1 -> Sensor nao Disponivel (Erro na Primeira Checagem)
	//  -2 -> Sensor nao Disponivel (Erro na Segunda Checagem)
	//  -3 -> Erro de CheckSum
	// Estrutura do Resultado
	// [0] - Humidade
	// [1] - Temperatura
	int Read(double sensorData[2]);
		
private:
	int _SensorPin;
	
	// Realiza o Parser dos Dados do Sensor
	byte ReadSensorData();
};

#endif