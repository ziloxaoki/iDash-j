package com.zilox;

import com.joffrey.iracing.irsdkjava.IRacingLibrary;
import lombok.Getter;


public class iDash {
    @Getter
    private static Property properties;
    private static Device[] devices;

    private void printBanner() {
        System.out.println(
                "===========================================\n" +
                        ",--. ,------.                    ,--.      \n" +
                        "`--' |  .-.  \\   ,--,--.  ,---.  |  ,---.  \n" +
                        ",--. |  |  \\  : ' ,-.  | (  .-'  |  .-.  | \n" +
                        "|  | |  '--'  / \\ '-'  | .-'  `) |  | |  | \n" +
                        "`--' `-------'   `--`--' `----'  `--' `--' \n" +
                        "===========================================\n");
    }

    public static Property getProperties() {
        return properties;
    }

    private static Device[] retrieveDevices(String[] properties) {
        Device[] result = new Device[properties.length];
        int offset = 0;
        for (String p : properties) {
            String[] deviceComponents = p.split(":");
            try {
                Device device = new Device(deviceComponents[0], Integer.parseInt(deviceComponents[1].trim()));
                result[offset++] = device;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void main (String[] args) {
        properties = new Property();
        iDash idash = new iDash();
        idash.printBanner();
        devices = retrieveDevices(properties.getDevices());
        System.out.println(properties);
    }
}
