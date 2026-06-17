package com.kean.singkamasvalley.world;

public class DayManager {

    private int currentDay = 1;

    public int getCurrentDay() {
        return currentDay;
    }

    public void nextDay() {
        currentDay++;
    }
}
