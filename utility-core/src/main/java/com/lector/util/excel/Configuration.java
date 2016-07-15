package com.lector.util.excel;

import com.lector.util.excel.document.ExcelDocumentBuilder;
import com.lector.util.excel.document.TabularDocument;
import com.lector.util.excel.exception.ModelNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class Configuration {

    private static final Log logger = LogFactory.getLog(Configuration.class);

    private Map<Class, TabularDocument> excelDocuments;

    public Configuration() {
        this.excelDocuments = new HashMap<>();
    }

    public Configuration addModel(Set<Class<?>> classes) {
        classes.stream()
                .forEach(this::addModel);
        return this;
    }

    public <T> Configuration addModel(Class<T> clazz) {
        final TabularDocument tabularDocument = new ExcelDocumentBuilder<T>()
                .setClass(clazz)
                .build();
        logger.info("Adding excel model for class : " + clazz);
        excelDocuments.put(clazz, tabularDocument);
        return this;
    }

    public <T> TabularDocument<T> lookupForDocument(Class<T> clazz) {
        final Optional<TabularDocument> document = Optional.ofNullable(excelDocuments.get(clazz));
        document.ifPresent(e -> logger.debug("Excel document found for class : " + clazz));
        return document.orElseThrow(() -> new ModelNotFoundException("Cannot find any model for given class : " + clazz));
    }

}
