package com.alvindizon.basejavaapp.ui.screens.settings;

public class SettingsUIState {

    enum Status {
        NONE, PROCESSING, ERROR, SUCCESS
    }

    private static SettingsUIState INSTANCE;
    private Status status;
    private String errorMessage;

    private SettingsUIState(Status status) {
        this.status = status;
    }
    private SettingsUIState(Status status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static SettingsUIState NONE() {
        INSTANCE = new SettingsUIState(Status.NONE);
        return INSTANCE;
    }

    public static SettingsUIState PROCESSING() {
        INSTANCE = new SettingsUIState(Status.PROCESSING);
        return INSTANCE;
    }

    public static SettingsUIState ERROR(String errorMessage) {
        INSTANCE = new SettingsUIState(Status.ERROR, errorMessage);
        return INSTANCE;
    }

    public static SettingsUIState SUCCESS() {
        INSTANCE = new SettingsUIState(Status.SUCCESS);
        return INSTANCE;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
