/*
MICROCONTROLANDO
SUA LOJA VIRTUAL ESPECIALIZADA EM ARDUINO E PIC
VISTE A LOJA http://lista.mercadolivre.com.br/_CustId_76934379

Exemplo do uso do Módulo de Tempo liga e desliga o LED da porta 13 do Arduino nas horas especificadas.
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
//Caso queira pode chamar mais vezes as funções ligar() e desligar() ou ainda chamar apenas 1 vez cada uma delas
//Nesse exemplo foi chamada duas vezes 
//Ajuste a chamada da funcao para a hora desejada, sendo (hora, min, seg) 
//Primeira chamada, liga as 22:40 e 10 segundos e desliga as 22:40 e 20 segundos
ligar(22,40,10);
desligar(22,40,20);

//Segunda chamada, liga as 10:15 e 12 segundos e desliga as 10:20 e 30 segundos
ligar(10,15,12);
desligar(10,20,30);


}



void ligar(int hora, int minu, int sec){
  DateTime now = RTC.now();
  if(now.hour() == hora && now.minute() == minu && now.second() == sec){
    //coloque o código que deseja que seja executado abaixo, nesse caso é enviar uma mensagem dizendo que o alarme foi ativado
    digitalWrite(13, HIGH);
    
    
       
    delay(1000);//delay para garantir que será ativado apenas uma vez
  }
}

void desligar(int hora, int minu, int sec){
  DateTime now = RTC.now();
  if(now.hour() == hora && now.minute() == minu && now.second() == sec){
    //coloque o código que deseja que seja executado abaixo, nesse caso é enviar uma mensagem dizendo que o alarme foi ativado
    digitalWrite(13, LOW);
    
    
    delay(1000);//delay para garantir que será ativado apenas uma vez
  }
}


