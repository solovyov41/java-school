package com.tlg.core.entity.enums;

public enum VehicleState {

    OK("vehicle.state.ok"), BROKEN("vehicle.state.broken");

    private final String messageCode;

    VehicleState(String messageCode) {

        this.messageCode = messageCode;
    }

    public String messageCode() {
        return messageCode;
    }
}
