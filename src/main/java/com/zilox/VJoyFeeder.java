package com.zilox;

import com.sun.jna.Native;
import com.zilox.command.Command;
import com.zilox.command.CommandHandler;
import com.zilox.command.SharedCommandQueueManager;
import com.zilox.vjoy.ButtonHandler;
import com.zilox.vjoy.ButtonState;
import com.zilox.vjoy.VJoy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.zilox.Constants.DEBUG_MSG_DEBOUNCE_TIME;
import static com.zilox.command.Command.CMD_BUTTON_STATUS;
import static com.zilox.vjoy.HIDUsages.*;
import static com.zilox.vjoy.VjdStat.*;

public class VJoyFeeder implements CommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(VJoyFeeder.class);
    private Debouncer debugMsgDebouncer = new Debouncer(DEBUG_MSG_DEBOUNCE_TIME);
    // Declaring one joystick (Device id 1) and a position structure. 
    private static int CENTER = 16384;
    //private static int AXIS_OFFSET = 4;
    private static int AXIS_OFFSET = 0;
    public VJoy joystick;
    public int jID;
    private int axisX = 0, axisY = 0;
    private final Lock processLock = new ReentrantLock();
    private SharedCommandQueueManager sharedCommandQueueManager;
    private ButtonHandler buttonHandler;

    public VJoyFeeder(int vjoyId, SharedCommandQueueManager sharedCommandQueueManager)
    {
        jID = vjoyId;
        this.sharedCommandQueueManager = sharedCommandQueueManager;
        this.initializeJoystick();
        buttonHandler = new ButtonHandler(joystick.GetVJDButtonNumber(jID));
    }

    private void initializeJoystick() {
        if (joystick == null)
            joystick = Native.load("./native/64/vJoyInterface",
                    VJoy.class);

        // Get the driver attributes (Vendor ID, Product ID, Version Number)
        if (!joystick.vJoyEnabled()) {
            LOGGER.warn("\nvJoy driver not enabled: Failed Getting vJoy attributes.");
            return;
        } else {
            System.out.println(String.format("Vendor: {%s} Product :{%s} Version Number:{%s}", joystick.GetvJoyManufacturerString(), joystick.GetvJoyProductString(), joystick.GetvJoySerialNumberString()));
            // Get the state of the requested device
            int status = joystick.GetVJDStatus(jID);
            switch (status) {
                case VJD_STAT_OWN:
                    System.out.println(String.format("vJoy Device {%s} is already owned by this feeder", jID));
                    break;
                case VJD_STAT_FREE:
                    System.out.println(String.format("vJoy Device {%s} is free", jID));
                    break;
                case VJD_STAT_BUSY:
                    System.out.println(String.format("vJoy Device {%s} is already owned by another feeder. Cannot continue", jID));
                    return;
                case VJD_STAT_MISS:
                    System.out.println(String.format("vJoy Device {%s} is not installed or disabled. Cannot continue", jID));
                    return;
                default:
                    System.out.println(String.format("vJoy Device {%s} general error. Cannot continue", jID));
                    return;
            }

            // Check which axes are supported
            boolean AxisX = joystick.GetVJDAxisExist(jID, HID_USAGE_X.getValue());
            boolean AxisY = joystick.GetVJDAxisExist(jID, HID_USAGE_Y.getValue());
            boolean AxisZ = joystick.GetVJDAxisExist(jID, HID_USAGE_Z.getValue());
            boolean AxisRX = joystick.GetVJDAxisExist(jID, HID_USAGE_RX.getValue());
            boolean AxisRZ = joystick.GetVJDAxisExist(jID, HID_USAGE_RZ.getValue());
            // Get the number of buttons and POV Hat switchessupported by this vJoy device
            int nButtons = joystick.GetVJDButtonNumber(jID);
            int ContPovNumber = joystick.GetVJDContPovNumber(jID);
            int DiscPovNumber = joystick.GetVJDDiscPovNumber(jID);

            // Print results
            System.out.println(String.format("vJoy Device {%s} capabilities:", jID));
            System.out.println(String.format("Number of buttons\t{%s}", nButtons));
            System.out.println(String.format("Number of Continuous POVs\t{%s}", ContPovNumber));
            System.out.println(String.format("Number of Descrete POVs\t{%s}", DiscPovNumber));
            System.out.println(String.format("Axis X\t\t{%s}", AxisX ? "Yes" : "No"));
            System.out.println(String.format("Axis Y\t\t{%s}", AxisY ? "Yes" : "No"));
            System.out.println(String.format("Axis Z\t\t{%s}", AxisZ ? "Yes" : "No"));
            System.out.println(String.format("Axis Rx\t\t{%s}", AxisRX ? "Yes" : "No"));
            System.out.println(String.format("Axis Rz\t\t{%s}", AxisRZ ? "Yes" : "No"));

            // Acquire the target
            if ((status == VJD_STAT_OWN) || (status == VJD_STAT_FREE) && (!joystick.AcquireVJD(jID))) {
                System.out.println(String.format("Failed to acquire vJoy device number {%s}.", jID));
                return;
            } else {
                System.out.println(String.format("Acquired: vJoy device number {%s}.\n", jID));
            }
        }
    }

    private void handleButtonState(Command command) {
        ButtonState[] buttonStates = buttonHandler.retrieveButtonStates(command);
        int status = joystick.GetVJDStatus(jID);
        //reconnect to vJoy in case it disconnected
        if (status != VJD_STAT_OWN)
        {
            joystick.AcquireVJD(jID);
        }

        for (int i = 0; i < buttonStates.length - 1; i++)
        {
            ButtonState bState =  buttonStates[i];
            joystick.SetBtn(bState == ButtonState.KEY_DOWN || bState == ButtonState.KEY_HOLD, jID, (byte)(i + 1));
            if (debugMsgDebouncer.debounce()) {
                LOGGER.debug(String.format("Set button states: %s", Utils.bytesToHexString(command.getRawData())));
            }
        }
    }

    @Override
    public void processCommand(Command command) {
        synchronized(processLock) {
            switch(command.getCommandId()) {
                case CMD_BUTTON_STATUS:
                    this.handleButtonState(command);
            }
        }
    }
}
