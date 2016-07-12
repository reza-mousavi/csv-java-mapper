package com.lectorl.util.excel;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public interface ExcelDataType<T> {

    void fromJava(T t, Cell cell);

    Optional<T> toJava(Cell cell);

    Class<T> getClazz();

}
