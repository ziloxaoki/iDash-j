package com.zilox.vjoy;

public enum ButtonState {
    NONE,
    KEY_DOWN,
    KEY_UP,
    KEY_HOLD;

    public static ButtonState nextDownState(ButtonState s)
    {
        switch (s)
        {
            case NONE:
                return KEY_DOWN;
            case KEY_DOWN:
                return KEY_HOLD;
            case KEY_HOLD:
                return KEY_HOLD;
            case KEY_UP:
                return KEY_DOWN;
            default:
                return NONE;
        }
    }

    public static ButtonState nextUpState(ButtonState s)
    {
        switch (s)
        {
            case NONE:
                return NONE;
            case KEY_DOWN:
                return KEY_UP;
            case KEY_HOLD:
                return KEY_UP;
            case KEY_UP:
                return NONE;
            default:
                return NONE;
        }
    }
}
