package com.lectorl.util.excel;

import com.lectorl.util.excel.datatype.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class CellConverter {

    private Map<Class<?>, ExcelDataType> excelDataTypeMap;

    public CellConverter() {
        excelDataTypeMap = new HashMap<>();

        excelDataTypeMap.put(Date.class, new ExcelDataTypeOfDate());
        excelDataTypeMap.put(BigDecimal.class, new ExcelDataTypeOfBigDecimal());
        excelDataTypeMap.put(Double.class, new ExcelDataTypeOfDouble());
        excelDataTypeMap.put(LocalDate.class, new ExcelDataTypeOfLocalDate());
        excelDataTypeMap.put(String.class, new ExcelDataTypeOfString());
    }

    public <T> Optional<T> toJava(Row row, int position, Class<T> resultClass) {
        final Cell cell = row.getCell(position);
        final ExcelDataType excelDataType = excelDataTypeMap.get(resultClass);
        final Optional<ExcelDataType> dataTypeHandler = Optional.of(excelDataType);
        return dataTypeHandler.map(e -> e.toJava(cell)).orElseThrow(() -> new RuntimeException("Cannot find any handler for given type"));
    }

}
