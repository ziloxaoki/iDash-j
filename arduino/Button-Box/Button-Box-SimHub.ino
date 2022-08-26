#define VERSION 'j'
#define INCLUDE_ENCODERS
#define INCLUDE_BUTTONS
#define INCLUDE_BUTTONMATRIX
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

#define DEVICE_NAME "Button Box"
#define DEVICE_UNIQUE_ID "9f68e4b1-f763-440e-adcd-28696c5b0677"

#define ENABLED_BUTTONS_COUNT 2
#define BUTTON_PIN_1 23
#define BUTTON_PIN_2 22
#define BUTTON_PIN_3 0
#define BUTTON_PIN_4 0
#define BUTTON_PIN_5 0
#define BUTTON_PIN_6 0
#define BUTTON_PIN_7 0
#define BUTTON_PIN_8 0
#define BUTTON_PIN_9 0
#define BUTTON_PIN_10 0
#define BUTTON_PIN_11 0
#define BUTTON_PIN_12 0
int BUTTON_PINS[] = { BUTTON_PIN_1, BUTTON_PIN_2, BUTTON_PIN_3, BUTTON_PIN_4, BUTTON_PIN_5, BUTTON_PIN_6, BUTTON_PIN_7, BUTTON_PIN_8, BUTTON_PIN_9, BUTTON_PIN_10, BUTTON_PIN_11, BUTTON_PIN_12};
SHButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
SHButton *BUTTONS[] = { &button1, &button2, &button3, &button4, &button5, &button6, &button7, &button8, &button9, &button10, &button11, &button12};
SHDebouncer ButtonsDebouncer(10);

#define ENABLED_ENCODERS_COUNT 4
#include "SHRotaryEncoder.h"
#define ENCODER1_CLK_PIN 25
#define ENCODER1_DT_PIN 21
#define ENCODER1_BUTTON_PIN -1
#define ENCODER1_REVERSE_DIRECTION 0
#define ENCODER1_ENABLE_PULLUP 1
#define ENCODER1_ENABLE_HALFSTEPS 1
#define ENCODER2_CLK_PIN 24
#define ENCODER2_DT_PIN 20
#define ENCODER2_BUTTON_PIN -1
#define ENCODER2_ENABLE_PULLUP 1
#define ENCODER2_REVERSE_DIRECTION 0
#define ENCODER2_ENABLE_HALFSTEPS 1
#define ENCODER3_CLK_PIN 27
#define ENCODER3_DT_PIN 19
#define ENCODER3_BUTTON_PIN -1
#define ENCODER3_ENABLE_PULLUP 1
#define ENCODER3_REVERSE_DIRECTION 0
#define ENCODER3_ENABLE_HALFSTEPS 1
#define ENCODER4_CLK_PIN 26
#define ENCODER4_DT_PIN 18
#define ENCODER4_BUTTON_PIN -1
#define ENCODER4_ENABLE_PULLUP 1
#define ENCODER4_REVERSE_DIRECTION 0
#define ENCODER4_ENABLE_HALFSTEPS 1
#define ENCODER5_CLK_PIN 0
#define ENCODER5_DT_PIN 0
#define ENCODER5_BUTTON_PIN -1
#define ENCODER5_ENABLE_PULLUP 0
#define ENCODER5_REVERSE_DIRECTION 0
#define ENCODER5_ENABLE_HALFSTEPS 0
#define ENCODER6_CLK_PIN 0
#define ENCODER6_DT_PIN 0
#define ENCODER6_BUTTON_PIN -1
#define ENCODER6_ENABLE_PULLUP 0
#define ENCODER6_REVERSE_DIRECTION 0
#define ENCODER6_ENABLE_HALFSTEPS 0
#define ENCODER7_CLK_PIN 0
#define ENCODER7_DT_PIN 0
#define ENCODER7_BUTTON_PIN -1
#define ENCODER7_ENABLE_PULLUP 0
#define ENCODER7_REVERSE_DIRECTION 0
#define ENCODER7_ENABLE_HALFSTEPS 0
#define ENCODER8_CLK_PIN 0
#define ENCODER8_DT_PIN 0
#define ENCODER8_BUTTON_PIN -1
#define ENCODER8_ENABLE_PULLUP 0
#define ENCODER8_REVERSE_DIRECTION 0
#define ENCODER8_ENABLE_HALFSTEPS 0
SHRotaryEncoder encoder1, encoder2, encoder3, encoder4, encoder5, encoder6, encoder7, encoder8;
SHRotaryEncoder *SHRotaryEncoders[] = { &encoder1, &encoder2, &encoder3, &encoder4, &encoder5, &encoder6, &encoder7, &encoder8
};

