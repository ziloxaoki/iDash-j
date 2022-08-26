#define VERSION 'j'


#define INCLUDE_WS2812B
#define INCLUDE_TM1637

#include <avr/pgmspace.h>
#include <EEPROM.h>
#include <SPI.h>
#include "Arduino.h"
#include <avr/pgmspace.h>
#include <Wire.h>
#include "Adafruit_GFX.h"
#include "FlowSerialRead.h"
#include "setPwmFrequency.h"
#include "SHDebouncer.h"
#include "SHButton.h"
#include <Encoder.h>
#include <Keypad.h>

#define SEG_A   0b00000001
#define SEG_B   0b00000010
#define SEG_C   0b00000100
#define SEG_D   0b00001000
#define SEG_E   0b00010000
#define SEG_F   0b00100000
#define SEG_G   0b01000000

#define DEVICE_NAME "Volante"
#define DEVICE_UNIQUE_ID "f6c2a5c6-e01b-4553-bfca-3ca865900067"

#define ENABLE_MICRO_GAMEPAD 0
#define MICRO_GAMEPAD_ENCODERPRESSTIME 50

#define TM1638_ENABLEDMODULES 0
#define TM1638_SWAPLEDCOLORS 0
#define TM1638_DIO 8
#define TM1638_CLK 7
#define TM1638_STB1 9
#define TM1638_SINGLECOLOR1 0
#define TM1638_STB2 10
#define TM1638_SINGLECOLOR2 0
#define TM1638_STB3 11
#define TM1638_SINGLECOLOR3 0
#define TM1638_STB4 12
#define TM1638_SINGLECOLOR4 0
#define TM1638_STB5 0
#define TM1638_SINGLECOLOR5 0
#define TM1638_STB6 0
#define TM1638_SINGLECOLOR6 0

#define TM1637_ENABLEDMODULES 1

#define TM1637_DIO1 A0
#define TM1637_CLK1 9
#define TM1637_DIO2 4
#define TM1637_CLK2 3
#define TM1637_DIO3 4
#define TM1637_CLK3 3
#define TM1637_DIO4 4
#define TM1637_CLK4 3
#define TM1637_DIO5 4
#define TM1637_CLK5 3
#define TM1637_DIO6 4
#define TM1637_CLK6 3
#define TM1637_DIO7 4
#define TM1637_CLK7 3
#define TM1637_DIO8 4
#define TM1637_CLK8 3

#include "SHTM1637.h"


#define MAX7221_ENABLEDMODULES 0
#define MAX7221_DATA 3
#define MAX7221_CLK 5
#define MAX7221_LOAD 4

#define MAX7221_MATRIX_ENABLED 0
#define MAX7221_MATRIX_DATA 3
#define MAX7221_MATRIX_CLK 5
#define MAX7221_MATRIX_LOAD 4


#define ENABLE_ADA_HT16K33_SingleColorMatrix 0
#define ADA_HT16K33_SINGLECOLORMATRIX_I2CADDRESS 0x70

#define WS2812B_RGBLEDCOUNT 16

#define WS2812B_DATAPIN 22
#define WS2812B_RGBENCODING 0
#define WS2812B_RIGHTTOLEFT 1
#include "SHRGBLedsNeoPixelFastLed.h"
SHRGBLedsNeoPixelFastLeds shRGBLedsWS2812B;
#define WS2812B_TESTMODE 0

#define PL9823_RGBLEDCOUNT 0
#define PL9823_DATAPIN 6
#define PL9823_RIGHTTOLEFT 0
#define PL9823_TESTMODE 0

#define WS2801_RGBLEDCOUNT 0
#define WS2801_RIGHTTOLEFT 0
#define WS2801_DATAPIN 5
#define WS2801_CLOCKPIN 6
#define WS2801_TESTMODE 0

#define WS2812B_MATRIX_ENABLED 0

#define WS2812B_MATRIX_DATAPIN 6

#define I2CLCD_enabled 0
#define I2CLCD_size 0
#define I2CLCD_ADDRESS 0x3f
#define I2CLCD_LIBRARY 0
#define I2CLCD_TEST 0
#define I2CLCD_20x4
#define I2CLCD_WIDTH 20
//#define I2CLCD_HEIGHT 4
//#define I2CLCD_WIDTH 16
#define I2CLCD_HEIGHT 2
#define I2CLCD_PCF8574AT

