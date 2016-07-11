package com.lectorl.util.excel.document;

import com.lectorl.util.excel.document.ExcelField;
import com.lectorl.util.excel.document.ExcelRow;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class ExcelDocument {

    private ExcelRow excelRow;

    private SortedSet<ExcelField> excelFields;

    public ExcelDocument(ExcelRow excelRow) {
        this.excelRow = excelRow;
    }

    public ExcelRow getExcelRow() {
        return this.excelRow;
    }

    public Set<ExcelField> getExcelFields() {
        return this.excelFields;
    }

    public void addExcelFields(ExcelField excelField) {
        if (this.excelFields == null) {
            this.excelFields = new TreeSet<>(Comparator.comparing(ExcelField::getPosition));
        }
        this.excelFields.add(excelField);
    }
}
