package com.lemmings.puppper.model;

public class AjaxBasicReturn {
    private boolean success;
    private String message;

    public AjaxBasicReturn(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
