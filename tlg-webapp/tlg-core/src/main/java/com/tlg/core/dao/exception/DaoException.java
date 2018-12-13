package com.tlg.core.dao.exception;

import com.tlg.core.dao.exception.error.DaoError;

public class DaoException extends Exception {

    private static final long serialVersionUID = 1L;

    private final DaoError error;

    public DaoException(DaoError error, Object... placeholders) {
        super(error.getMessage(placeholders));
        this.error = error;
    }

    public DaoException(Throwable cause, DaoError error, Object... placeholders) {
        super(error.getMessage(placeholders), cause);
        this.error = error;
    }

//    public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }

    public DaoError getError() {
        return error;
    }
}