#define ENABLE_ADA_HT16K33_7SEGMENTS 0

#define ENABLE_ADA_HT16K33_BiColorMatrix 0
#define ADA_HT16K33_BICOLORMATRIX_I2CADDRESS 0x70


#define ENABLE_TACHOMETER 0
#define TACHOMETER_PIN 9

#define ENABLE_SPEEDOGAUGE 0
#define SPEEDO_PIN 4

#define ENABLE_BOOSTGAUGE 0
#define BOOST_PIN 5

#define ENABLE_TEMPGAUGE 0
#define TEMP_PIN 5

#define ENABLE_FUELGAUGE 0
#define FUEL_PIN 5

#define ENABLE_CONSGAUGE 0
#define CONS_PIN 5

#define ENABLED_BUTTONS_COUNT 0
#define BUTTON_PIN_1 3
#define BUTTON_PIN_2 3
#define BUTTON_PIN_3 3
#define BUTTON_PIN_4 3
#define BUTTON_PIN_5 3
#define BUTTON_PIN_6 3
#define BUTTON_PIN_7 3
#define BUTTON_PIN_8 3
#define BUTTON_PIN_9 3
#define BUTTON_PIN_10 3
#define BUTTON_PIN_11 3
#define BUTTON_PIN_12 3

SHDebouncer ButtonsDebouncer(10);

#define ENABLED_ENCODERS_COUNT 0
#define ENCODER1_CLK_PIN 7
#define ENCODER1_DT_PIN 8
#define ENCODER1_BUTTON_PIN 9
#define ENCODER1_REVERSE_DIRECTION 0
#define ENCODER1_ENABLE_PULLUP 0
#define ENCODER1_ENABLE_HALFSTEPS 0
#define ENCODER2_CLK_PIN 11
#define ENCODER2_DT_PIN 12
#define ENCODER2_BUTTON_PIN 13
#define ENCODER2_ENABLE_PULLUP 0
#define ENCODER2_REVERSE_DIRECTION 0
#define ENCODER2_ENABLE_HALFSTEPS 0
#define ENCODER3_CLK_PIN 7
#define ENCODER3_DT_PIN 8
#define ENCODER3_BUTTON_PIN 9
#define ENCODER3_ENABLE_PULLUP 0
#define ENCODER3_REVERSE_DIRECTION 0
#define ENCODER3_ENABLE_HALFSTEPS 0
#define ENCODER4_CLK_PIN 7
#define ENCODER4_DT_PIN 8
#define ENCODER4_BUTTON_PIN 9
#define ENCODER4_ENABLE_PULLUP 0
#define ENCODER4_REVERSE_DIRECTION 0
#define ENCODER4_ENABLE_HALFSTEPS 0
#define ENCODER5_CLK_PIN 7
#define ENCODER5_DT_PIN 8
#define ENCODER5_BUTTON_PIN 9
#define ENCODER5_ENABLE_PULLUP 0
#define ENCODER5_REVERSE_DIRECTION 0
#define ENCODER5_ENABLE_HALFSTEPS 0
#define ENCODER6_CLK_PIN 7
#define ENCODER6_DT_PIN 8
#define ENCODER6_BUTTON_PIN 9
#define ENCODER6_ENABLE_PULLUP 0
#define ENCODER6_REVERSE_DIRECTION 0
#define ENCODER6_ENABLE_HALFSTEPS 0
#define ENCODER7_CLK_PIN 7
#define ENCODER7_DT_PIN 8
#define ENCODER7_BUTTON_PIN 9
#define ENCODER7_ENABLE_PULLUP 0
#define ENCODER7_REVERSE_DIRECTION 0
#define ENCODER7_ENABLE_HALFSTEPS 0
#define ENCODER8_CLK_PIN 7
#define ENCODER8_DT_PIN 8
#define ENCODER8_BUTTON_PIN 9
#define ENCODER8_ENABLE_PULLUP 0
#define ENCODER8_REVERSE_DIRECTION 0
#define ENCODER8_ENABLE_HALFSTEPS 0


#define ENABLED_BUTTONMATRIX 0

#define BMATRIX_COLS 3
#define BMATRIX_ROWS 3

