package com.zilox.vjoy;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public interface VJoy extends Library {
	public static final int NO_HANDLE_BY_INDEX = -1;
	public static final int BAD_PREPARSED_DATA = -2;
	public static final int NO_CAPS = -3;
	public static final int BAD_N_BTN_CAPS = -4;
	public static final int BAD_CALLOC = -5;
	public static final int BAD_BTN_CAPS = -6;
	public static final int BAD_BTN_RANGE = -7;
	public static final int BAD_N_VAL_CAPS = -8;
	public static final int BAD_ID_RANGE = -9;
	public static final int NO_SUCH_AXIS = -10;
	public static final int BAD_DEV_STAT = -11;
	public static final int NO_DEV_EXIST = -12;
	public static final int NO_FILE_EXIST = -13;

	public static final int AXIS_MIN_VALUE = 0;
	public static final int AXIS_MID_VALUE = 16384;
	public static final int AXIS_MAX_VALUE = 32768;

	public static final int HID_USAGE_X = 0x30;
	public static final int HID_USAGE_Y = 0x31;
	public static final int HID_USAGE_Z = 0x32;
	public static final int HID_USAGE_RX = 0x33;
	public static final int HID_USAGE_RY = 0x34;
	public static final int HID_USAGE_RZ = 0x35;
	public static final int HID_USAGE_SL0 = 0x36;
	public static final int HID_USAGE_SL1 = 0x37;
	public static final int HID_USAGE_WHL = 0x38;
	public static final int HID_USAGE_POV = 0x39;

	public static final int HID_USAGE_CONST = 0x26;
	public static final int HID_USAGE_RAMP = 0x27;
	public static final int HID_USAGE_SQUR = 0x30;
	public static final int HID_USAGE_SINE = 0x31;
	public static final int HID_USAGE_TRNG = 0x32;
	public static final int HID_USAGE_STUP = 0x33;
	public static final int HID_USAGE_STDN = 0x34;
	public static final int HID_USAGE_SPRNG = 0x40;
	public static final int HID_USAGE_DMPR = 0x41;
	public static final int HID_USAGE_INRT = 0x42;
	public static final int HID_USAGE_FRIC = 0x43;

	public static final int HID_ID_STATE = 0x02;
	public static final int HID_ID_EFFREP = 0x01;
	public static final int HID_ID_ENVREP = 0x02;
	public static final int HID_ID_CONDREP = 0x03;
	public static final int HID_ID_PRIDREP = 0x04;
	public static final int HID_ID_CONSTREP = 0x05;
	public static final int HID_ID_RAMPREP = 0x06;
	public static final int HID_ID_CSTMREP = 0x07;
	public static final int HID_ID_SMPLREP = 0x08;
	public static final int HID_ID_EFOPREP = 0x0A;
	public static final int HID_ID_BLKFRREP = 0x0B;
	public static final int HID_ID_CTRLREP = 0x0C;
	public static final int HID_ID_GAINREP = 0x0D;
	public static final int HID_ID_SETCREP = 0x0E;
	public static final int HID_ID_NEWEFREP = 0x01;
	public static final int HID_ID_BLKLDREP = 0x02;
	public static final int HID_ID_POOLREP = 0x03;

	public short 	GetvJoyVersion();
	public boolean 	vJoyEnabled();
	public String 	GetvJoyProductString();
	public String 	GetvJoyManufacturerString();
	public String 	GetvJoySerialNumberString();
	public boolean 	DriverMatch(int DllVer, int DrvVer);
	public void 	RegisterRemovalCB(RemovalCB cb, Pointer data);
	public boolean 	vJoyFfbCap(boolean Supported);	// Is this version of vJoy capable of FFB?
	public boolean 	GetvJoyMaxDevices(int n);	// What is the maximum possible number of vJoy devices
	public boolean 	GetNumberExistingVJD(int n);	// What is the number of vJoy devices currently enabled

	/////	vJoy Device properties
	public int 		GetVJDButtonNumber(int rID);	// Get the number of buttons defined in the specified VDJ
	public int 		GetVJDDiscPovNumber(int rID);	// Get the number of descrete-type POV hats defined in the specified VDJ
	public int 		GetVJDContPovNumber(int rID);	// Get the number of descrete-type POV hats defined in the specified VDJ
	public boolean 	GetVJDAxisExist(int rID, int Axis); // Test if given axis defined in the specified VDJ
	public boolean 	GetVJDAxisMax(int rID, int Axis, long Max); // Get logical Maximum value for a given axis defined in the specified VDJ
	public boolean 	GetVJDAxisMin(int rID, int Axis, long Min); // Get logical Minimum value for a given axis defined in the specified VDJ
	public int 		GetVJDStatus(int rID); 	// Get the status of the specified vJoy Device.
	// Added in 2.1.6
	public boolean 	isVJDExists(int rID);  	// TRUE if the specified vJoy Device exists
	// Added in 2.1.8
	public int 		GetOwnerPid(int rID);  	// Reurn owner's Process ID if the specified vJoy Device exists

	/////	Write access to vJoy Device - Basic
	public boolean 	AcquireVJD(int rID);  // Acquire the specified vJoy Device.
	public void 	RelinquishVJD(int rID); 	// Relinquish the specified vJoy Device.
	public boolean 	UpdateVJD(int rID, Pointer pData);	// Update the position data of the specified vJoy Device.

	/////	Write access to vJoy Device - Modifyiers
	// This group of functions modify the current value of the position data
	// They replace the need to create a structure of position data then call UpdateVJD

	//// Reset functions
	public boolean 	ResetVJD(int rID); 	// Reset all controls to predefined values in the specified VDJ
	public void		ResetAll();  // Reset all controls to predefined values in all VDJ
	public boolean 	ResetButtons(int rID); // Reset all buttons (To 0) in the specified VDJ
	public boolean 	ResetPovs(int rID); // Reset all POV Switches (To -1) in the specified VDJ

	// Write data
	public boolean 	SetAxis(long Value, int rID,  int Axis); // Write Value to a given axis defined in the specified VDJ
	public boolean 	SetBtn(boolean Value, int rID, byte nBtn); // Write Value to a given button defined in the specified VDJ
	public boolean 	SetDiscPov(int Value, int rID, byte nPov);	// Write Value to a given descrete POV defined in the specified VDJ
	public boolean 	SetContPov(WinDef.DWORD Value, int rID, byte nPov);	// Write Value to a given continuous POV defined in the specified VDJ
}
