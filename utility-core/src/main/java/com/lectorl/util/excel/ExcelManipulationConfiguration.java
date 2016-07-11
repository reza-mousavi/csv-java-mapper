package com.lectorl.util.excel;

import com.lectorl.util.excel.document.ExcelDocument;
import com.lectorl.util.excel.document.ExcelDocumentBuilder;
import com.lectorl.util.excel.document.ExcelField;
import com.lectorl.util.excel.exception.ModelNotFoundException;
import com.lectorl.util.excel.util.AnnotationUtil;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class ExcelManipulationConfiguration {

    private static final Logger logger = Logger.getLogger(ExcelManipulationConfiguration.class.getName());

    private Map<Class,ExcelDocument> excelDocuments;

    public ExcelManipulationConfiguration() {
        this.excelDocuments = new HashMap<>();
    }

    public ExcelManipulationConfiguration addModel(Set<Class<?>> classes) {
        classes.stream()
                .forEach(this::addModel);
        return this;
    }

    public ExcelManipulationConfiguration addModel(Class<?> clazz) {
        final ExcelDocument excelDocument = new ExcelDocumentBuilder()
                .setClass(clazz)
                .build();
        logger.info("Adding excel model for class : " + clazz);
        excelDocuments.put(clazz, excelDocument);
        return this;
    }

    public <T> T fromRow(Row row, Class<T> clazz){
        try {
            final T instance = clazz.newInstance();
            final ExcelDocument excelDocument = lookupForDocument(clazz);
            final Set<ExcelField> fieldsStructure = excelDocument.getExcelFields();
            for (ExcelField excelField : fieldsStructure) {
                final Object fieldValue = AnnotationUtil.extractValue(excelField, row);
                logger.debug("Value for property is : " + fieldValue);
                final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
                logger.debug("Setting value for property : " + propertyDescriptor.getName());
                AnnotationUtil.setPropertyValue(instance, fieldValue, propertyDescriptor);
            }
            return instance;
        } catch (InstantiationException|IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Row toHeaderRow(Class<T> clazz, Workbook workbook, Sheet sheet, int rowNumber){
        final Row header = sheet.createRow(rowNumber);
        final ExcelDocument excelDocument = lookupForDocument(clazz);
        final Set<ExcelField> fieldsStructure = excelDocument.getExcelFields();
        fieldsStructure.stream().map(e-> "\t\tMethod found for property : " + e.getPropertyDescriptor().getName()).forEach(logger::debug);
        fieldsStructure.stream().forEach(c -> CellUtil.createCellForString(workbook, header, c.getPosition(), c.getName()));
        return header;
    }

    public <T> Row toRow(T record, Workbook workbook, Sheet sheet, int rowNumber){
        final Row row = sheet.createRow(rowNumber);
        final Class<?> clazz = record.getClass();
        final ExcelDocument excelDocument = lookupForDocument(clazz);
        final Set<ExcelField> fieldsStructure = excelDocument.getExcelFields();
        for (ExcelField excelField : fieldsStructure) {
            final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
            final T value = AnnotationUtil.getPropertyValue(record, propertyDescriptor);
            final int position = excelField.getPosition();
            logger.debug("Value for property is : " + value);
            if (value != null) {
                CellUtil.createCell(workbook, row, position, value);
            }
        }
        return row;
    }

    private <T> ExcelDocument lookupForDocument(Class<T> clazz) {
        final ExcelDocument excelDocument = excelDocuments.get(clazz);
        if (excelDocument == null) {
            throw new ModelNotFoundException("Cannot find any model for given class : " + clazz);
        }
        logger.debug("Excel document found for class : "  +clazz);
        return excelDocument;
    }

}
