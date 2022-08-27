#define VERSION 'j'

#define INCLUDE_WS2812B
#define INCLUDE_TM1637
#define ENABLE_ADA_HT16K33_7SEGMENTS 0
#define ENABLE_ADA_HT16K33_BiColorMatrix 0
#define ADA_HT16K33_BICOLORMATRIX_I2CADDRESS 0x70
#define ENABLE_ADA_HT16K33_SingleColorMatrix 0
#define ADA_HT16K33_SINGLECOLORMATRIX_I2CADDRESS 0x70
#define I2CLCD_enabled 0
#define ENABLED_OLEDLCD 0
#define ENABLED_NOKIALCD 0
#define WS2812B_MATRIX_ENABLED 0

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

#define SEG_A	 0b00000001
#define SEG_B	 0b00000010
#define SEG_C	 0b00000100
#define SEG_D	 0b00001000
#define SEG_E	 0b00010000
#define SEG_F	 0b00100000
#define SEG_G	 0b01000000

#define DEVICE_NAME "Momo"
#define DEVICE_UNIQUE_ID "79aee007-36ef-446b-81ce-87aa07b40e51"

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
#define TM1637_DIO1 9
#define TM1637_CLK1 8
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

#define WS2812B_RGBLEDCOUNT 16 //16 leds

#define MAX7221_ENABLEDMODULES 0
#define MAX7221_DATA 3
#define MAX7221_CLK 5
#define MAX7221_LOAD 4

#define MAX7221_MATRIX_ENABLED 0
#define MAX7221_MATRIX_DATA 3
#define MAX7221_MATRIX_CLK 5
#define MAX7221_MATRIX_LOAD 4

#define WS2812B_DATAPIN 12
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
int BUTTON_PINS[] = { BUTTON_PIN_1, BUTTON_PIN_2, BUTTON_PIN_3, BUTTON_PIN_4, BUTTON_PIN_5, BUTTON_PIN_6, BUTTON_PIN_7, BUTTON_PIN_8, BUTTON_PIN_9, BUTTON_PIN_10, BUTTON_PIN_11, BUTTON_PIN_12};
SHButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
SHButton *BUTTONS[] = { &button1, &button2, &button3, &button4, &button5, &button6, &button7, &button8, &button9, &button10, &button11, &button12};
SHDebouncer ButtonsDebouncer(10);

#define ENABLED_BUTTONMATRIX 1
#define BMATRIX_COLS 4
#define BMATRIX_ROWS 4
#include "SHButtonMatrix.h"
#define BMATRIX_COL1 14
#define BMATRIX_COL2 15
#define BMATRIX_COL3 16
#define BMATRIX_COL4 17
#define BMATRIX_COL5 2
#define BMATRIX_COL6 2
#define BMATRIX_COL7 2
#define BMATRIX_COL8 2
#define BMATRIX_ROW1 6
#define BMATRIX_ROW2 7
#define BMATRIX_ROW3 18
#define BMATRIX_ROW4 19
#define BMATRIX_ROW5 2
#define BMATRIX_ROW6 2
#define BMATRIX_ROW7 2
#define BMATRIX_ROW8 2
byte BMATRIX_COLSDEF[8] = { BMATRIX_COL1, BMATRIX_COL2, BMATRIX_COL3, BMATRIX_COL4, BMATRIX_COL5, BMATRIX_COL6, BMATRIX_COL7, BMATRIX_COL8};
byte BMATRIX_ROWSDEF[8] = { BMATRIX_ROW1, BMATRIX_ROW2, BMATRIX_ROW3, BMATRIX_ROW4, BMATRIX_ROW5, BMATRIX_ROW6, BMATRIX_ROW7, BMATRIX_ROW8};
SHButtonMatrix shButtonMatrix;

#define ENCODER1_CLK_PIN 2					 
#define ENCODER1_DT_PIN 4
#define ENCODER1_BUTTON_PIN -1
#define ENCODER1_ENABLE_PULLUP 1
#define ENCODER1_REVERSE_DIRECTION 1
#define ENCODER1_ENABLE_HALFSTEPS 1
#define ENCODER2_CLK_PIN 3
#define ENCODER2_DT_PIN 5
#define ENCODER2_BUTTON_PIN -1
#define ENCODER2_ENABLE_PULLUP 1
#define ENCODER2_REVERSE_DIRECTION 1
#define ENCODER2_ENABLE_HALFSTEPS 1
#define ENABLED_ENCODERS_COUNT 2
#include "SHRotaryEncoder.h"
SHRotaryEncoder encoder1, encoder2, encoder3, encoder4, encoder5, encoder6, encoder7, encoder8;
SHRotaryEncoder *SHRotaryEncoders[] = { &encoder1, &encoder2, &encoder3, &encoder4, &encoder5, &encoder6, &encoder7, &encoder8 };

#include "SHCustomProtocol.h"
SHCustomProtocol shCustomProtocol;
#include "SHCommands.h"
#include "SHCommandsGlcd.h"

#define ENABLED_MATRIX_COLUMNS_ 4
#define ENABLED_MATRIX_ROWS_ 4

TM1637 tm1637(TM1637_CLK1, TM1637_DIO1);

void InitEncoders() {
  if (ENABLED_ENCODERS_COUNT > 0) encoder1.begin(ENCODER1_CLK_PIN, ENCODER1_DT_PIN, ENCODER1_BUTTON_PIN, ENCODER1_REVERSE_DIRECTION, ENCODER1_ENABLE_PULLUP, 1, ENCODER1_ENABLE_HALFSTEPS, EncoderPositionChanged);
  if (ENABLED_ENCODERS_COUNT > 1) encoder2.begin(ENCODER2_CLK_PIN, ENCODER2_DT_PIN, ENCODER2_BUTTON_PIN, ENCODER2_REVERSE_DIRECTION, ENCODER2_ENABLE_PULLUP, 2, ENCODER2_ENABLE_HALFSTEPS, EncoderPositionChanged);
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
	for (int i = 0; i < ENABLED_ENCODERS_COUNT; i++) {
		SHRotaryEncoders[i] -> read();
	}
	
	if (ButtonsDebouncer.Debounce())
	{
		bool changed = false;
		for (int btnIdx = 0; btnIdx < ENABLED_BUTTONS_COUNT; btnIdx++)
		{
			BUTTONS[btnIdx]->read();
		}

		shCustomProtocol.idle();
	}
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

	TM1637_Init();
	
	//Rotary
	InitEncoders();

	shRGBLedsWS2812B.begin(WS2812B_RGBLEDCOUNT, WS2812B_RIGHTTOLEFT, WS2812B_TESTMODE);

	//Matrix
	shButtonMatrix.begin(BMATRIX_COLS, BMATRIX_ROWS, BMATRIX_COLSDEF, BMATRIX_ROWSDEF, buttonMatrixStatusChanged);
	
	//Buttons
	/*
	for (int btnIdx = 0; btnIdx < ENABLED_BUTTONS_COUNT; btnIdx++) {
		BUTTONS[btnIdx] -> begin(btnIdx + 1, BUTTON_PINS[btnIdx], buttonStatusChanged);
	}
	*/

	//
	TM1637_screens[0]->rawDisplay(0, 32 | 16);
	TM1637_screens[0]->rawDisplay(1, 32 | 16 | 64 | 4);
	TM1637_screens[0]->rawDisplay(2, 4);
	TM1637_screens[0]->rawDisplay(3, 32 | 64 | 8 | 16);

	shCustomProtocol.setup();
	arqserial.setIdleFunction(idle);
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
		//reAttachInterrupts();
}
