package com.andreabergia.echos;

public class InvalidCommandLineException extends RuntimeException {
    public InvalidCommandLineException(Exception reason) {
        super(reason);
    }

    public InvalidCommandLineException(String message) {
        super(message);
    }
}