#define BMATRIX_COL1 2
#define BMATRIX_COL2 3
#define BMATRIX_COL3 4
#define BMATRIX_COL4 5
#define BMATRIX_COL5 2
#define BMATRIX_COL6 2
#define BMATRIX_COL7 2
#define BMATRIX_COL8 2
#define BMATRIX_ROW1 6
#define BMATRIX_ROW2 7
#define BMATRIX_ROW3 8
#define BMATRIX_ROW4 9
#define BMATRIX_ROW5 2
#define BMATRIX_ROW6 2
#define BMATRIX_ROW7 2
#define BMATRIX_ROW8 2


#define ADAMOTORS_SHIELDSCOUNT 0
#define ADAMOTORS_FREQ 1900

#define MOTOMONSTER_ENABLED 0
#define MOTOMONSTER_REVERSEDIRECTION 0

#define DKMOTOR_SHIELDSCOUNT 0
#define DKMOTOR_USEHUMMINGREDUCING 0

#define L98NMOTORS_ENABLED 0
#define L98N_enA 10
#define L98N_in1 9
#define L98N_in2 8
#define L98N_enB 5
#define L98N_in3 7
#define L98N_in4 6

#define SHAKEITPWM_ENABLED_MOTORS 0
#define SHAKEITPWM_O1 5
#define SHAKEITPWM_MIN_OUTPUT_O1 0
#define SHAKEITPWM_MAX_OUTPUT_O1 255
#define SHAKEITPWM_O2 6
#define SHAKEITPWM_MIN_OUTPUT_O2 0
#define SHAKEITPWM_MAX_OUTPUT_O2 255
#define SHAKEITPWM_O3 9
#define SHAKEITPWM_MIN_OUTPUT_O3 0
#define SHAKEITPWM_MAX_OUTPUT_O3 255
#define SHAKEITPWM_O4 10
#define SHAKEITPWM_MIN_OUTPUT_O4 0
#define SHAKEITPWM_MAX_OUTPUT_O4 255



#define SHAKEITPWMFANS_ENABLED_MOTORS 0


#define SHAKEITPWMFANS_O1 9
#define SHAKEITPWMFANS_MIN_OUTPUT_O1 0
#define SHAKEITPWMFANS_MAX_OUTPUT_O1 255
#define SHAKEITPWMFANS_O2 10
#define SHAKEITPWMFANS_MIN_OUTPUT_O2 0
#define SHAKEITPWMFANS_MAX_OUTPUT_O2 255
#define SHAKEITPWMFANS_O3 11
#define SHAKEITPWMFANS_MIN_OUTPUT_O3 0
#define SHAKEITPWMFANS_MAX_OUTPUT_O3 255
#define SHAKEITPWMFANS_O4 10
#define SHAKEITPWMFANS_MIN_OUTPUT_O4 0
#define SHAKEITPWMFANS_MAX_OUTPUT_O4 255


#define ENABLED_OLEDLCD 0
#define ENABLED_NOKIALCD 0


#define ENABLED_NOKIALCD 0

#define ENABLED_OLEDLCD 0

#define ENABLE_74HC595_GEAR_DISPLAY 0
#define RS_74HC595_DATAPIN 2
#define RS_74HC595_LATCHPIN 3
#define RS_74HC595_CLOCKPIN 4

#define ENABLE_6C595_GEAR_DISPLAY 0
#define RS_6c595_DATAPIN 11
#define RS_6c595_LATCHPIN 13
#define RS_6c595_SLAVEPIN 10

#include "SHLedsBackpack.h"

#include "SHCustomProtocol.h"
SHCustomProtocol shCustomProtocol;
#include "SHCommands.h"
#include "SHCommandsGlcd.h"

#define ENABLED_MATRIX_COLUMNS_ 3
#define ENABLED_MATRIX_ROWS_ 4
#define TOTAL_ROTARY_ 4

byte rowPins[] = {0, 1, 11, 15}; //output
byte columnPins[] = {12, 13, 14}; //pull-up
long lastknobState[] = {0, 0, 0, 0};
long knobState[] = {0, 0, 0, 0};
long lastKnobPosition[] = { -999, -999, -999, -999};
byte buttonNumber = 1;
long lastKnobStateChange = 0;

