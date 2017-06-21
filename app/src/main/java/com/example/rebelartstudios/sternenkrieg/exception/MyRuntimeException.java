package com.example.rebelartstudios.sternenkrieg.exception;

public class MyRuntimeException extends RuntimeException {

    public MyRuntimeException() {
    }

    public MyRuntimeException(String message) {
        super(message);
    }

    public MyRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyRuntimeException(Throwable cause) {
        super(cause);
    }

}
