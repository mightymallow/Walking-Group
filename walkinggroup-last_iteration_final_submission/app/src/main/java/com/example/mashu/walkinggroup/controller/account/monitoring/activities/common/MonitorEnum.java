package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common;

/**
 * The MonitorEnum enum describes
 * an enum containing distinctions of the two monitoring classes.
 */

public enum MonitorEnum {

    MONITORED_BY_ACTIVITY("To Monitor You"),
    MONITORS_ACTIVITY("To Monitor");

    private String text;

    MonitorEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}