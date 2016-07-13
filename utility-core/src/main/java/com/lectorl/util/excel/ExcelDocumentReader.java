package com.lectorl.util.excel;

import com.lectorl.util.excel.exception.ExcelDocumentCreationException;
import com.lectorl.util.excel.util.SheetUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

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
        final ImplementationType implementationType = configuration.getImplementationType();
        final Sheet sheet = SheetUtil.getSheet(implementationType, inputStream, 0);
        final int firstRowNum = sheet.getFirstRowNum();
        final int lastRowNum = sheet.getLastRowNum();
        for (int rowNum = firstRowNum +1; rowNum <= lastRowNum; rowNum++) {
            final Row row = sheet.getRow(rowNum);
            final T instance = configuration.fromRow(row, resultClazz);
            logger.debug("Read object is : " + instance);
            result.add(instance);
        }
        return result;
    }

}
