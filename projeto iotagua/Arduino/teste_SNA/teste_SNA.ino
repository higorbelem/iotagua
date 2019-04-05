int atual, SNA;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:
  SNA = analogRead(A0); // lê o valor analógico do SNA
  
    if(SNA > 920 ){
        //rele = "off";
        //digitalWrite(porta_rele,  LOW); // Desliga
    }else{
        //rele = "On";
        //digitalWrite(porta_rele, HIGH); // Liga
    }
      // Salva na variavél atual, qual é o nivel lido no SNA
         if(SNA <= 102) atual = 1;
          if(SNA >= 103 && SNA <= 1003) atual = 2;  // Valores obtidos através de teste
           if(SNA >= 1004 && SNA <= 1005) atual = 3;
            if(SNA >= 999 && SNA <= 1000) atual = 4;
             if(SNA >= 9999 && SNA <= 9999) atual = 5;
              if(SNA >= 9999 && SNA <= 9999) atual = 6;
               if(SNA >= 9999 && SNA <= 9999) atual = 7;

    Serial.println("SNA: " + String(SNA) + " | " + String(SNA-100));

    delay(1000);
}