#define ENABLED_BUTTONMATRIX 1
#define BMATRIX_COLS 7
#define BMATRIX_ROWS 7
#include "SHButtonMatrix.h"
#define BMATRIX_COL1 29
#define BMATRIX_COL2 30
#define BMATRIX_COL3 31
#define BMATRIX_COL4 32
#define BMATRIX_COL5 33
#define BMATRIX_COL6 34
#define BMATRIX_COL7 35
#define BMATRIX_COL8 0
#define BMATRIX_ROW1 40
#define BMATRIX_ROW2 41
#define BMATRIX_ROW3 42
#define BMATRIX_ROW4 43
#define BMATRIX_ROW5 44
#define BMATRIX_ROW6 45
#define BMATRIX_ROW7 46
#define BMATRIX_ROW8 0
byte BMATRIX_COLSDEF[8] = { BMATRIX_COL1, BMATRIX_COL2, BMATRIX_COL3, BMATRIX_COL4, BMATRIX_COL5, BMATRIX_COL6, BMATRIX_COL7, BMATRIX_COL8
};
byte BMATRIX_ROWSDEF[8] = { BMATRIX_ROW1, BMATRIX_ROW2, BMATRIX_ROW3, BMATRIX_ROW4, BMATRIX_ROW5, BMATRIX_ROW6, BMATRIX_ROW7, BMATRIX_ROW8
};
SHButtonMatrix shButtonMatrix;

#include "SHLedsBackpack.h"

#include "SHCustomProtocol.h"
SHCustomProtocol shCustomProtocol;

#include "SHCommands.h"
#include "SHCommandsGlcd.h"

void InitEncoders()
{
  if (ENABLED_ENCODERS_COUNT > 0) encoder1.begin(ENCODER1_CLK_PIN, ENCODER1_DT_PIN, ENCODER1_BUTTON_PIN, ENCODER1_REVERSE_DIRECTION, ENCODER1_ENABLE_PULLUP, 1, ENCODER1_ENABLE_HALFSTEPS, EncoderPositionChanged);
  if (ENABLED_ENCODERS_COUNT > 1) encoder2.begin(ENCODER2_CLK_PIN, ENCODER2_DT_PIN, ENCODER2_BUTTON_PIN, ENCODER2_REVERSE_DIRECTION, ENCODER2_ENABLE_PULLUP, 2, ENCODER2_ENABLE_HALFSTEPS, EncoderPositionChanged);
  if (ENABLED_ENCODERS_COUNT > 2) encoder3.begin(ENCODER3_CLK_PIN, ENCODER3_DT_PIN, ENCODER3_BUTTON_PIN, ENCODER3_REVERSE_DIRECTION, ENCODER3_ENABLE_PULLUP, 3, ENCODER3_ENABLE_HALFSTEPS, EncoderPositionChanged);
  if (ENABLED_ENCODERS_COUNT > 3) encoder4.begin(ENCODER4_CLK_PIN, ENCODER4_DT_PIN, ENCODER4_BUTTON_PIN, ENCODER4_REVERSE_DIRECTION, ENCODER4_ENABLE_PULLUP, 4, ENCODER4_ENABLE_HALFSTEPS, EncoderPositionChanged);
  if (ENABLED_ENCODERS_COUNT > 4) encoder5.begin(ENCODER5_CLK_PIN, ENCODER5_DT_PIN, ENCODER5_BUTTON_PIN, ENCODER5_REVERSE_DIRECTION, ENCODER5_ENABLE_PULLUP, 5, ENCODER5_ENABLE_HALFSTEPS, EncoderPositionChanged);
  if (ENABLED_ENCODERS_COUNT > 5) encoder6.begin(ENCODER6_CLK_PIN, ENCODER6_DT_PIN, ENCODER6_BUTTON_PIN, ENCODER6_REVERSE_DIRECTION, ENCODER6_ENABLE_PULLUP, 6, ENCODER6_ENABLE_HALFSTEPS, EncoderPositionChanged);
  if (ENABLED_ENCODERS_COUNT > 6) encoder7.begin(ENCODER7_CLK_PIN, ENCODER7_DT_PIN, ENCODER7_BUTTON_PIN, ENCODER7_REVERSE_DIRECTION, ENCODER7_ENABLE_PULLUP, 7, ENCODER7_ENABLE_HALFSTEPS, EncoderPositionChanged);
  if (ENABLED_ENCODERS_COUNT > 7) encoder8.begin(ENCODER8_CLK_PIN, ENCODER8_DT_PIN, ENCODER8_BUTTON_PIN, ENCODER8_REVERSE_DIRECTION, ENCODER8_ENABLE_PULLUP, 8, ENCODER8_ENABLE_HALFSTEPS, EncoderPositionChanged);
}

