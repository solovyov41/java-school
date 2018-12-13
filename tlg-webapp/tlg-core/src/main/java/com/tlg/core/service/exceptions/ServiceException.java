package com.tlg.core.service.exceptions;

import com.tlg.core.service.exceptions.error.ServiceError;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ServiceError error;

    public ServiceException(Throwable cause, ServiceError error, Object... placeholders) {
        super(error.getMessage(placeholders), cause);
        this.error = error;
    }

    public ServiceException(ServiceError error, Object... placeholders) {
        super(error.getMessage(placeholders));
        this.error = error;
    }

    public ServiceError getError() {
        return error;
    }
}