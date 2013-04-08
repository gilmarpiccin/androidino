/*
MICROCONTROLANDO
SUA LOJA VIRTUAL ESPECIALIZADA EM ARDUINO E PIC
VISTE A LOJA http://lista.mercadolivre.com.br/_CustId_76934379

Exemplo do uso do Módulo de Tempo, ajusta a hora para a do computador na hora da gravação do código no Arduino
Após gravar o programa nao desconete o Arduino da USB e grave em seguida o exemplo de Mostrar_Hora a hora.
Pois se reiniciar o Arduino a hora será ajustada novamente para o horario da compilação, resultando em um atraso
Para conectar o Módulo de Tempo direto no Arduino conecte da seguinte forma
Conecte o Pino SCL do sensor no pino A5 do Arduino
Conecte o Pino SDA do sensor no pino A4 do Arduino
Conecte o Pino VCC do sensor no pino A3 do Arduino
Conecte o Pino GND do sensor no pino A2 do Arduino
Caso não queira basta conectar o através de fios o módulo a uma protoboard e ao Arduino:
Conecte o Pino SCL do sensor no pino A5 do Arduino
Conecte o Pino SDA do sensor no pino A4 do Arduino
Conecte o Pino VCC do sensor no pino 5V do Arduino
Conecte o Pino GND do sensor no pino GND do Arduino

*/

#include <Wire.h>
#include "RTClib.h"

RTC_DS1307 RTC;

void setup () {
    pinMode(A3, OUTPUT);
    analogWrite(A3, 255);
    pinMode(A2, OUTPUT);
    analogWrite(A2, 0);
    Wire.begin();
    RTC.begin();

  if (RTC.isrunning()) {
    RTC.adjust(DateTime(__DATE__, __TIME__));
  }

}

void loop () {
 
}
