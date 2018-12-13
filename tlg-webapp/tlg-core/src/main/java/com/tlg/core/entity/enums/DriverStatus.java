package com.tlg.core.entity.enums;

public enum DriverStatus {

    DRIVE("driver.status.drive"), IN_SHIFT("driver.status.in.shift"), REST("driver.status.rest");

    private final String messageCode;

    DriverStatus(String messageCode) {
        this.messageCode = messageCode;
    }

    public String messageCode() {
        return messageCode;
    }

}
