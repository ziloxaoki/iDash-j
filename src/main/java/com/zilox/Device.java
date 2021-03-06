package com.zilox;

import com.zilox.command.SharedCommandQueueManager;
import com.zilox.serial.Serial;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Device {
    private static final Logger LOGGER = LoggerFactory.getLogger(Device.class);

    @Getter @Setter
    private VJoyFeeder vJoyFeeder;
    @Getter @Setter
    private Serial serial;
    @Getter
    private SharedCommandQueueManager sharedQueueManager;

    public Device(String comPort, int vJoyId) {
        //sharedQueue must be first object to be instantiate as it is used by Serial and vJoyFeeder
        sharedQueueManager = new SharedCommandQueueManager(this);
        this.initDevice(comPort, vJoyId);
    }

    private void initDevice(String comPort, int vJoyId) {
        this.initializeSerialPort(comPort);
        this.initializevJoyDevices(vJoyId);
        LOGGER.debug("\nStarting Shared Queue Manager...\n");
        sharedQueueManager.start();
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
            LOGGER.error("Error retrieving device ID. "+e.getStackTrace());
            System.err.println(comPort + "is not a valid.");
        }

        return 0;
    }

    private void initializevJoyDevices(int vJoyId) {
        this.vJoyFeeder = new VJoyFeeder(vJoyId, sharedQueueManager);
    }

    private void initializeSerialPort(String port) {
        if (port != null) {
            String comPort = port.toUpperCase().startsWith("COM") ? port : "COM" + port;
            this.serial = new Serial(comPort, sharedQueueManager);
            this.serial.start();
        }
    }
}
