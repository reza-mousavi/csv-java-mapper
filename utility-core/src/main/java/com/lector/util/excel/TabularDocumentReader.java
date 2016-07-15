package com.lector.util.excel;

import com.lector.util.excel.exception.ExcelDocumentCreationException;
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

    public TabularDocumentReader setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public TabularDocumentReader setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public <T> List<T> read(Class<T> resultClazz) throws ExcelDocumentCreationException {
        logger.debug("Reading from excel document");
        return configuration.read(resultClazz, inputStream);
    }

}
