package com.lector.util.tabular.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/13/2016
 */
public class UnknownCellTypeException extends RuntimeException {

    private static final long serialVersionUID = 8359720748218041732L;

    public UnknownCellTypeException(String message) {
        super(message);
    }

    public UnknownCellTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
