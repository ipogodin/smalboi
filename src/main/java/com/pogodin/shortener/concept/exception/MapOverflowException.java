package com.pogodin.shortener.concept.exception;

public class MapOverflowException extends RuntimeException {

    public static final String OVERFLOW_MAP_EXCEPTION = "Such pair already exists." +
            "The key pair should be verified before the placement";

    public MapOverflowException() {
        super(OVERFLOW_MAP_EXCEPTION);
    }

    public MapOverflowException(String message) {
        super(message + " " + OVERFLOW_MAP_EXCEPTION);
    }
}
