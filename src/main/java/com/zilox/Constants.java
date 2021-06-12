package com.zilox;

import lombok.Getter;

public class Constants {
    public static final int BUFFER_SIZE = 100;
    public static final int DEBUG_MSG_DEBOUNCE_TIME = 1000;
    public static final int RECONNECT_MSG_DEBOUNCE_TIME = 10000;

    public static final byte[] RPM_PATTERN = { 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, 1, 1, (byte)255, 1, 1, (byte)255, 1, 1, (byte)255 };
    public static final byte[] BLACK_RGB = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
    public static final byte[] WHITE_RGB = { 1, 1, 1, 1, 1, 1, 1, 1, 1, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, (byte)247, (byte)247, (byte)231, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
    public static final byte[] YELLOW_RGB = { 1, 1, 1, 1, 1, 1, 1, 1, 1, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, (byte)255, (byte)217, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
    public static final byte[] BLUE_RGB = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, (byte)255, 0, 0, (byte)255, 0, 0, (byte)255, 0, 0, (byte)255, 0, 0, (byte)255, 0, 0, (byte)255, 0, 0, (byte)255, 0, 0, (byte)255, 0, 0, (byte)255, 0, 0, (byte)255, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

    public static final int LED_NUM_TOTAL = 16;
    public static final byte LED_NO_BLINK = 0;
    public static final byte LED_BLINK = 1;
    public static final float FIRST_RPM = 0.7f;

    public enum FlagType
    {
        NO_FLAG(0),
        BLUE_FLAG(1),
        YELLOW_FLAG(2),
        BLACK_FLAG(8),
        WHITE_FLAG(16),
        CHECKERED_FLAG(32),
        PENALTY_FLAG(64),
        SPEED_LIMITER(128),
        IN_PIT_FLAG(256);

        @Getter
        private int value;

        FlagType(int value  ) {
            this.value = value;
        }
    }

    public static final byte TM1637_COLON_BIT = (byte)128;
    //public static final byte TM1637_CHAR_SPACE = 0;
    public static final byte TM1637_CHAR_SPACE = 48;
    public static final byte TM1637_CHAR_EXC = 6;
    public static final byte TM1637_CHAR_D_QUOTE = 34;
    public static final byte TM1637_CHAR_POUND = 118;
    public static final byte TM1637_CHAR_DOLLAR = 109;
    public static final byte TM1637_CHAR_PERC = 36;
    public static final byte TM1637_CHAR_AMP = 127;
    public static final byte TM1637_CHAR_S_QUOTE = 32;
    public static final byte TM1637_CHAR_L_BRACKET = 57;
    public static final byte TM1637_CHAR_R_BRACKET = 15;
    public static final byte TM1637_CHAR_STAR = 92;
    public static final byte TM1637_CHAR_PLUS = 80;
    public static final byte TM1637_CHAR_COMMA = 16;
    public static final byte TM1637_CHAR_MIN = 64;
    public static final byte TM1637_CHAR_DOT = 8;
    public static final byte TM1637_CHAR_F_SLASH = 6;
    public static final byte TM1637_CHAR_0 = 63;
    public static final byte TM1637_CHAR_1 = 6;
    public static final byte TM1637_CHAR_2 = 91;
    public static final byte TM1637_CHAR_3 = 79;
    public static final byte TM1637_CHAR_4 = 102;
    public static final byte TM1637_CHAR_5 = 109;
    public static final byte TM1637_CHAR_6 = 125;
    public static final byte TM1637_CHAR_7 = 7;
    public static final byte TM1637_CHAR_8 = 127;
    public static final byte TM1637_CHAR_9 = 111;
    public static final byte TM1637_CHAR_COLON = 48;
    public static final byte TM1637_CHAR_S_COLON = 48;
    public static final byte TM1637_CHAR_LESS = 88;
    public static final byte TM1637_CHAR_EQUAL = 72;
    public static final byte TM1637_CHAR_GREAT = 76;
    public static final byte TM1637_CHAR_QUEST = 83;
    public static final byte TM1637_CHAR_AT = 95;
    public static final byte TM1637_CHAR_A = 119;
    public static final byte TM1637_CHAR_B = 127;
    public static final byte TM1637_CHAR_C = 57;
    public static final byte TM1637_CHAR_D = 94;
    public static final byte TM1637_CHAR_E = 121;
    public static final byte TM1637_CHAR_F = 113;
    public static final byte TM1637_CHAR_G = 61;
    public static final byte TM1637_CHAR_H = 118;
    public static final byte TM1637_CHAR_I = 6;
    public static final byte TM1637_CHAR_J = 14;
    public static final byte TM1637_CHAR_K = 117;
    public static final byte TM1637_CHAR_L = 56;
    public static final byte TM1637_CHAR_M = 21;
    public static final byte TM1637_CHAR_N = 55;
    public static final byte TM1637_CHAR_O = 63;
    public static final byte TM1637_CHAR_P = 115;
    public static final byte TM1637_CHAR_Q = 103;
    public static final byte TM1637_CHAR_R = 51;
    public static final byte TM1637_CHAR_S = 109;
    public static final byte TM1637_CHAR_T = 120;
    public static final byte TM1637_CHAR_U = 62;
    public static final byte TM1637_CHAR_V = 28;
    public static final byte TM1637_CHAR_W = 42;
    public static final byte TM1637_CHAR_X = 118;
    public static final byte TM1637_CHAR_Y = 110;
    public static final byte TM1637_CHAR_Z = 91;
    public static final byte TM1637_CHAR_L_S_BRACKET = 57;
    public static final byte TM1637_CHAR_B_SLASH = 48;
    public static final byte TM1637_CHAR_R_S_BRACKET = 15;
    public static final byte TM1637_CHAR_A_CIRCUM = 19;
    public static final byte TM1637_CHAR_UNDERSCORE = 8;
    public static final byte TM1637_CHAR_A_GRAVE = 16;
    public static final byte TM1637_CHAR_a = 95;
    public static final byte TM1637_CHAR_b = 124;
    public static final byte TM1637_CHAR_c = 88;
    public static final byte TM1637_CHAR_d = 94;
    public static final byte TM1637_CHAR_e = 123;
    public static final byte TM1637_CHAR_f = 113;
    public static final byte TM1637_CHAR_g = 111;
    public static final byte TM1637_CHAR_h = 116;
    public static final byte TM1637_CHAR_i = 4;
    public static final byte TM1637_CHAR_j = 12;
    public static final byte TM1637_CHAR_k = 117;
    public static final byte TM1637_CHAR_l = 48;
    public static final byte TM1637_CHAR_m = 21;
    public static final byte TM1637_CHAR_n = 84;
    public static final byte TM1637_CHAR_o = 92;
    public static final byte TM1637_CHAR_p = 115;
    public static final byte TM1637_CHAR_q = 103;
    public static final byte TM1637_CHAR_r = 80;
    public static final byte TM1637_CHAR_s = 109;
    public static final byte TM1637_CHAR_t = 120;
    public static final byte TM1637_CHAR_u = 28;
    public static final byte TM1637_CHAR_v = 28;
    public static final byte TM1637_CHAR_w = 42;
    public static final byte TM1637_CHAR_x = 118;
    public static final byte TM1637_CHAR_y = 102;
    public static final byte TM1637_CHAR_z = 91;
    public static final byte TM1637_CHAR_L_ACCON = 121;
    public static final byte TM1637_CHAR_BAR = 6;
    public static final byte TM1637_CHAR_R_ACCON = 79;
    public static final byte TM1637_CHAR_TILDE = 64;
}
