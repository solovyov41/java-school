package com.tlg.core.dao.exception.error;

public enum DaoError {

    NOT_SINGLE_RESULT("Found %s results but one expected"),
    NO_RESULT("There is no result"),
    QUERY_ERROR("Can not execute query"),
    SEARCH_QUERY_ERROR("Can not execute query for search %s by %s");


    private String message;

    DaoError(String logMessage) {
        this.message = logMessage;
    }

    public String getMessage(Object... placeholders) {
        if (placeholders != null) {
            return String.format(message, placeholders);
        }
        return message;
    }
}