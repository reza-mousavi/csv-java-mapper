package com.lectorl.util.excel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.OutputStream;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
public class ExcelDocumentWriter {

    private static final Log logger = LogFactory.getLog(ExcelDocumentWriter.class);

    private boolean createHeader = true;
    private OutputStream outputStream;
    private ExcelManipulationConfiguration configuration;

    public ExcelManipulationConfiguration getConfiguration() {
        return configuration;
    }

    public ExcelDocumentWriter setConfiguration(ExcelManipulationConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public ExcelDocumentWriter setConfiguration(Supplier<ExcelManipulationConfiguration> supplier) {
        this.configuration = supplier.get();
        return this;
    }

    public ExcelDocumentWriter setCreateHeader(boolean createHeader) {
        this.createHeader = createHeader;
        return this;
    }

    public ExcelDocumentWriter setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public <T> void write(Class<T> clazz, List<T> elements) {
        logger.debug("Writing excel document.");
        configuration.write(clazz, createHeader, elements, outputStream);
    }

}
