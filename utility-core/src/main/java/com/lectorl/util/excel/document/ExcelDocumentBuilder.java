package com.lectorl.util.excel.document;

import com.lectorl.util.excel.util.AnnotationUtil;
import com.lectorl.util.excel.annotation.Field;
import com.lectorl.util.excel.annotation.Row;
import com.lectorl.util.excel.exception.NoModelException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class ExcelDocumentBuilder {

    private static final Logger logger = Logger.getLogger(ExcelDocumentBuilder.class.getName());
    private Class<?> clazz;

    public ExcelDocumentBuilder setClass(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public ExcelDocument build() {
        if(!clazz.isAnnotationPresent(Row.class)){
            throw new NoModelException(
                    "Class : " +clazz.getName()+ " is not a valid model. " +
                    "It should have the annotation <Row> over it.");
        }
        final Row row = clazz.getAnnotation(Row.class);
        final ExcelRow excelRow = new ExcelRow(row, clazz);
        logger.debug("Excel sheet name is : '" + excelRow.getName() + "'.");
        final ExcelDocument excelDocument = new ExcelDocument(excelRow);
        final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            final Method readMethod = propertyDescriptor.getReadMethod();
            final Field annotation = readMethod.getAnnotation(Field.class);
            if (annotation != null) {
                logger.debug("Adding excel field : '" + propertyDescriptor.getName() + "' to the model : " + clazz.getName());
                addFiled(excelDocument, annotation, propertyDescriptor);
            }
        }
        logger.debug("------------All fields has been read -----------.");
        return excelDocument;
    }

    private ExcelDocumentBuilder addFiled(ExcelDocument excelDocument, Field field, PropertyDescriptor propertyDescriptor) {
        final String fieldName = AnnotationUtil.getFieldName(field, propertyDescriptor);
        final ExcelField excelField = new ExcelField(fieldName, field, propertyDescriptor);
        excelDocument.addExcelFields(excelField);
        return this;
    }
}
