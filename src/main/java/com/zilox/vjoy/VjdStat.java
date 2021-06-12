package com.zilox.vjoy;

public class VjdStat {
    /** The vJoy Device is owned by this application. */
    public static final int VJD_STAT_OWN = 0;
    /** The vJoy Device is NOT owned by any application (including this one). */
    public static final int VJD_STAT_FREE = 1;
    /** The vJoy Device is owned by another application. It cannot be acquired by this application. */
    public static final int VJD_STAT_BUSY = 2;
    /** The vJoy Device is missing. It either does not exist or the driver is down. */
    public static final int VJD_STAT_MISS = 3;
    /** Unknown */
    public static final int VJD_STAT_UNKNOWN = 4;
}
