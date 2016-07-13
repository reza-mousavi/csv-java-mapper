package com.lectorl.util.excel;

import com.lectorl.util.excel.document.ExcelDocument;
import com.lectorl.util.excel.document.ExcelDocumentBuilder;
import com.lectorl.util.excel.document.ExcelField;
import com.lectorl.util.excel.exception.ModelNotFoundException;
import com.lectorl.util.excel.util.AnnotationUtil;
import com.lectorl.util.excel.util.RowUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.lectorl.util.excel.ImplementationType.HSSF;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class ExcelManipulationConfiguration {

    private static final Logger logger = Logger.getLogger(ExcelManipulationConfiguration.class.getName());

    private CellConverter cellConverter;
    private Map<Class, ExcelDocument> excelDocuments;
    private ImplementationType implementationType = HSSF;

    public ExcelManipulationConfiguration() {
        this.cellConverter = new CellConverter();
        this.excelDocuments = new HashMap<>();
    }

    public ExcelManipulationConfiguration setImplementationType(ImplementationType implementationType) {
        this.implementationType = implementationType;
        return this;
    }

    public ImplementationType getImplementationType() {
        return implementationType;
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
            lookupForDocument(clazz)
                    .getExcelFields()
                    .stream()
                    .peek(e -> logger.debug("Setting value for property : " + e.getPropertyDescriptor().getName()))
                    .forEach(excelField -> fromColumnToJava(row, instance, excelField));
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Row toHeaderRow(Class<T> clazz, Sheet sheet) {
        final Row header = RowUtil.createRow(sheet);
        final ExcelDocument excelDocument = lookupForDocument(clazz);
        final Set<ExcelField> fieldsStructure = excelDocument.getExcelFields();
        fieldsStructure
                .stream()
                .peek(e -> logger.debug("\t\tMethod found for property : " + e.getPropertyDescriptor().getName()))
                .forEach(c -> cellConverter.fromJava(header, c.getPosition(), c.getName()));
        return header;
    }

    public <T> Row toRow(T record, Sheet sheet) {
        final Row row = RowUtil.createRow(sheet);
        Optional<T> recordOptional = Optional.ofNullable(record);
        recordOptional
                .map(e-> lookupForDocument(e.getClass()))
                .orElse(ExcelDocument.EMPTY)
                .getExcelFields()
                .stream()
                .forEach(e -> fromJavaToColumn(record, row, e));
        return row;
    }

    private <T> void fromColumnToJava(Row row, T instance, ExcelField excelField) {
        final Optional<Object> fieldValue = extractValue(excelField, row);
        final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
        fieldValue.ifPresent(e -> AnnotationUtil.setPropertyValue(instance, e, propertyDescriptor));
    }

    private <T> void fromJavaToColumn(T record, Row row, ExcelField excelField) {
        final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
        final T value = AnnotationUtil.getPropertyValue(record, propertyDescriptor);
        final int position = excelField.getPosition();
        logger.debug("Value for property is : " + value);
        if (value != null) {
            cellConverter.fromJava(row, position, value);
        }
    }

    private <T> ExcelDocument lookupForDocument(Class<T> clazz) {
        final Optional<ExcelDocument> document = Optional.ofNullable(excelDocuments.get(clazz));
        document.ifPresent(e -> logger.debug("Excel document found for class : " + clazz));
        return document.orElseThrow(() -> new ModelNotFoundException("Cannot find any model for given class : " + clazz));
    }

    private <T> Optional<T> extractValue(ExcelField excelField, Row row) {
        final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
        final Class<?> propertyType = propertyDescriptor.getPropertyType();
        final int position = excelField.getPosition();
        return cellConverter.toJava(row, position, (Class<T>) propertyType);
    }

}
