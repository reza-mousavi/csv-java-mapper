package com.lector.util.tabular.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class TabularProcessorRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1641989886426535634L;

    public TabularProcessorRuntimeException(String message) {
        super(message);
    }

    public TabularProcessorRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
