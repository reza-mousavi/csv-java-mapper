package com.lector.util.excel;

import com.lector.util.excel.document.TabularDocument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.OutputStream;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
public class TabularDocumentWriter {

    private static final Log logger = LogFactory.getLog(TabularDocumentWriter.class);

    private boolean createHeader = true;
    private OutputStream outputStream;
    private Configuration configuration;
    private DocumentManipulator documentManipulator;

    public Configuration getConfiguration() {
        return configuration;
    }

    public TabularDocumentWriter setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public TabularDocumentWriter setConfiguration(Supplier<Configuration> supplier) {
        this.configuration = supplier.get();
        return this;
    }

    public TabularDocumentWriter setDocumentManipulator(DocumentManipulator documentManipulator) {
        this.documentManipulator = documentManipulator;
        return this;
    }

    public TabularDocumentWriter setCreateHeader(boolean createHeader) {
        this.createHeader = createHeader;
        return this;
    }

    public TabularDocumentWriter setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public <T> void write(Class<T> clazz, List<T> elements) {
        logger.debug("Writing excel document.");
        logger.debug("Writing for class type : " + clazz);
        final TabularDocument<T> tabularDocument = configuration.lookupForDocument(clazz);
        documentManipulator.write(tabularDocument, createHeader, elements, outputStream);
    }

}
