package com.zilox.vjoy;

import static com.zilox.vjoy.ButtonState.*;
import com.zilox.Command;
import java.util.List;


public class ButtonHandler {
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
                    //default button state when pressed
                    currentStates[offset++] = KEY_DOWN;
                }
            }
        }

        return currentStates;
    }
}
