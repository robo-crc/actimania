/*
 *  Arduino 1
 */

int Act1B1;    // Actuateur 1, côté bleu, première lecture
int Act1B2;    // Actuateur 1, côté bleu, deuxième lecture
int Act1B;     // Actuateur 1, côté bleu, état officiel transmis
// etc
int Act1J1;
int Act1J2;
int Act1J;

int Act2B1;
int Act2B2;
int Act2B;

int Act2J1;
int Act2J2;
int Act2J;

int Act3B1;
int Act3B2;
int Act3B;

int Act3J1;
int Act3J2;
int Act3J;


int Act4B1;
int Act4B2;
int Act4B;


int Act4J1;
int Act4J2;
int Act4J;

int Act5B1;
int Act5B2;
int Act5B;

int Act5J1;
int Act5J2;
int Act5J;

int Act6B1;
int Act6B2;
int Act6B;

int Act6J1;
int Act6J2;
int Act6J;

int Cible1A;    // Cible 1, première lecture
int Cible1B;    // Cible 1, deuxième lecture
int Cible1;     // Cible 1, état officiel

int Cible2A;    
int Cible2B;
int Cible2;

int Cible3A;    
int Cible3B;
int Cible3;

int Cible4A;    
int Cible4B;
int Cible4;

int Cible5A;    
int Cible5B;
int Cible5;

int Cible6A;    
int Cible6B;
int Cible6;

void setup() {

  pinMode(2, INPUT);
  pinMode(3, INPUT);
  pinMode(4, INPUT);
  pinMode(5, INPUT);
  pinMode(6, INPUT);
  pinMode(7, INPUT);
  pinMode(8, INPUT);
  pinMode(9, INPUT);
  pinMode(10, INPUT);
  pinMode(11, INPUT);
  pinMode(12, INPUT);
  pinMode(13, INPUT);            // Attention : branchement spécial pour elle.
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  pinMode(A3, INPUT);
  pinMode(A4, INPUT);
  pinMode(A5, INPUT);  

  Serial.begin(9600);                // Set up Serial communication at 9600bps
  Act1B = LOW;
  Act1J = LOW; 
  Act2B = LOW;
  Act2J = LOW; 
  Act3B = LOW;
  Act3J = LOW; 
  Act4B = LOW;
  Act4J = LOW; 
  Act5B = LOW;
  Act5J = LOW; 
  Act6B = LOW;
  Act6J = LOW;
  Cible1 = LOW; 
  Cible2 = LOW;
  Cible3 = LOW;
  Cible4 = LOW;
  Cible5 = LOW;
  Cible6 = LOW;
}

