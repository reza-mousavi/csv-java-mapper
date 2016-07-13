package com.lectorl.util.excel.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/13/2016
 */
public class ExcelManipulationIOException extends RuntimeException {

    private static final long serialVersionUID = -6880145328740749529L;

    public ExcelManipulationIOException(String message) {
        super(message);
    }

    public ExcelManipulationIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
