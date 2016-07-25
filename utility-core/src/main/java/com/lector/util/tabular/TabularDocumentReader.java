package com.lector.util.tabular;

import com.lector.util.tabular.document.TabularDocument;
import com.lector.util.tabular.exception.TabularDocumentCreationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
public class TabularDocumentReader {

    private static final Log logger = LogFactory.getLog(TabularDocumentReader.class);

    private InputStream inputStream;
    private Configuration configuration;
    private DocumentManipulator documentManipulator;

    public TabularDocumentReader setDocumentManipulator(DocumentManipulator documentManipulator) {
        this.documentManipulator = documentManipulator;
        return this;
    }

    public TabularDocumentReader setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public TabularDocumentReader setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public <T> List<T> read(Class<T> resultClazz) throws TabularDocumentCreationException {
        logger.debug("Reading from tabular document");
        logger.debug("Reading for class type : " + resultClazz);
        final TabularDocument<T> tabularDocument = configuration.lookupForDocument(resultClazz);
        return documentManipulator.read(tabularDocument, inputStream);
    }

}
