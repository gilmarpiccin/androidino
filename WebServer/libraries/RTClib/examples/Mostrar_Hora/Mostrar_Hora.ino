/*
MICROCONTROLANDO
SUA LOJA VIRTUAL ESPECIALIZADA EM ARDUINO E PIC
VISTE A LOJA http://lista.mercadolivre.com.br/_CustId_76934379

Exemplo do uso do Módulo de Tempo, ajusta a hora para a do computador na hora da gravação do código no Arduino
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
    Serial.begin(9600);
    pinMode(A3, OUTPUT);
    analogWrite(A3, 255);
    pinMode(A2, OUTPUT);
    analogWrite(A2, 0);
    Wire.begin();
    RTC.begin();
}

void loop () {
    DateTime now = RTC.now();
    Serial.print("Data e hora atual: ");
    Serial.print(now.day(), DEC);
    Serial.print('/');
    Serial.print(now.month(), DEC);
    Serial.print('/');
    Serial.print(now.year(), DEC);
    Serial.print(' ');
    Serial.print(now.hour(), DEC);
    Serial.print(':');
    Serial.print(now.minute(), DEC);
    Serial.print(':');
    Serial.print(now.second(), DEC);
    Serial.println();
    delay(1000);
}
