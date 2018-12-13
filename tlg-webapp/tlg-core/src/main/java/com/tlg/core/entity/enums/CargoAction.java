package com.tlg.core.entity.enums;

public enum CargoAction {

    LOAD("cargo.action.load"), UNLOAD("cargo.action.unload");

    private final String messageCode;

    CargoAction(String messageCode) {

        this.messageCode = messageCode;
    }

    public String messageCode() {
        return messageCode;
    }
}
