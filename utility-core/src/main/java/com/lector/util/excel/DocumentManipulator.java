package com.lector.util.excel;

import com.lector.util.excel.document.TabularDocument;
import com.lector.util.excel.exception.ExcelDocumentCreationException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/14/2016
 */
public interface DocumentManipulator {

    <T> List<T> read(TabularDocument<T> tabularDocument, InputStream inputStream) throws ExcelDocumentCreationException;

    <T> void write(TabularDocument<T> tabularDocument, boolean createHeader, List<T> elements, OutputStream outputStream);
}