byte thumbstick1[] = {A1, A2};
byte thumbstick2[] = {A3, A4};

TM1637 tm1637(TM1637_CLK1, TM1637_DIO1);
//SevenSegmentTM1637 tm1637(TM1637_CLK1, TM1637_DIO1);

Encoder knob1(5, 16);
Encoder knob2(6, 3);
Encoder knob3(7, 4);
Encoder knob4(8, 2);

#define ANALOG_CENTER 512
int Xstick;
int Ystick;
int Zstick;
int Zstickrotate;
int upperBound=550;
int lowerBound=400;

void(* resetFunc) (void) = 0;

void initOutputPins() {
  for (byte x = 0; x < ENABLED_MATRIX_ROWS_; x++) {
    pinMode(rowPins[x], OUTPUT);
    digitalWrite(rowPins[x], HIGH);        // initiate high
  }
}

void resetArduino() {
	pinMode(rowPins[0], OUTPUT);
	pinMode(rowPins[2], OUTPUT);
	digitalWrite(rowPins[0], LOW);
	digitalWrite(rowPins[2], LOW);
	
	if (digitalRead(columnPins[1]) == LOW && digitalRead(columnPins[0]) == LOW) {
		//Serial.println("=================RESET");
		resetFunc();
	}
}

void sendMatrixState() {
  for (byte i = 0; i < ENABLED_MATRIX_ROWS_; i++) {
    initOutputPins();
    digitalWrite(rowPins[i], LOW);	
	
    for (byte x = 0; x < ENABLED_MATRIX_COLUMNS_; x++) {
      byte pinState = (digitalRead(columnPins[x]) == LOW) ? 1 : 0;
      Joystick.button(buttonNumber++, pinState);
      Joystick.send_now();
    }
    //delay(10);
	
	/*
	for(byte x=0; x<ENABLED_MATRIX_COLUMNS_; x++) {
		byte pinState = (digitalRead(columnPins[x]) == LOW) ? 1 : 0;
		Serial.print(i);
		Serial.print(x);
		Serial.print(" = ");
		Serial.print(pinState);
		Serial.println();
	}
	
	delay(10);
	*/
  }
}

void sendKnobState() {
  long newKnobPosition[] = {0, 0, 0, 0};

  newKnobPosition[0] = knob1.read();
  newKnobPosition[1] = knob2.read();
  newKnobPosition[2] = knob3.read();
  newKnobPosition[3] = knob4.read();

  for (byte i = 0; i < TOTAL_ROTARY_; i++) {
    if (millis() - lastKnobStateChange > 50) {
      if (lastKnobPosition[i] > newKnobPosition[i] + 3 || lastKnobPosition[i] < newKnobPosition[i] - 3) {
        if (lastKnobPosition[i] < newKnobPosition[i]) {
          //turn left
          knobState[i] = 1;
        } else {
          //turn right
          knobState[i] = 2;
        }
        lastKnobPosition[i] = newKnobPosition[i];

        lastKnobStateChange = millis();
      } else {
        //not pressed
        knobState[i] = 0;
      }
    }

    switch (knobState[i]) {
      case 0:
        Joystick.button(buttonNumber++, 0);
        Joystick.button(buttonNumber++, 0);
        Joystick.send_now();
        break;
      case 1:
        Joystick.button(buttonNumber++, 0);
        Joystick.button(buttonNumber++, 1);
        Joystick.send_now();
        break;
      case 2:
        Joystick.button(buttonNumber++, 1);
        Joystick.button(buttonNumber++, 0);
        Joystick.send_now();
        break;
    }
  }
}

void idle(bool critical) {


  if (ButtonsDebouncer.Debounce()) {
    bool changed = false;
    shCustomProtocol.idle();
  }
}


void buttonStatusChanged(int buttonId, byte Status) {
  arqserial.CustomPacketStart(0x03, 2);
  arqserial.CustomPacketSendByte(buttonId);
  arqserial.CustomPacketSendByte(Status);
  arqserial.CustomPacketEnd();

}

