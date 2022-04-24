package com.zilox;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class Property {
    InputStream inputStream;
    @Getter
    private String[] comPorts;
    @Getter
    private int reconnectWaitTime;
    @Getter
    private String[] devices;
    @Getter
    private String deviceProperty;
    @Getter
    private int commandQueueSizeLimit;

    public Property() {
        try {
            Properties prop = new Properties();
            String propFileName = "/config.properties";

            inputStream = this.getClass().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            // get the property value and print it out
            comPorts = prop.getProperty("comPorts", "").split(",");
            reconnectWaitTime = Integer.parseInt(prop.getProperty("reconnectWaitTime", "500"));
            devices = prop.getProperty("devices", "").split(",");
            commandQueueSizeLimit = Integer.parseInt(prop.getProperty("commandQueueSizeLimit", "1000"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "\n\nProperties = {\n" +
                "\tcomPorts=" + Arrays.toString(comPorts) + "\n" +
                "\tdevices=" + Arrays.toString(devices) + "(arduino:vJoy)\n" +
                "\treconnectWaitTime=" + reconnectWaitTime + "(milis)\n" +
                "\tcommandQueueSizeLimit=" + commandQueueSizeLimit + "(commands)\n" +
                "}\n\n";
    }
}
