package com.lector.util.tabular.exception;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
public class TabularDocumentCreationException extends RuntimeException {

    private static final long serialVersionUID = 8551911856681275599L;

    public TabularDocumentCreationException(String message) {
        super(message);
    }

    public TabularDocumentCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
