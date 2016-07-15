package com.lector.util.excel.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/8/2016
 */
public class ModelNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5661890058486159288L;

    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