void loop()
{
  Act1B1 = digitalRead(2);
  Act1J1 = digitalRead(3); 
  Act2B1 = digitalRead(4);
  Act2J1 = digitalRead(5); 
  Act3B1 = digitalRead(6);
  Act3J1 = digitalRead(7); 
  Act4B1 = digitalRead(8);
  Act4J1 = digitalRead(9); 
  Act5B1 = digitalRead(10);
  Act5J1 = digitalRead(11); 
  Act6B1 = digitalRead(12);
  Act6J1 = digitalRead(13);
  Cible1A = digitalRead(A0);
  Cible2A = digitalRead(A1); 
  Cible3A = digitalRead(A2);
  Cible4A = digitalRead(A3); 
  Cible5A = digitalRead(A4);
  Cible6A = digitalRead(A5); 

  delay(10);                         // 10 milliseconds is a good amount of time

  Act1B2 = digitalRead(2);
  Act1J2 = digitalRead(3); 
  Act2B2 = digitalRead(4);
  Act2J2 = digitalRead(5); 
  Act3B2 = digitalRead(6);
  Act3J2 = digitalRead(7); 
  Act4B2 = digitalRead(8);
  Act4J2 = digitalRead(9); 
  Act5B2 = digitalRead(10);
  Act5J2 = digitalRead(11); 
  Act6B2 = digitalRead(12);
  Act6J2 = digitalRead(13);
  Cible1B = digitalRead(A0);
  Cible2B = digitalRead(A1); 
  Cible3B = digitalRead(A2);
  Cible4B = digitalRead(A3); 
  Cible5B = digitalRead(A4);
  Cible6B = digitalRead(A5);

  // read the input again to check for bounces

  if (Act1B1 == Act1B2) 
  {                 // Si nos deux valeurs sont égales
    if (Act1B1 != Act1B) 
    {          // Et que l'état a changé
      if (Act1B1 == LOW) 
      {
        Serial.println("1.BL");
      } 
      if (Act1B1 == HIGH) 
      {
        Serial.println("1.BH");
      }
    }
  }
  Act1B = Act1B1;                 // save the new state in our variable



  if (Act1J1 == Act1J2) 
  {                 // Si nos deux valeurs sont égales
    if (Act1J1 != Act1J) 
    {          // Et que l'état a changé
      if (Act1J1 == LOW) 
      {
        Serial.println("1.JL");
      } 
      if (Act1J1 == HIGH) 
      {
        Serial.println("1.JH");
      }
    }
  }
  Act1J = Act1J1;                 // save the new state in our variable


  if (Act2B1 == Act2B2) 
  {                 // Si nos deux valeurs sont égales
    if (Act2B1 != Act2B) 
    {          // Et que l'état a changé
      if (Act2B1 == LOW) 
      {
        Serial.println("2.BL");
      } 
      if (Act2B1 == HIGH) 
      {
        Serial.println("2.BH");
      }
    }
  }
  Act2B = Act2B1;                 // save the new state in our variable



  if (Act2J1 == Act2J2) 
  {                 // Si nos deux valeurs sont égales
    if (Act2J1 != Act2J) 
    {          // Et que l'état a changé
      if (Act2J1 == LOW) 
      {
        Serial.println("2.JL");
      } 
      if (Act2J1 == HIGH) 
      {
        Serial.println("2.JH");
      }
    }
  }
  Act2J = Act2J1;                 // save the new state in our variable


  if (Act3B1 == Act3B2) 
  {                 // Si nos deux valeurs sont égales
    if (Act3B1 != Act3B) 
    {          // Et que l'état a changé
      if (Act3B1 == LOW) 
      {
        Serial.println("3.BL");
      } 
      if (Act3B1 == HIGH) 
      {
        Serial.println("3.BH");
      }
    }
  }
  Act3B = Act3B1;                 // save the new state in our variable



  if (Act3J1 == Act3J2) 
  {                 // Si nos deux valeurs sont égales
    if (Act3J1 != Act3J) 
    {          // Et que l'état a changé
      if (Act3J1 == LOW) 
      {
        Serial.println("3.JL");
      } 
      if (Act3J1 == HIGH) 
      {
        Serial.println("3.JH");
      }
    }
  }
  Act3J = Act3J1;                 // save the new state in our variable


  if (Act4B1 == Act4B2) 
  {                 // Si nos deux valeurs sont égales
    if (Act4B1 != Act4B) 
    {          // Et que l'état a changé
      if (Act4B1 == LOW) 
      {
        Serial.println("4.BL");
      } 
      if (Act4B1 == HIGH) 
      {
        Serial.println("4.BH");
      }
    }
  }
  Act4B = Act4B1;                 // save the new state in our variable



  if (Act4J1 == Act4J2) 
  {                 // Si nos deux valeurs sont égales
    if (Act4J1 != Act4J) 
    {          // Et que l'état a changé
      if (Act4J1 == LOW) 
      {
        Serial.println("4.JL");
      } 
      if (Act4J1 == HIGH) 
      {
        Serial.println("4.JH");
      }
    }
  }
  Act4J = Act4J1;                 // save the new state in our variable


  if (Act5B1 == Act5B2) 
  {                 // Si nos deux valeurs sont égales
    if (Act5B1 != Act5B) 
    {          // Et que l'état a changé
      if (Act5B1 == LOW) 
      {
        Serial.println("5.BL");
      } 
      if (Act5B1 == HIGH) 
      {
        Serial.println("5.BH");
      }
    }
  }
  Act5B = Act5B1;                 // save the new state in our variable



  if (Act5J1 == Act5J2) 
  {                 // Si nos deux valeurs sont égales
    if (Act5J1 != Act5J) 
    {          // Et que l'état a changé
      if (Act5J1 == LOW) 
      {
        Serial.println("5.JL");
      } 
      if (Act5J1 == HIGH) 
      {
        Serial.println("5.JH");
      }
    }
  }
  Act5J = Act5J1;                 // save the new state in our variable


  if (Act6B1 == Act6B2) 
  {                 // Si nos deux valeurs sont égales
    if (Act6B1 != Act6B) 
    {          // Et que l'état a changé
      if (Act6B1 == LOW) 
      {
        Serial.println("6.BL");
      } 
      if (Act6B1 == HIGH) 
      {
        Serial.println("6.BH");
      }
    }
  }
  Act6B = Act6B1;                 // save the new state in our variable



  if (Act6J1 == Act6J2) 
  {                 // Si nos deux valeurs sont égales
    if (Act6J1 != Act6J) 
    {          // Et que l'état a changé
      if (Act6J1 == LOW) 
      {
        Serial.println("6.JL");
      } 
      if (Act6J1 == HIGH) 
      {
        Serial.println("6.JH");
      }
    }
  }
  Act6J = Act6J1;                 // save the new state in our variable

  if (Cible1A == Cible1B) 
  {                 // Si nos deux valeurs sont égales
    if (Cible1A != Cible1) //et que la valeur a changé
    {
      if (Cible1A == LOW) // Le bouton vient d'être relâché : envoyer le hit
      {
        Serial.println("C.1");
      } 
       Cible1 = Cible1A; 
    }
  }

  if (Cible2A == Cible2B) 
  {                 // Si nos deux valeurs sont égales
      if (Cible2A != Cible2) //et que la valeur a changé
    {
      if (Cible2A == LOW) // Le bouton vient d'être relâché : envoyer le hit
      {
        Serial.println("C.2");
      } 
       Cible2 = Cible2A; 
    }
  }
  if (Cible3A == Cible3B) 
  {                 // Si nos deux valeurs sont égales
      if (Cible3A != Cible3) //et que la valeur a changé
    {
      if (Cible3A == LOW) // Le bouton vient d'être relâché : envoyer le hit
      {
        Serial.println("C.3");
      } 
       Cible3 = Cible3A; 
    }
  }
  if (Cible4A == Cible4B) 
  {                 // Si nos deux valeurs sont égales
      if (Cible4A != Cible4) //et que la valeur a changé
    {
      if (Cible4A == LOW) // Le bouton vient d'être relâché : envoyer le hit
      {
        Serial.println("C.4");
      } 
       Cible4 = Cible4A; 
    }
  }
  if (Cible5A == Cible5B) 
  {                 // Si nos deux valeurs sont égales
      if (Cible5A != Cible5) //et que la valeur a changé
    {
      if (Cible5A == LOW) // Le bouton vient d'être relâché : envoyer le hit
      {
        Serial.println("C.5");
      } 
       Cible5 = Cible5A; 
    }
  }
  if (Cible6A == Cible6B) 
  {                 // Si nos deux valeurs sont égales
      if (Cible6A != Cible6) //et que la valeur a changé
    {
      if (Cible6A == LOW) // Le bouton vient d'être relâché : envoyer le hit
      {
        Serial.println("C.6");
      } 
       Cible6 = Cible6A; 
    }
  }


}


