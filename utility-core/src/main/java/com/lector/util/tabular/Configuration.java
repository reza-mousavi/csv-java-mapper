package com.lector.util.tabular;

import com.lector.util.tabular.document.TabularDocumentBuilder;
import com.lector.util.tabular.document.TabularDocument;
import com.lector.util.tabular.exception.ModelNotFoundException;
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

    private Map<Class, TabularDocument> tabularDocuments;

    public Configuration() {
        this.tabularDocuments = new HashMap<>();
    }

    public Configuration addModel(Set<Class<?>> classes) {
        classes.stream()
                .forEach(this::addModel);
        return this;
    }

    public <T> Configuration addModel(Class<T> clazz) {
        final TabularDocument tabularDocument = new TabularDocumentBuilder<T>()
                .setClass(clazz)
                .build();
        logger.info("Adding tabular model for class : " + clazz);
        tabularDocuments.put(clazz, tabularDocument);
        return this;
    }

    public <T> TabularDocument<T> lookupForDocument(Class<T> clazz) {
        final Optional<TabularDocument<T>> document = Optional.ofNullable(tabularDocuments.get(clazz));
        document.ifPresent(e -> logger.debug("Tabular document found for class : " + clazz));
        return document.orElseThrow(() -> new ModelNotFoundException("Cannot find any model for given class : " + clazz));
    }

}
