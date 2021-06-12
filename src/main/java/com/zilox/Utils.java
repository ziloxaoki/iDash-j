package com.zilox;

import static com.zilox.Constants.*;

public class Utils {
    public static String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        if (bytes != null) {
            for (int b : bytes) {
                sb.append(String.format("%02x ", b & 0xff));
            }
            return sb.toString();
        }
        return null;
    }

    public static String bytesToIntString(byte[] bytes) {
        if (bytes != null) {
            StringBuilder sb = new StringBuilder();
            for (int b : bytes) {
                sb.append((char) b);
            }
            return sb.toString();
        }
        return null;
    }

    public static void resetArray(byte[] array)
    {
        for(int i = 0; i < array.length; i++)
        {
            array[i] = 0;
        }
    }

    public static byte[] convertByteTo7Segment(byte[] content, int offset)
    {
        byte[] result = null;
        int pos = 0;

        if (content != null) {
            var count = countNumberOfOccurrencies(content, (byte)'.');
            result = new byte[content.length - offset - count];
            for (int x = offset; x < content.length; x++)
            {
                //. has to be converted as this is the cmd se
                if (x < content.length - 2 && content[x + 1] == (byte)'.')
                {
                    //. is the most significant byte in a char
                    result[pos++] = (byte)(convertByteTo7Segment(content[x++]) + 128);
                } else
                {
                    result[pos++] = convertByteTo7Segment(content[x]);
                }
            }
        }

        return result;
    }

    public static byte convertByteTo7Segment(byte b)
    {
        switch (b)
        {
            case (byte)'.':
                return TM1637_COLON_BIT; // 128
            case (byte)' ':
                return TM1637_CHAR_SPACE; // 0
            case (byte)'!':
                return TM1637_CHAR_EXC; // 6
            case (byte)'"':
                return TM1637_CHAR_D_QUOTE; // 34
            case (byte)'#':
                return TM1637_CHAR_POUND; // 118
            case (byte)'$':
                return TM1637_CHAR_DOLLAR; // 109
            case (byte)'%':
                return TM1637_CHAR_PERC; // 36
            case (byte)'&':
                return TM1637_CHAR_AMP; // 127
            case (byte)'\'':
                return TM1637_CHAR_S_QUOTE; // 32
            case (byte)'(':
                return TM1637_CHAR_L_BRACKET; // 57
            case (byte)')':
                return TM1637_CHAR_R_BRACKET; // 15
            case (byte)'*':
                return TM1637_CHAR_STAR; // 92
            case (byte)'+':
                return TM1637_CHAR_PLUS; // 80
            case (byte)',':
                return TM1637_CHAR_COMMA; // 16
            case (byte)'-':
                return TM1637_CHAR_MIN; // 64
            //case (byte)'.':
            //    return TM1637_CHAR_DOT; // 8
            case (byte)'/':
                return TM1637_CHAR_F_SLASH; // 6
            case (byte)'0':
                return TM1637_CHAR_0; // 63
            case (byte)'1':
                return TM1637_CHAR_1; // 6
            case (byte)'2':
                return TM1637_CHAR_2; // 91
            case (byte)'3':
                return TM1637_CHAR_3; // 79
            case (byte)'4':
                return TM1637_CHAR_4; // 102
            case (byte)'5':
                return TM1637_CHAR_5; // 109
            case (byte)'6':
                return TM1637_CHAR_6; // 125
            case (byte)'7':
                return TM1637_CHAR_7; // 7
            case (byte)'8':
                return TM1637_CHAR_8; // 127
            case (byte)'9':
                return TM1637_CHAR_9; // 111
            case (byte)':':
                return TM1637_CHAR_COLON; // 48
            case (byte)';':
                return TM1637_CHAR_S_COLON; // 48
            case (byte)'<':
                return TM1637_CHAR_LESS; // 88
            case (byte)'=':
                return TM1637_CHAR_EQUAL; // 72
            case (byte)'>':
                return TM1637_CHAR_GREAT; // 76
            case (byte)'?':
                return TM1637_CHAR_QUEST; // 83
            case (byte)'@':
                return TM1637_CHAR_AT; // 95
            case (byte)'A':
                return TM1637_CHAR_A; // 119
            case (byte)'B':
                return TM1637_CHAR_B; // 127
            case (byte)'C':
                return TM1637_CHAR_C; // 57
            case (byte)'D':
                return TM1637_CHAR_D; // 94
            case (byte)'E':
                return TM1637_CHAR_E; // 121
            case (byte)'F':
                return TM1637_CHAR_F; // 113
            case (byte)'G':
                return TM1637_CHAR_G; // 61
            case (byte)'H':
                return TM1637_CHAR_H; // 118
            case (byte)'I':
                return TM1637_CHAR_I; // 6
            case (byte)'J':
                return TM1637_CHAR_J; // 14
            case (byte)'K':
                return TM1637_CHAR_K; // 117
            case (byte)'L':
                return TM1637_CHAR_L; // 56
            case (byte)'M':
                return TM1637_CHAR_M; // 21
            case (byte)'N':
                return TM1637_CHAR_N; // 55
            case (byte)'O':
                return TM1637_CHAR_O; // 63
            case (byte)'P':
                return TM1637_CHAR_P; // 115
            case (byte)'Q':
                return TM1637_CHAR_Q; // 103
            case (byte)'R':
                return TM1637_CHAR_R; // 51
            case (byte)'S':
                return TM1637_CHAR_S; // 109
            case (byte)'T':
                return TM1637_CHAR_T; // 120
            case (byte)'U':
                return TM1637_CHAR_U; // 62
            case (byte)'V':
                return TM1637_CHAR_V; // 28
            case (byte)'W':
                return TM1637_CHAR_W; // 42
            case (byte)'X':
                return TM1637_CHAR_X; // 118
            case (byte)'Y':
                return TM1637_CHAR_Y; // 110
            case (byte)'Z':
                return TM1637_CHAR_Z; // 91
            case (byte)'[':
                return TM1637_CHAR_L_S_BRACKET; // 57
            case (byte)'\\':
                return TM1637_CHAR_B_SLASH; // 48
            case (byte)']':
                return TM1637_CHAR_R_S_BRACKET; // 15
            case (byte)'^':
                return TM1637_CHAR_A_CIRCUM; // 19
            case (byte)'_':
                return TM1637_CHAR_UNDERSCORE; // 8
            case (byte)'`':
                return TM1637_CHAR_A_GRAVE; // 16
            case (byte)'a':
                return TM1637_CHAR_a; // 95
            case (byte)'b':
                return TM1637_CHAR_b; // 124
            case (byte)'c':
                return TM1637_CHAR_c; // 88
            case (byte)'d':
                return TM1637_CHAR_d; // 94
            case (byte)'e':
                return TM1637_CHAR_e; // 123
            case (byte)'f':
                return TM1637_CHAR_f; // 113
            case (byte)'g':
                return TM1637_CHAR_g; // 111
            case (byte)'h':
                return TM1637_CHAR_h; // 116
            case (byte)'i':
                return TM1637_CHAR_i; // 4
            case (byte)'j':
                return TM1637_CHAR_j; // 12
            case (byte)'k':
                return TM1637_CHAR_k; // 117
            case (byte)'l':
                return TM1637_CHAR_l; // 48
            case (byte)'m':
                return TM1637_CHAR_m; // 21
            case (byte)'n':
                return TM1637_CHAR_n; // 84
            case (byte)'o':
                return TM1637_CHAR_o; // 92
            case (byte)'p':
                return TM1637_CHAR_p; // 115
            case (byte)'q':
                return TM1637_CHAR_q; // 103
            case (byte)'r':
                return TM1637_CHAR_r; // 80
            case (byte)'s':
                return TM1637_CHAR_s; // 109
            case (byte)'t':
                return TM1637_CHAR_t; // 120
            case (byte)'u':
                return TM1637_CHAR_u; // 28
            case (byte)'v':
                return TM1637_CHAR_v; // 28
            case (byte)'w':
                return TM1637_CHAR_w; // 42
            case (byte)'x':
                return TM1637_CHAR_x; // 118
            case (byte)'y':
                return TM1637_CHAR_y; // 102
            case (byte)'z':
                return TM1637_CHAR_z; // 91
            case (byte)'{':
                return TM1637_CHAR_L_ACCON; // 121
            case (byte)'|':
                return TM1637_CHAR_BAR; // 6
            case (byte)'}':
                return TM1637_CHAR_R_ACCON; // 79
            case (byte)'~':
                return TM1637_CHAR_TILDE; // 64
        }

        return 0;
    }

    public static int countNumberOfOccurrencies(byte[] array, byte val) {
        int result = 0;
        for (byte b : array) {
            if (b == val) result++;
        }

        return result;
    }
}
