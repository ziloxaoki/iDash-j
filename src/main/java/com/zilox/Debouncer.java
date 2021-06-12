package com.zilox;

public class Debouncer {
    private int debouncerTime = 0;
    private long lastDebounce = 0;

    public Debouncer(int debouncerTime) {
        this.debouncerTime = debouncerTime;
    }

    public boolean debounce() {
        boolean result = System.currentTimeMillis() - lastDebounce > debouncerTime;
        if (result) {
            lastDebounce = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
