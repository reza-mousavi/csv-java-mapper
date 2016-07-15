package com.lector.util.excel.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
public class CellValueConvertException extends RuntimeException {

    private static final long serialVersionUID = 8551911856681275599L;

    public CellValueConvertException(String message) {
        super(message);
    }

    public CellValueConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
