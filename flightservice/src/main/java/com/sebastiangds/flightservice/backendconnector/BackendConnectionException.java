package com.sebastiangds.flightservice.backendconnector;

public class BackendConnectionException extends Exception {
    public BackendConnectionException() {
    }

    public BackendConnectionException(String message) {
        super(message);
    }
}
