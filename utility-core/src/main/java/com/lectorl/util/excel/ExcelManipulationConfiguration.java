package com.lectorl.util.excel;

import com.lectorl.util.excel.document.ExcelDocument;
import com.lectorl.util.excel.document.ExcelDocumentBuilder;
import com.lectorl.util.excel.exception.ExcelDocumentCreationException;
import com.lectorl.util.excel.exception.ModelNotFoundException;
import com.lectorl.util.excel.manipulator.PoiDocumentManipulator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class ExcelManipulationConfiguration {

    private static final Log logger = LogFactory.getLog(ExcelManipulationConfiguration.class);

    private Map<Class, ExcelDocument> excelDocuments;
    private ImplementationType implementationType;
    private DocumentManipulator documentManipulator;

    public ExcelManipulationConfiguration() {
        this.excelDocuments = new HashMap<>();
    }

    public ExcelManipulationConfiguration setImplementationType(ImplementationType implementationType) {
        this.implementationType = implementationType;
        this.documentManipulator = new PoiDocumentManipulator(implementationType);
        return this;
    }

    public ImplementationType getImplementationType() {
        return implementationType;
    }

    public ExcelManipulationConfiguration addModel(Set<Class<?>> classes) {
        classes.stream()
                .forEach(this::addModel);
        return this;
    }

    public ExcelManipulationConfiguration addModel(Class<?> clazz) {
        final ExcelDocument excelDocument = new ExcelDocumentBuilder()
                .setClass(clazz)
                .build();
        logger.info("Adding excel model for class : " + clazz);
        excelDocuments.put(clazz, excelDocument);
        return this;
    }

    public <T> ExcelDocument lookupForDocument(Class<T> clazz) {
        final Optional<ExcelDocument> document = Optional.ofNullable(excelDocuments.get(clazz));
        document.ifPresent(e -> logger.debug("Excel document found for class : " + clazz));
        return document.orElseThrow(() -> new ModelNotFoundException("Cannot find any model for given class : " + clazz));
    }

    public <T> List<T> read(Class<T> resultClazz, InputStream inputStream) throws ExcelDocumentCreationException {
        logger.debug("Reading for class type : " + resultClazz);
        final ExcelDocument excelDocument = lookupForDocument(resultClazz);
        return documentManipulator.read(excelDocument, inputStream);
    }

    public <T> void write(Class<T> clazz, boolean createHeader, List<T> elements, OutputStream outputStream) {
        logger.debug("Writing for class type : " + clazz);
        final ExcelDocument excelDocument = lookupForDocument(clazz);
        documentManipulator.write(excelDocument, createHeader, elements, outputStream);
    }

}
