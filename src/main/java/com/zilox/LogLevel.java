package com.zilox;

import jdk.jfr.StackTrace;

import java.util.Arrays;

public enum LogLevel {
    FATAL(0), ERROR(1), WARN(2), INFO(3), DEBUG(4), TRACE(5), NONE(6);

    private int level = 5;

    private LogLevel(int level) {
        this.level = level;
    }

    public static void FATAL(String message) {
        if (FATAL.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(message);
        }
    }

    public static void ERROR(String message) {
        if (ERROR.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(message);
        }
    }

    public static void WARN(String message) {
        if (WARN.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(message);
        }
    }

    public static void INFO(String message) {
        if (INFO.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(message);
        }
    }

    public static void DEBUG(String message) {
        if (DEBUG.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(message);
        }
    }

    public static void TRACE(String message) {
        if (TRACE.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(message);
        }
    }

    public static void ERROR(StackTraceElement[] stackTrace) {
        if (ERROR.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(Arrays.toString(stackTrace));
        }
    }

    public static void WARN(StackTraceElement[]  stackTrace) {
        if (WARN.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(Arrays.toString(stackTrace));
        }
    }

    public static void INFO(StackTraceElement[]  stackTrace) {
        if (INFO.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(Arrays.toString(stackTrace));
        }
    }

    public static void DEBUG(StackTraceElement[]  stackTrace) {
        if (DEBUG.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(Arrays.toString(stackTrace));
        }
    }

    public static void TRACE(StackTraceElement[]  stackTrace) {
        if (TRACE.level >= iDash.getProperties().getLogLevel().level) {
            System.out.println(Arrays.toString(stackTrace));
        }
    }
}
