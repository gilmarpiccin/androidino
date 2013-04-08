/*
MICROCONTROLANDO
SUA LOJA VIRTUAL ESPECIALIZADA EM ARDUINO E PIC
VISTE A LOJA http://lista.mercadolivre.com.br/_CustId_76934379

Exemplo do uso do Módulo de Tempo, um alarme vai acender o LED da porta 13 e enviar a mensagem pela serial na hora ajustada
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
    pinMode(13, OUTPUT);
    digitalWrite(13, LOW);
    pinMode(A3, OUTPUT);
    analogWrite(A3, 255);
    pinMode(A2, OUTPUT);
    analogWrite(A2, 0);
    Wire.begin();
    RTC.begin();
}

void loop () {
//Ajuste a chamada da funcao para a data e hora desejada, sendo (ano, mes, dia, hora, min, seg)  
alarme(2012,11,03,22,40,10);
}



void alarme(int ano, int mes, int dia, int hora, int minu, int sec){
  DateTime now = RTC.now();
  if(now.year() == ano && now.month() == mes && now.day() == dia && now.hour() == hora && now.minute() == minu && now.second() == sec){
    //coloque o código que deseja que seja executado abaixo, nesse caso é enviar uma mensagem dizendo que o alarme foi ativado
    Serial.print("Alarme Ativado!" );
    digitalWrite(13, HIGH);
    
    
    
    delay(1000);//delay para garantir que o alarme será ativado apenas uma vez
  }
}
