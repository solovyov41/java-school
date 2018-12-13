package com.tlg.core.entity.enums;

public enum Role {

    ADMIN("role.admin"), DISPATCHER("role.dispatcher"), DRIVER("role.driver"), REGISTERED("role.authorized");

    private final String messageCode;

    Role(String messageCode) {

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