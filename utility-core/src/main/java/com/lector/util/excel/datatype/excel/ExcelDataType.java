package com.lector.util.excel.datatype.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public interface ExcelDataType<T> {

    Cell fromJava(Row row, int position, T value);

    Optional<T> toJava(Cell cell);

    Class<T> getClazz();

}
