package com.zilox;

import static com.zilox.Command.*;
import static com.zilox.Constants.DEBUG_MSG_DEBOUNCE_TIME;

import com.zilox.serial.Serial;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SharedCommandQueue extends Thread {
    private Debouncer debugMsgDebouncer = new Debouncer(DEBUG_MSG_DEBOUNCE_TIME);
    private ConcurrentLinkedQueue<Command> queue = new ConcurrentLinkedQueue<>();
    private Device device;

    public SharedCommandQueue(Device device) {
        this.device = device;
    }

    public void addToQueue(Command command) {
        if (queue.size() > iDash.getProperties().getCommandQueueSizeLimit()) {
            this.wipeOutQueueData();
        }
        queue.offer(command);
    }

    public void addToQueue(Command[] commands) {
        for (Command command : commands) {
            this.addToQueue(command);
        }
    }

    public Command pollFromQueue() {
        return queue.poll();
    }

    public Command[] pollAllFromQueue() {
        Command[] result = new Command[queue.size()];
        for (int x = 0; x < queue.size() - 1; x++) {
            result[x] = queue.poll();
        }

        return result;
    }

    private void processCommands() {
        Serial serial = device.getSerial();
        VJoyFeeder vJoyFeeder = device.getVJoyFeeder();

        for (Command command : this.pollAllFromQueue()) {
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
                                LogLevel.DEBUG("Processing serial command:" + command);
                            }
                            serial.processCommand(command);
                        }
                        break;
                    //Arduino buttons state message
                    case CMD_BUTTON_STATUS:
                        if (vJoyFeeder != null) {
                            if (debugMsgDebouncer.debounce()) {
                                LogLevel.DEBUG("Processing vJoy command:" + command);
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

    public void wipeOutQueueData() {
        LogLevel.FATAL("Clearing full Queue!!!");
        queue.clear();
    }

    public int getQueueCurrentSize() {
        return queue.size();
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            this.processCommands();
        }
    }

    public String dumpQueue() {
        return queue.toString();
    }
}
