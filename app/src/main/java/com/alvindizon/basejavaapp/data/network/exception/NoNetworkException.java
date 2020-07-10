package com.alvindizon.basejavaapp.data.network.exception;

import java.io.IOException;

public class NoNetworkException extends IOException {

    private final static String NO_INTERNET_CONNECTION = "No internet connection.";

    public NoNetworkException() {
        super(NO_INTERNET_CONNECTION);
    }
}
