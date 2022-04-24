package com.zilox.vjoy;

import com.zilox.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zilox.vjoy.ButtonState.KEY_DOWN;
import static com.zilox.vjoy.ButtonState.NONE;


public class ButtonHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonHandler.class);

    private ButtonState[] currentStates;

    public ButtonHandler(int numberOfButtons) {
        currentStates = new ButtonState[numberOfButtons];
        for (int x = 0; x < numberOfButtons - 1; x++) {
            currentStates[x] = NONE;
        }
    }

    public ButtonState[] retrieveButtonStates(Command command) {
        //bStates[0] is the command header
        //bStates[1] is the arduino id
        //bStates[2] is the command id
        byte[] bStates = command.getData();
        int offset = 0;
        for (byte bState : bStates) {
            ButtonState currentState = currentStates[offset];

            //button is not pressed or was released
            if (bState == 0) {
                //if button is valid update the state to the next up state
                if (offset < currentStates.length) {
                    currentStates[offset++] = ButtonState.nextUpState(currentState);
                } else {
                    //default button state when not pressed
                    currentStates[offset++] = NONE;
                }
            } else {
                //if button is valid update the state to the next down state
                if (offset < currentStates.length) {
                    currentStates[offset++] = ButtonState.nextDownState(currentState);
                } else {
                    LOGGER.debug(String.format("Button %s pressed.", offset ));
                    //default button state when pressed
                    currentStates[offset++] = KEY_DOWN;
                }
            }
        }

        return currentStates;
    }
}
