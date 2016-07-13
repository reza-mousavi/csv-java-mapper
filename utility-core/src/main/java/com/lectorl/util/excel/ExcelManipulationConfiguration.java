package com.lectorl.util.excel;

import com.lectorl.util.excel.document.ExcelDocument;
import com.lectorl.util.excel.document.ExcelDocumentBuilder;
import com.lectorl.util.excel.document.ExcelField;
import com.lectorl.util.excel.exception.ModelNotFoundException;
import com.lectorl.util.excel.util.AnnotationUtil;
import com.lectorl.util.excel.util.RowUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class ExcelManipulationConfiguration {

    private static final Logger logger = Logger.getLogger(ExcelManipulationConfiguration.class.getName());

    private CellConverter cellConverter;
    private Map<Class, ExcelDocument> excelDocuments;

    public ExcelManipulationConfiguration() {
        this.cellConverter = new CellConverter();
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

    public <T> T fromRow(Row row, Class<T> clazz) {
        try {
            final T instance = clazz.newInstance();
            final ExcelDocument excelDocument = lookupForDocument(clazz);
            final Set<ExcelField> fieldsStructure = excelDocument.getExcelFields();
            for (ExcelField excelField : fieldsStructure) {
                final Optional<Object> fieldValue = extractValue(excelField, row);
                final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
                logger.debug("Setting value for property : " + propertyDescriptor.getName());
                fieldValue.ifPresent(e -> AnnotationUtil.setPropertyValue(instance, e, propertyDescriptor));
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Row toHeaderRow(Class<T> clazz, Sheet sheet) {
        final Workbook workbook = sheet.getWorkbook();
        final Row header = RowUtil.createRow(sheet);
        final ExcelDocument excelDocument = lookupForDocument(clazz);
        final Set<ExcelField> fieldsStructure = excelDocument.getExcelFields();
        fieldsStructure.stream().map(e -> "\t\tMethod found for property : " + e.getPropertyDescriptor().getName()).forEach(logger::debug);
        fieldsStructure.stream().forEach(c -> {
            String value = c.getName();
            final CellStyle cellStyle = workbook.createCellStyle();
            final Cell cell = header.createCell(c.getPosition());
            cell.setCellStyle(cellStyle);
            if (value != null) {
                cell.setCellValue(value);
                cell.setCellType(Cell.CELL_TYPE_STRING);
            } else {
                cell.setCellType(Cell.CELL_TYPE_BLANK);
            }

        });
        return header;
    }

    public <T> Row toRow(T record, Sheet sheet) {
        final Row row = RowUtil.createRow(sheet);
        final Class<?> clazz = record.getClass();
        final ExcelDocument excelDocument = lookupForDocument(clazz);
        final Set<ExcelField> fieldsStructure = excelDocument.getExcelFields();
        for (ExcelField excelField : fieldsStructure) {
            final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
            final T value = AnnotationUtil.getPropertyValue(record, propertyDescriptor);
            final int position = excelField.getPosition();
            logger.debug("Value for property is : " + value);
            if (value != null) {
                cellConverter.fromJava(row, position, value);
            }
        }
        return row;
    }

    private <T> ExcelDocument lookupForDocument(Class<T> clazz) {
        final Optional<ExcelDocument> document = Optional.ofNullable(excelDocuments.get(clazz));
        document.ifPresent(e -> logger.debug("Excel document found for class : " + clazz));
        return document.orElseThrow(() -> new ModelNotFoundException("Cannot find any model for given class : " + clazz));
    }

    public <T> Optional<T> extractValue(ExcelField excelField, Row row) {
        final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
        final Class<?> propertyType = propertyDescriptor.getPropertyType();
        final int position = excelField.getPosition();
        return cellConverter.toJava(row, position, (Class<T>) propertyType);
    }

}
