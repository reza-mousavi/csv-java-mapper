package com.lectorl.util.excel.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
public class ExcelDocumentCreationException extends RuntimeException {

    private static final long serialVersionUID = 8551911856681275599L;

    public ExcelDocumentCreationException(String message) {
        super(message);
    }

    public ExcelDocumentCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
