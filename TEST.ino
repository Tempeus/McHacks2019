const double STEPS_PER_ROTATION = 3200.0;
const double DEGREES_PER_ROTATION = 360.0;
const double MILLIMETER_PER_DEGREE = 2.0*3.1415926535899*300.0 / 360.0;
const double STEPS_PER_DEGREE = STEPS_PER_ROTATION / DEGREES_PER_ROTATION;
const double DEGREE_PER_STEPS = DEGREES_PER_ROTATION / STEPS_PER_ROTATION;

//Delay for the max speed
int microseconds_per_step = 100;

const int PUL = 11;

const int DIR = 12;

const int DIS = 13;

const int ENDSTOP = 1;

const int LED = 2;

int itteration = 0;

void setup() {
  pinMode(PUL, OUTPUT);
  pinMode(DIR, OUTPUT);
  pinMode(DIS, OUTPUT);
  pinMode(ENDSTOP, INPUT);
  pinMode(LED, OUTPUT);

  digitalWrite(DIR, HIGH);
  digitalWrite(DIS,LOW);

}
boolean forward = true;

void loop() {
  int state = analogRead(ENDSTOP);
   
  
  if (forward == true){
    stepForward();
    itteration ++;  
    if (itteration >= 1000){
      forward = false;
    }
  }

  else{
    stepBackward();
    itteration --;
    if (itteration == 0){
      delay(3000);
      forward = true;
    }
  }
}

void stepForward(){
  digitalWrite(DIR, HIGH); //make the arm launch
  digitalWrite(DIS, LOW); //turn on the motor
  digitalWrite(PUL, HIGH); //give the voltage to pulse for the motor to move one step
  delayMicroseconds(microseconds_per_step); //max delay for rotation
  digitalWrite(PUL, LOW); //stop the voltage supply to the pulse
  delayMicroseconds(microseconds_per_step); //max delay for rotation
} 


void stepBackward(){
  digitalWrite(DIR, LOW); //make arm go backwards
  digitalWrite(DIS, LOW); //turn on the motor
  digitalWrite(PUL, HIGH); //give the voltage to pulse for the motor to move one step
  delayMicroseconds(microseconds_per_step); //max delay for rotation
  digitalWrite(PUL, LOW); //stop the voltage supply to the pulse
  delayMicroseconds(microseconds_per_step); //max delay for rotation
}
