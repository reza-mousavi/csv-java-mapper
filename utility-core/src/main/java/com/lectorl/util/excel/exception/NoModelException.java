package com.lectorl.util.excel.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/8/2016
 */
public class NoModelException extends RuntimeException {

    private static final long serialVersionUID = 5661890058486159288L;

    public NoModelException(String message) {
        super(message);
    }

    public NoModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
