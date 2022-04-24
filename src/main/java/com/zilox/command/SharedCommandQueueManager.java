package com.zilox.command;

import com.zilox.Debouncer;
import com.zilox.Device;
import com.zilox.VJoyFeeder;
import com.zilox.serial.Serial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zilox.Constants.DEBUG_MSG_DEBOUNCE_TIME;
import static com.zilox.command.Command.*;

public class SharedCommandQueueManager extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(SharedCommandQueueManager.class);

    private Debouncer debugMsgDebouncer = new Debouncer(DEBUG_MSG_DEBOUNCE_TIME);
    private SharedCommandQueue sharedCommandQueue = new SharedCommandQueue();

    private Device device;

    public SharedCommandQueueManager(Device device) {
        this.device = device;
    }

    public void addToQueue(Command command) {
        sharedCommandQueue.addToQueue(command);
    }

    public void addToQueue(Command[] commands) {
        sharedCommandQueue.addToQueue(commands);
    }

    public Command pollFromQueue() {
        return sharedCommandQueue.pollFromQueue();
    }

    public void wipeOutQueueData() {
        sharedCommandQueue.wipeOutQueueData();
    }

    public int getQueueCurrentSize() {
        return sharedCommandQueue.getQueueCurrentSize();
    }

    public String dumpQueue() {
        return sharedCommandQueue.dumpQueue();
    }

    private void processCommands() {
        Serial serial = device.getSerial();
        VJoyFeeder vJoyFeeder = device.getVJoyFeeder();

        for (Command command : sharedCommandQueue.pollAllFromQueue()) {
            if (command != null && command.getRawData() != null) {
                switch (command.getCommandId()) {
                    //ACK message sent by Arduino
                    case CMD_SYN:
                    case CMD_RESPONSE_SET_DEBUG_MODE:
                    case CMD_DEBUG_BUTTON:
                    case CMD_RGB_SHIFT:
                    case CMD_7_SEGS:
                        if (serial != null) {
                            if (debugMsgDebouncer.debounce()) {
                                LOGGER.debug("Processing serial command:" + command);
                            }
                            serial.processCommand(command);
                        }
                        break;
                    //Arduino buttons state message
                    case CMD_BUTTON_STATUS:
                        if (vJoyFeeder != null) {
                            if (debugMsgDebouncer.debounce()) {
                                LOGGER.debug("Processing vJoy command:" + command);
                            }
                            vJoyFeeder.processCommand(command);
                        }
                        break;
                    //Arduino response when crc command failed
                    case CMD_INVALID:
                    case CMD_SYN_ACK:
                        break;
                }
            }
        }
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            this.processCommands();
        }
    }
}
