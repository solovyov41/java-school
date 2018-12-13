package com.tlg.core.entity.enums;

public enum CarriageStatus {

    CREATED("carriage.status.created"), IN_PROGRESS("carriage.status.in.progress"), DONE("carriage.status.done");

    private final String messageCode;

    CarriageStatus(String messageCode) {

        this.messageCode = messageCode;
    }

    public String messageCode() {
        return messageCode;
    }


    @Override
    public String toString() {
        return this.messageCode;
    }
}
