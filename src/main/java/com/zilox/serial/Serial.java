package com.zilox.serial;

import static com.zilox.Constants.*;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.zilox.*;
import com.zilox.command.Command;
import com.zilox.command.CommandHandler;
import com.zilox.command.SharedCommandQueueManager;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Serial extends Thread implements CommandHandler {
    private SerialPort serialPort;
    @Getter
    private String deviceId;
    private byte[] writeBuffer;
    private Queue<byte[]> dataQueue = new LinkedList<>();
    private final Lock readLock = new ReentrantLock();
    private final Lock processLock = new ReentrantLock();
    private byte[] serialCommand = new byte[Constants.BUFFER_SIZE];
    private int commandLength = 0;
    private Debouncer debugMsgDebouncer = new Debouncer(DEBUG_MSG_DEBOUNCE_TIME);
    private Debouncer reconnectMsgDebouncer = new Debouncer(RECONNECT_MSG_DEBOUNCE_TIME);
    private SharedCommandQueueManager sharedCommandQueueManager;
    private SerialUtils serialUtils;

    /**
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */
    private BufferedReader input;
    /** The output stream to the port */
    private OutputStream output;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 38400;

    public Serial (String comPort, SharedCommandQueueManager sharedCommandQueueManager) {
        this.sharedCommandQueueManager = sharedCommandQueueManager;
        serialUtils = new SerialUtils(this.sharedCommandQueueManager);
        this.connect(comPort);
    }

    public boolean connect(String comPort) {
        SerialPort ports[] = SerialPort.getCommPorts();

        for (SerialPort port : ports) {
            deviceId = comPort;
            if (port.getSystemPortName().equalsIgnoreCase(comPort)) {
                System.out.println(port.getSystemPortName() + " found...");
                serialPort = port;
                serialPort.setComPortParameters(DATA_RATE, 8, 1, 0);
                if (serialPort.openPort()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        LogLevel.TRACE(e.getStackTrace());
                    }

                    serialPort.addDataListener(new SerialPortDataListener() {
                        @Override
                        public int getListeningEvents() {
                            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                        }

                        @Override
                        public void serialEvent(SerialPortEvent event) {
                            if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                                synchronized (readLock) {
                                    int len = serialPort.bytesAvailable();
                                    byte[] readBuffer = new byte[len];
                                    serialPort.readBytes(readBuffer, len);
                                    dataQueue.offer(readBuffer);
                                    readLock.notifyAll();
                                }
                            }
                        }
                    });
                    return true;
                }
            } else {
                if(reconnectMsgDebouncer.debounce()) {
                    LogLevel.FATAL(String.format("%s not found. Please connect arduino to usb port.", comPort));
                }
            }
        }

        return false;
    }

    public boolean isConnected() {
        return serialPort != null && serialPort.isOpen();
    }

    private Command parseDataAndRetrieveCommand(byte [] data) {
        Command command = null;

        for (byte b : data)
        {
            switch (b)
            {
                //init command char found
                case Command.CMD_INIT_DEBUG:
                case Command.CMD_INIT:
                    serialCommand[0] = b;
                    commandLength = 1;
                    break;

                //end of command char found, send command to be processed
                case Command.CMD_END:
                    serialCommand[commandLength++] = b;
                    if (commandLength > 3)
                    {
                        command = new Command(Arrays.copyOfRange(serialCommand, 0, commandLength));
                        return command;
                    }
                    commandLength = 0;
                    Utils.resetArray(serialCommand);
                    break;

                //command init char already found, start adding the command data to buffer
                default:
                    if (commandLength > 0)
                    {
                        serialCommand[commandLength++] = b;
                    }
                    if (commandLength == Constants.BUFFER_SIZE - 1)
                    {
                        commandLength = 0;
                        Utils.resetArray(serialCommand);
                    }
                    break;
            }
        }

        return null;
    }

    public void processQueue() {
        try {
            synchronized (readLock) {
                byte[] data = dataQueue.poll();
                if (data != null) {
                    Command command = this.parseDataAndRetrieveCommand(data);
                    if (command != null) {
                        sharedCommandQueueManager.addToQueue(command);
                        if (debugMsgDebouncer.debounce()) {
                            LogLevel.DEBUG(String.format("added to queue: %s", Utils.bytesToHexString(data)));
                            LogLevel.DEBUG(command.toString());
                        }
                    }
                }
                readLock.wait(5);
                readLock.notifyAll();
            }
        } catch (InterruptedException e) {
            LogLevel.TRACE(e.getStackTrace());
        }
    }

    public void run() {
        String pattern = "hh.mm";
        DateFormat df = new SimpleDateFormat(pattern);

        while (!Thread.currentThread().isInterrupted()) {
            if(isConnected()) {
                String msg = df.format(new Date());
                if (msg != null && !msg.isBlank()) {
                    serialUtils.sendTo7Segments(msg);
                }
                processQueue();
            } else {
                try {
                    this.connect(deviceId);
                    Thread.sleep(iDash.getProperties().getReconnectWaitTime());
                } catch (Exception e) {
                    LogLevel.TRACE(e.getStackTrace());
                }
            }
        }
    }

    @Override
    public void processCommand(Command command) {
        synchronized(processLock) {
            //System.out.println(Utils.bytesToIntString(command.getRawData()));
            serialPort.writeBytes(command.getRawData(), command.getRawData().length);
        }
    }
}