void EncoderPositionChanged(int encoderId, int position, byte direction)
{
  if (direction < 2)
  {
    arqserial.CustomPacketStart(0x01, 3);
    arqserial.CustomPacketSendByte(encoderId);
    arqserial.CustomPacketSendByte(direction);
    arqserial.CustomPacketSendByte(position);
    arqserial.CustomPacketEnd();
  }
  else
  {
    arqserial.CustomPacketStart(0x02, 2);
    arqserial.CustomPacketSendByte(encoderId);
    arqserial.CustomPacketSendByte(direction - 2);
    arqserial.CustomPacketEnd();
  }
}

void idle(bool critical)
{
  shButtonMatrix.read();
  for (int i = 0; i < ENABLED_ENCODERS_COUNT; i++)
  {
    SHRotaryEncoders[i]->read();
  }
  
  if (ButtonsDebouncer.Debounce())
  {
    for (int btnIdx = 0; btnIdx < ENABLED_BUTTONS_COUNT; btnIdx++)
    {
      BUTTONS[btnIdx]->read();
    }

    shCustomProtocol.idle();
  }
}

void buttonStatusChanged(int buttonId, byte Status) {
  arqserial.CustomPacketStart(0x03, 2);
  arqserial.CustomPacketSendByte(buttonId);
  arqserial.CustomPacketSendByte(Status);
  arqserial.CustomPacketEnd();
}

void buttonMatrixStatusChanged(int buttonId, byte Status)
{
  arqserial.CustomPacketStart(0x03, 2);
  arqserial.CustomPacketSendByte(ENABLED_BUTTONS_COUNT + buttonId);
  arqserial.CustomPacketSendByte(Status);
  arqserial.CustomPacketEnd();
}

void setup()
{
  FlowSerialBegin(19200);
  
  //Rotary
  InitEncoders();
  
  //Matrix
  shButtonMatrix.begin(BMATRIX_COLS, BMATRIX_ROWS, BMATRIX_COLSDEF, BMATRIX_ROWSDEF, buttonMatrixStatusChanged);
  
  //Buttons
  for (int btnIdx = 0; btnIdx < ENABLED_BUTTONS_COUNT; btnIdx++)
  {
    BUTTONS[btnIdx]->begin(btnIdx + 1, BUTTON_PINS[btnIdx], buttonStatusChanged);
  } 
  
  shCustomProtocol.setup();
  arqserial.setIdleFunction(idle);
}

char loop_opt;
void loop()
{
  shCustomProtocol.loop();

  if (FlowSerialAvailable() > 0)
  {
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
      else if (loop_opt == '0') Command_Features();
      else if (loop_opt == '3') Command_TM1638Data();
      else if (loop_opt == 'V') Command_Motors();
      else if (loop_opt == 'S') Command_7SegmentsData();
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
}