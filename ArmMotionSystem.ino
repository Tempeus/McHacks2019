const double STEPS_PER_ROTATION = 3200.0;
const double DEGREES_PER_ROTATION = 360.0;
const double MILLIMETER_PER_DEGREE = 2.0*3.1415926535899*300.0 / 360.0;
const double STEPS_PER_DEGREE = STEPS_PER_ROTATION / DEGREES_PER_ROTATION;
const double DEGREE_PER_STEPS = DEGREES_PER_ROTATION / STEPS_PER_ROTATION;
char inputBuffer[256];
double angle;
double velocity;

//Delay for the max speed
int microseconds_per_step = 300;

//X is the delay needed to get the appropriate velocity
double X = 0;

//target amount of steps from the angular origin to finish the throw
int stepPosTarget = 0;

//current amount of steps from angular origin
int stepPosition = 0;



//PULSE --> HIGH AND LOW TO MAKE THE STEPPER MOTOR MOVE
const int PUL = 11; //SETTING TO PIN 11

//DIRECTION --> HIGH FOR CLOCK WISE AND LOW FOR COUNTERCLOCK WISE
const int DIR = 12; //SETTING TO PIN 12

//ENABLE --> LOW FOR ENABLE CONNECTION WITH THE STEPPER MOTOR
const int DIS = 13; //SETTING TO PIN 13

//ENDSTOP --> DETECTS WHEN THE ARM IS PRESSING AGAINST THE SWITCH AT THE ORIGIN
const int ENDSTOP = 1; //SETTING TO PIN 1

//enumerating all the modes
enum modes {
  STANDBY,
  RESET,
  THROW
};

enum modes mode = STANDBY;

void setup() {
  //SETTING UP THE VARIABLES TO OUTPUT
  Serial.begin(9600); //initialize serial communicaiton from laptop to arduino at 9600 bits per second
  pinMode(PUL, OUTPUT);
  pinMode(DIR, OUTPUT);
  pinMode(DIS, OUTPUT);
  pinMode(ENDSTOP, INPUT);
}

void loop() {

  switch (mode) {
    case STANDBY: //the arm will not perform any actions until it is given an input from the user
      digitalWrite(DIS, LOW); //turning the motor off

      //Reads input stream FROM java TO ardiono to a char array
      Serial.readBytes(inputBuffer, 256);
      Serial.flush();

      if (sizeof(inputBuffer) != 0) {
        char sAngle[128];
        char sVel[128];

        //Assign sAngle and sVel accordingly
        for (int i = 0; i < 128; i++) {
          sAngle[i] = inputBuffer[i];
        }
        for (int i = 0; i < 128; i++) {
          sAngle[128 + i] = inputBuffer[128 + i];
        }

        //convert the angle and velocity strings into proper doubles
        angle = atof(sAngle);
        velocity = atof(sVel);
        
      }
      break;
      
    case RESET: //the arm will reset to it's original location
      int state = digitalRead(ENDSTOP); // 1 if the voltage at the pin is HIGH or when pressed, return 0 if the voltage at the pin is LOW when not pressed
      if (state = 1){
        mode = STANDBY;
        break;
      }
      stepBackward();
      //TODO
      break;
      
    case THROW: //the motor will turn on, making the arm launch the projectile
      if (stepPosition >= stepPosTarget){
        mode = STANDBY;
        break;   
      }
      stepForward();
      break;
      
    };
  
}

void stepForward(){
  digitalWrite(DIR, HIGH); //make the arm launch
  digitalWrite(DIS, LOW); //turn on the motor
  digitalWrite(PUL, HIGH); //give the voltage to pulse for the motor to move one step
  delayMicroseconds(microseconds_per_step); //max delay for rotation
  digitalWrite(PUL, LOW); //stop the voltage supply to the pulse
  delayMicroseconds(microseconds_per_step); //max delay for rotation
  stepPosition ++;
}


void stepBackward(){
  digitalWrite(DIR, LOW); //make arm go backwards
  digitalWrite(DIS, LOW); //turn on the motor
  digitalWrite(PUL, HIGH); //give the voltage to pulse for the motor to move one step
  delayMicroseconds(microseconds_per_step); //max delay for rotation
  digitalWrite(PUL, LOW); //stop the voltage supply to the pulse
  delayMicroseconds(microseconds_per_step); //max delay for rotation
  stepPosition --;
}

double getDelayPerStep(double angularVel, double finalAngle) {
  double seconds = finalAngle / angularVel; //time required to reach the target location
  double stepPosTarget = finalAngle * STEPS_PER_DEGREE; //steps required to move the object to target location
  double X = seconds / stepPosTarget;
  return (X);
}
