package com.zilox.serial;

import com.zilox.command.Command;
import com.zilox.Constants;
import com.zilox.command.SharedCommandQueueManager;
import com.zilox.Utils;

public class SerialUtils {
    private SharedCommandQueueManager sharedCommandQueueManager;
    
    public SerialUtils(SharedCommandQueueManager sharedCommandQueueManager) {
        this.sharedCommandQueueManager = sharedCommandQueueManager;
    }

    protected void sendTo7Segments(String msg) {
        Command command = new Command(Command.CMD_7_SEGS, Utils.convertByteTo7Segment(msg.getBytes(), 0));
        sharedCommandQueueManager.addToQueue(command);
    }
    
    protected void sendRPMShiftMsg(float currentRpm, float firstRpm, float lastRpm, int flag)
    {
        //black, last byte indicate state - 0 = no blink, 1 = blink
        byte[] rpmLedData = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Constants.LED_NO_BLINK };
        byte[] pattern = new byte[rpmLedData.length - 1];

        System.arraycopy(Constants.RPM_PATTERN, 0, pattern, 0, pattern.length);
        int ledOffset = 0;

        if ((flag & (int)Constants.FlagType.YELLOW_FLAG.getValue()) != 0) {
            System.arraycopy(Constants.YELLOW_RGB, 0, pattern, ledOffset, pattern.length);
            ledOffset++;
        }
        if ((flag & (int)Constants.FlagType.BLUE_FLAG.getValue()) != 0) {
            System.arraycopy(Constants.BLUE_RGB, 0, pattern, ledOffset, pattern.length - (2 * ledOffset));
            ledOffset++;
        }
        if (((flag & (int) Constants.FlagType.IN_PIT_FLAG.getValue()) != 0) || ((flag & (int)Constants.FlagType.SPEED_LIMITER.getValue()) != 0)) {
            System.arraycopy(Constants.WHITE_RGB, 0, pattern, ledOffset, pattern.length - (2 * ledOffset));
        }

        float rpmPerLed = (lastRpm - firstRpm) / Constants.LED_NUM_TOTAL; //rpm range per led

        Command rgbShiftCommand = null;
        if (rpmPerLed > 0)
        {
            if (currentRpm >= firstRpm)
            {
                int numActiveLeds = (int)(Math.ceil((currentRpm - firstRpm) / rpmPerLed)) + 1;

                if (numActiveLeds > Constants.LED_NUM_TOTAL)
                {
                    numActiveLeds = Constants.LED_NUM_TOTAL;
                }
                System.arraycopy(pattern, 0, rpmLedData, 0, numActiveLeds * 3); //each led colour has 3 bytes

                if (currentRpm < lastRpm)
                {
                    rpmLedData[rpmLedData.length - 1] = Constants.LED_NO_BLINK;
                }
                else //blink
                {
                    rpmLedData[rpmLedData.length - 1] = Constants.LED_BLINK;
                }
            }
            else
            {
                if (flag > 0)
                {
                    System.arraycopy(pattern, 0, rpmLedData, 0, pattern.length);
                    rpmLedData[rpmLedData.length - 1] = Constants.LED_BLINK;
                }
                else
                {
                    //clear shift lights
                    System.arraycopy(Constants.BLACK_RGB, 0, rpmLedData, 0, Constants.BLACK_RGB.length);
                }
            }
        }
        sharedCommandQueueManager.addToQueue(new Command(Command.CMD_RGB_SHIFT, rpmLedData));
    }
}
