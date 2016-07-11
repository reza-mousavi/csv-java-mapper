package com.lectorl.util.excel;

import com.lectorl.util.excel.exception.ExcelDocumentCreationException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
public class ExcelDocumentReader {

    private static final Logger logger = Logger.getLogger(ExcelDocumentReader.class.getName());

    private InputStream inputStream;

    private ExcelManipulationConfiguration configuration;

    public ExcelDocumentReader setConfiguration(ExcelManipulationConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public ExcelDocumentReader setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public <T> List<T> read(Class<T> resultClazz) throws ExcelDocumentCreationException {
        final List<T> result = new ArrayList<>();
        try {
            final HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            final HSSFSheet sheet = workbook.getSheetAt(0);
            final int firstRowNum = sheet.getFirstRowNum();
            final int lastRowNum = sheet.getLastRowNum();
            for (int rowNum = firstRowNum +1; rowNum <= lastRowNum; rowNum++) {
                final Row row = sheet.getRow(rowNum);
                final T instance = configuration.fromRow(row, resultClazz);
                logger.debug("Read object is : " + instance);
                result.add(instance);
            }
            return result;
        } catch (IOException e) {
            throw new ExcelDocumentCreationException("Cannot read excel document", e);
        }
    }

}
