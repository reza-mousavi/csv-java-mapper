package com.lector.util.excel.document;

import com.lector.util.excel.annotation.Row;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class ExcelRow<T> {

    private String name;
    private Class<T> clazz;

    public ExcelRow(Row row, Class<T> clazz) {
        this.name = row.name();
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
