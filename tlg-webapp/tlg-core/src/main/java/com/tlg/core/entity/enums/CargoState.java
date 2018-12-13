package com.tlg.core.entity.enums;

public enum CargoState {

    PREPARED("cargo.state.prepared"), LOADED("cargo.state.loaded"), DELIVERED(
            "cargo.state.delivered"), PROBLEM("cargo.state.problem");

    private final String messageCode;

    CargoState(String messageCode) {
        this.messageCode = messageCode;
    }

    public String messageCode() {
        return messageCode;
    }
}
