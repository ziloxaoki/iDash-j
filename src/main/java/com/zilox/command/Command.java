package com.zilox.command;

import com.zilox.Utils;
import lombok.Getter;

import java.util.Arrays;

public class Command {
    public static final byte CMD_INIT = (byte)200; //94d 5Eh
    public static final byte CMD_INIT_DEBUG = (byte)201; //95d 5Fh
    public static final byte CMD_END = (byte)'~'; //126d 7Eh
    public static final byte CMD_SET_DEBUG_MODE = (byte)11;
    public static final byte CMD_RESPONSE_SET_DEBUG_MODE = (byte)12;
    public static final byte CMD_SYN = (byte)'A'; //65d 41h
    public static final byte CMD_7_SEGS = (byte)'B'; //66d 42h
    public static final byte CMD_SYN_ACK = (byte)'a'; //97d 61h
    public static final byte CMD_RGB_SHIFT = (byte)'C'; //67d 43h
    public static final byte CMD_BUTTON_STATUS = (byte)'D'; //68d 44h
    public static final byte CMD_DEBUG_BUTTON = (byte)202; //CAh
    public static final byte CMD_INVALID = (byte)0xef; //239d EFh
    //command data offset starts just after byte command. (3)
    public static final byte CMD_DATA_INIT_OFFSET = (byte)3;

    @Getter
    private byte[] rawData;

    /* data = bytes between cmd_id and crc byte (exclusive)
    i.e:
    received: c8 21 44 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 2d 7e
    CMD_INIT(c8) DEVICE_ID(21) CMD_BUTTON_STATUS(44)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0) CRC (2d) CMD_END(7e)
    rawData = CMD_INIT(c8) ARDUINO_ID(21) CMD_BUTTON_STATUS(44)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0) CRC (2d) CMD_END(7e)
    data = CMD_BUTTON_STATUS(44)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)  (0)
    */
    @Getter
    private byte crc;

    public Command(byte[] bytes) {
        int commandLength = bytes.length;

        if (isValidCommand(bytes))
        {
            //buffer[0] is the command header 200
            //buffer[1] is the arduino id
            //buffer[2] is the command id
            rawData = Arrays.copyOfRange(bytes, 0, commandLength);
            this.crc = bytes[commandLength - 2];
        }
    }

    public Command(byte commandId, byte[] commandData, boolean isDebug) {
        //commandLength = CMD_INIT + DEVICE_ID + CMD_ID + ... + CRC + CMD_END
        int commandLength = 5;
        if (commandData != null)
        {
            commandLength += commandData.length;
        }

        //rawdata
        this.rawData = new byte[commandLength];
        //init array with 0s
        Utils.resetArray(rawData);
        this.rawData[0] = CMD_INIT;
        this.rawData[1] = 99;
        this.rawData[2] = commandId;

        if(commandLength > 4) {
            System.arraycopy(commandData, 0, rawData, 3, commandData.length);
        }

        if(isDebug)
        {
            this.rawData[0] = CMD_INIT_DEBUG;
        }

        //set crc
        this.crc = calculateCRC(this.rawData);
        this.rawData[commandLength - 2] = this.crc;
        this.rawData[commandLength - 1] = Command.CMD_END;
    }

    public Command(byte commandId, byte[] commandData) {
        this(commandId, commandData, false);
    }

    public byte[] getData() {
        return Arrays.copyOfRange(rawData, CMD_DATA_INIT_OFFSET, rawData.length - CMD_DATA_INIT_OFFSET + 1);
    }

    public byte getDeviceId() {
        return rawData[1];
    }

    public byte getCommandId() {
        return rawData[2];
    }

    public static byte calculateCRC(byte[] data)
    {
        int sum = 0;
        for (byte b : data)
        {
            sum += b;
        }

        int crc = sum % 256;

        return (byte)crc;
    }

    public static boolean isValidCommand(byte[] bytes)
    {
        int commandLength = bytes.length;
        if (bytes != null && commandLength > 3 && (bytes[0] == CMD_INIT || bytes[0] == CMD_INIT_DEBUG) && bytes[commandLength - 1] == CMD_END)
        {
            byte[] temp = Arrays.copyOfRange(bytes, 0, commandLength - 2);
            byte tCrc = calculateCRC(temp);
            return bytes[commandLength - 2] == tCrc;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int counter = 0;
        if (this.rawData != null) {
            for (byte b : this.rawData) {
                counter++;
                switch (b) {
                    case CMD_INIT:
                        result.append("CMD_INIT(200) ");
                        break;
                    case CMD_INIT_DEBUG:
                        result.append("CMD_INIT_DEBUG(201) ");
                        break;
                    case CMD_END:
                        result.append("CMD_END(126) ");
                        break;
                    case CMD_SET_DEBUG_MODE:
                        result.append("CMD_SET_DEBUG_MODE(11) ");
                        break;
                    case CMD_RESPONSE_SET_DEBUG_MODE:
                        result.append("CMD_RESPONSE_SET_DEBUG_MODE(12) ");
                        break;
                    case CMD_SYN:
                        result.append("CMD_SYN(65) ");
                        break;
                    case CMD_7_SEGS:
                        result.append("CMD_7_SEGS(66) ");
                        break;
                    case CMD_SYN_ACK:
                        result.append("CMD_SYN_ACK(97) ");
                        break;
                    case CMD_RGB_SHIFT:
                        result.append("CMD_RGB_SHIFT(67) ");
                        break;
                    case CMD_BUTTON_STATUS:
                        result.append("CMD_BUTTON_STATUS(68) ");
                        break;
                    case CMD_DEBUG_BUTTON:
                        result.append("CMD_DEBUG_BUTTON(202) ");
                        break;
                    case CMD_INVALID:
                        result.append("CMD_INVALID(239) ");
                        break;
                    default:
                        result.append(String.format("%s(%s) ",(counter == 2 ? "ARDUINO_ID" : counter == rawData.length - 1 ? "CRC " : " "), b));
                }
            }
        }
        return result.toString();
    }
}
