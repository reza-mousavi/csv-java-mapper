package com.lectorl.util.excel.document;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class ExcelDocument {

    public static ExcelDocument EMPTY = new ExcelDocument(null);

    private ExcelRow excelRow;

    private SortedSet<ExcelField> excelFields;

    public ExcelDocument(ExcelRow excelRow) {
        this.excelRow = excelRow;
        this.excelFields = new TreeSet<>(Comparator.comparing(ExcelField::getPosition));
    }

    public ExcelRow getExcelRow() {
        return this.excelRow;
    }

    public Set<ExcelField> getExcelFields() {
        return this.excelFields;
    }

    public void addExcelFields(ExcelField excelField) {
        this.excelFields.add(excelField);
    }

}
