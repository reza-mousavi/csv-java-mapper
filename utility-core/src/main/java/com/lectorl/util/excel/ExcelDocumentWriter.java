package com.lectorl.util.excel;

import com.lectorl.util.excel.annotation.Row;
import com.lectorl.util.excel.exception.ExcelDocumentCreationException;
import com.lectorl.util.excel.util.SheetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
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

    public <T> void create(Class<T> clazz, List<T> elements) {
        createDocument(clazz, elements, outputStream);
    }

    private <T> void createDocument(Class<T> clazz, List<T> records, OutputStream writer) {
        final Row annotation = clazz.getAnnotation(Row.class);
        if (annotation == null) {
            throw new ExcelDocumentCreationException("Cannot create excel for non model class : " + clazz.getName());
        }
        final String sheetName = annotation.name();
        final ImplementationType implementationType = configuration.getImplementationType();
        final Sheet sheet = SheetUtil.createSheet(implementationType, sheetName);

        try {
            if (createHeader) {
                configuration.toHeaderRow(clazz, sheet);
            }
            records.stream()
                    .peek(record -> logger.debug("Converting to excel row : " + record))
                    .forEach(record -> configuration.toRow(record, sheet));

            final Workbook workbook = sheet.getWorkbook();
            workbook.write(writer);
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException("Cannot create excel result for output.", e);
        }

    }

}
