package com.lectorl.util.excel.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class ExcelProcessorRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1641989886426535634L;

    public ExcelProcessorRuntimeException(String message) {
        super(message);
    }

    public ExcelProcessorRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
