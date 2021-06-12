package com.zilox;

import com.zilox.serial.Serial;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Device {
    @Getter @Setter
    private VJoyFeeder vJoyFeeder;
    @Getter @Setter
    private Serial serial;
    @Getter
    private SharedCommandQueue sharedQueue;

    public Device(String comPort, int vJoyId) {
        //sharedQueue must be first object to be instantiate as it is used by Serial and vJoyFeeder
        sharedQueue = new SharedCommandQueue(this);
        this.initDevice(comPort, vJoyId);
    }

    private void initDevice(String comPort, int vJoyId) {
        this.initializeSerialPort(comPort);
        this.initializevJoyDevices(vJoyId);
        LogLevel.FATAL("\nStarting Shared Queue Manager...\n");
        sharedQueue.start();
    }

    private byte retrieveDeviceID(String comPort) {
        String result = "";
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(comPort);
        while(m.find()) {
            result += (m.group());
        }
        try {
            return Byte.parseByte(result);
        } catch(NumberFormatException e) {
            LogLevel.TRACE(e.getStackTrace());
            System.err.println(comPort + "is not a valid.");
        }

        return 0;
    }

    private void initializevJoyDevices(int vJoyId) {
        this.vJoyFeeder = new VJoyFeeder(vJoyId, sharedQueue);
    }

    private void initializeSerialPort(String port) {
        if (port != null) {
            String comPort = port.toUpperCase().startsWith("COM") ? port : "COM" + port;
            this.serial = new Serial(comPort, sharedQueue);
            this.serial.start();
        }
    }
}
