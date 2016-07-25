package com.lector.util.tabular.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/13/2016
 */
public class TabularManipulationIOException extends RuntimeException {

    private static final long serialVersionUID = -6880145328740749529L;

    public TabularManipulationIOException(String message) {
        super(message);
    }

    public TabularManipulationIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
