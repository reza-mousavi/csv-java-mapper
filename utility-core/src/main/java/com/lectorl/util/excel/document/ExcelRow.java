package com.lectorl.util.excel.document;

import com.lectorl.util.excel.annotation.Row;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class ExcelRow {

    private String name;
    private Class clazz;

    public ExcelRow(Row row, Class clazz) {
        this.name = row.name();
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class getClazz() {
        return clazz;
    }
}
