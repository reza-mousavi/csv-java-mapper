package com.lectorl.util.excel;

import com.lectorl.util.excel.document.ExcelDocument;
import com.lectorl.util.excel.exception.ExcelDocumentCreationException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/14/2016
 */
public interface DocumentManipulator {

    <T> List<T> read(ExcelDocument excelDocument, InputStream inputStream) throws ExcelDocumentCreationException;

    <T> void write(ExcelDocument excelDocument, boolean createHeader, List<T> elements, OutputStream outputStream);
}
