package com.lector.util.excel.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/15/2016
 */
public class InvalidCSVRowException extends RuntimeException {

    private static final long serialVersionUID = -4677303477251734295L;

    public InvalidCSVRowException(String message) {
        super(message);
    }

    public InvalidCSVRowException(String message, Throwable cause) {
        super(message, cause);
    }
}
