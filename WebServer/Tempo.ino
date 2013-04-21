

String Tempo(){
  DateTime now = RTC.now();
  String sHora = "";
  sHora.concat(now.day());
  sHora.concat('/');
  sHora.concat(now.month());
  sHora.concat('/');
  sHora.concat(now.year());
  sHora.concat(' ');
  sHora.concat(now.hour());
  sHora.concat(':');
  sHora.concat(now.minute());
  sHora.concat(':');
  sHora.concat(now.second());
 return sHora;   
}
