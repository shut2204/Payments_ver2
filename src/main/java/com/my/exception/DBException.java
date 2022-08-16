package com.my.exception;

public class DBException extends AppException{

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
