

void iniciaSensor(byte Pin){
  pinMode(Pin,INPUT);//Entrada
  digitalWrite(Pin,LOW);
}

boolean sensorOnOFF(byte Pin){
  boolean estado = !digitalRead(Pin);
  digitalWrite(Pin,estado);
  return estado;
}

