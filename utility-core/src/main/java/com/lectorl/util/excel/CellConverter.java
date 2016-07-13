package com.lectorl.util.excel;

import com.lectorl.util.excel.datatype.*;
import com.lectorl.util.excel.exception.UnknownCellTypeException;
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
        excelDataTypeMap.put(Boolean.class, new ExcelDataTypeOfBoolean());
        excelDataTypeMap.put(Blank.class, new ExcelDataTypeOfBlank());
    }

    public <T> Cell fromJava(Row row, int position, Object value) {
        final Optional<Class<?>> valueOp = Optional
                .ofNullable(value)
                .map(Object::getClass);
        final Class<?> clazz = valueOp.orElse(Blank.class);
        final ExcelDataType excelDataType = excelDataTypeMap.get(clazz);
        final Optional<ExcelDataType> dataTypeHandler = Optional.ofNullable(excelDataType);
        return dataTypeHandler
                .map(e -> e.fromJava(row, position, value))
                .orElseThrow(() -> new UnknownCellTypeException("Cannot find any handler for given type"));

    }

    public <T> Optional<T> toJava(Row row, int position, Class<T> resultClass) {
        final Cell cell = row.getCell(position);
        final ExcelDataType excelDataType = excelDataTypeMap.get(resultClass);
        final Optional<ExcelDataType> dataTypeHandler = Optional.of(excelDataType);
        return dataTypeHandler.map(e -> e.toJava(cell)).orElseThrow(() -> new RuntimeException("Cannot find any handler for given type"));
    }

    //Handle null values plus handle unknown classes values ...

}
