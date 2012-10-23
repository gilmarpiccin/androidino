

void iniciaSensor(byte Pin){
  pinMode(Pin,INPUT);//Entrada
  digitalWrite(Pin,LOW);
}

String sensorOnOFF(byte Pin){
  boolean estado = !digitalRead(Pin);
  digitalWrite(Pin,estado);
  if (estado)
    return "Ligado.";
  else
    return "Desligado.";
}