void setup()
{
  FlowSerialBegin(19200);

  TM1637_Init();

  shRGBLedsWS2812B.begin(WS2812B_RGBLEDCOUNT, WS2812B_RIGHTTOLEFT, WS2812B_TESTMODE);

  shCustomProtocol.setup();
  arqserial.setIdleFunction(idle);

  Joystick.useManualSend(true);
  for (byte x = 0; x < ENABLED_MATRIX_COLUMNS_; x++) {
    pinMode(columnPins[x], INPUT_PULLUP);           // set pin to input
  }

  TM1637_screens[0]->rawDisplay(0, 32 | 16);
  TM1637_screens[0]->rawDisplay(1, 32 | 16 | 64 | 4);
  TM1637_screens[0]->rawDisplay(2, 4);
  TM1637_screens[0]->rawDisplay(3, 32 | 64 | 8 | 16);
}


void sendAnalogicState() {
  Xstick = analogRead(A1);
  Ystick = analogRead(A2);

  if ((Xstick > 512 && Xstick <= upperBound) || (Xstick < 512 && Xstick >= lowerBound)) {
    Xstick = 512;
  }

  if ((Ystick > 512 && Ystick <= upperBound) || (Ystick < 512 && Ystick >= lowerBound)) {
    Ystick = 512;
  }

  //Xstick = (Xstick - 511) * 2 + 511;
  // cap result
  //if (Xstick > 1023)
  //  Xstick = 1023;
  //if (Xstick < 0)
  //  Xstick = 0;

  Joystick.X(Xstick);
  Joystick.Y(Ystick);

  // Left Analogue Stick
  Zstick = analogRead(A3);
  Zstickrotate = analogRead(A4);

  if ((Zstick > 512 && Zstick <= upperBound) || (Zstick < 512 && Zstick >= lowerBound)) {
    Zstick = 512;
  }

  if ((Zstickrotate > 512 && Zstickrotate <= upperBound) || (Zstickrotate < 512 && Zstickrotate >= lowerBound)) {
    Zstickrotate = 512;
  }
  
  Joystick.Z(Zstick);
  Joystick.Zrotate(Zstickrotate);
}

char loop_opt;
void loop() {
  shCustomProtocol.loop();

  if (FlowSerialAvailable() > 0) {
    if (FlowSerialTimedRead() == MESSAGE_HEADER)
    {

      loop_opt = FlowSerialTimedRead();

      if (loop_opt == '1') Command_Hello();
      else if (loop_opt == '8') Command_SetBaudrate();
      else if (loop_opt == 'J') Command_ButtonsCount();
      else if (loop_opt == '2') Command_TM1638Count();
      else if (loop_opt == 'B') Command_SimpleModulesCount();
      else if (loop_opt == 'A') Command_Acq();
      else if (loop_opt == 'N') Command_DeviceName();
      else if (loop_opt == 'I') Command_UniqueId();
      else if (loop_opt == '0') Command_Features();
      else if (loop_opt == '3') Command_TM1638Data();
      else if (loop_opt == 'V') Command_Motors();
      else if (loop_opt == 'S') Command_7SegmentsData();
      //else if (loop_opt == 'S') TM1637_Init();
      else if (loop_opt == '4') Command_RGBLEDSCount();
      else if (loop_opt == '6') Command_RGBLEDSData();
      else if (loop_opt == 'R') Command_RGBMatrixData();
      else if (loop_opt == 'M') Command_MatrixData();
      else if (loop_opt == 'G') Command_GearData();
      else if (loop_opt == 'L') Command_I2CLCDData();
      else if (loop_opt == 'K') Command_GLCDData();
      else if (loop_opt == 'P') Command_CustomProtocolData();
      else if (loop_opt == 'X')
      {
        String xaction = FlowSerialReadStringUntil(' ', '\n');
        if (xaction == F("list")) Command_ExpandedCommandsList();
        else if (xaction == F("mcutype")) Command_MCUType();
        else if (xaction == F("tach")) Command_TachData();
        else if (xaction == F("speedo")) Command_SpeedoData();
        else if (xaction == F("boost")) Command_BoostData();
        else if (xaction == F("temp")) Command_TempData();
        else if (xaction == F("fuel")) Command_FuelData();
        else if (xaction == F("cons")) Command_ConsData();
        else if (xaction == F("encoderscount")) Command_EncodersCount();
      }
    }
  }
  buttonNumber = 1;
  sendMatrixState();
  sendKnobState();
  sendAnalogicState();
  resetArduino();
}
