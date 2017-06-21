package com.example.rebelartstudios.sternenkrieg.exception;

public class MyException extends Exception {

    /*
     This is an Exception used for the tests.
     */

    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(Throwable cause) {
        super(cause);
    }

}
