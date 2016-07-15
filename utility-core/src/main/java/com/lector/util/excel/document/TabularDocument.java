package com.lector.util.excel.document;

import com.lector.util.excel.util.AnnotationUtil;

import java.util.*;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class TabularDocument<T> {

    public static TabularDocument<Object> EMPTY = new TabularDocument<>(null);

    private ExcelRow<T> excelRow;

    private SortedSet<ExcelField> excelFields;

    public TabularDocument(ExcelRow<T> excelRow) {
        this.excelRow = excelRow;
        this.excelFields = new TreeSet<>(Comparator.comparing(ExcelField::getPosition));
    }

    public ExcelRow<T> getExcelRow() {
        return this.excelRow;
    }

    public Set<ExcelField> getExcelFields() {
        return this.excelFields;
    }

    public void addExcelFields(ExcelField excelField) {
        this.excelFields.add(excelField);
    }

    public T newInstance() {
        final ExcelRow<T> excelRow = getExcelRow();
        final Class<T> clazz = excelRow.getClazz();
        return AnnotationUtil.createNewInstance(clazz);
    }

}
