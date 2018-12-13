package com.tlg.core.service.exceptions.error;

public enum ServiceError {

    EMAIL_EXISTS("There is an account with that email address: %s"),
    CANT_UPLOAD_FILE("Can not upload the file"),
    CANT_CREATE("Can not create: %s"),
    SEARCH_ERROR("Error during search of: %s"),
    UPDATE_ERROR("Can not update found by [%s - %s] object by %s"),
    DELETE_ERROR("Can not delete found by [%s - %s] %s object"),
    NOT_SINGLE_OPEN_SHIFT("Finish %s driver shifts instead of one."),
    CHECKIN_ERROR("Unable to check in arrival for carriage %s in waypoint #%s for driver %s"),
    CANT_ASSIGN_CITY("Can not assign city %s for %s - %s"),
    DRIVER_STATUS_CHANGE("Can not change status for driver %s because of: %s");

    private String message;

    ServiceError(String logMessage) {
        this.message = logMessage;
    }

    public String getMessage(Object... placeholders) {
        if (placeholders != null) {
            return String.format(message, placeholders);
        }
        return message;
    }
}