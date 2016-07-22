package com.lector.util.excel.document;

import com.lector.util.excel.annotation.Field;
import com.lector.util.excel.exception.NoModelException;
import com.lector.util.excel.util.AnnotationUtil;
import com.lector.util.excel.annotation.Row;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class ExcelDocumentBuilder<T> {

    private static final Log logger = LogFactory.getLog(ExcelDocumentBuilder.class);
    private Class<T> clazz;

    public ExcelDocumentBuilder setClass(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public TabularDocument<T> build() {
        if (!clazz.isAnnotationPresent(Row.class)) {
            throw new NoModelException(
                    "Class : " + clazz.getName() + " is not a valid model. " +
                            "It should have the annotation <Row> over it.");
        }
        final Row row = clazz.getAnnotation(Row.class);
        final ExcelRow<T> excelRow = new ExcelRow<>(row, clazz);
        logger.debug("Excel sheet name is : '" + excelRow.getName() + "'.");
        final TabularDocument<T> tabularDocument = new TabularDocument<>(excelRow);
        final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            final Method readMethod = propertyDescriptor.getReadMethod();
            final Field annotation = readMethod.getAnnotation(Field.class);
            if (annotation != null) {
                logger.debug("Adding excel field : '" + propertyDescriptor.getName() + "' to the model : " + clazz.getName());
                addFiled(tabularDocument, annotation, propertyDescriptor);
            }
        }
        logger.debug("------------All fields has been read -----------.");
        return tabularDocument;
    }

    private ExcelDocumentBuilder addFiled(TabularDocument tabularDocument, Field field, PropertyDescriptor propertyDescriptor) {
        final String fieldName = AnnotationUtil.getFieldName(field, propertyDescriptor);
        final ExcelField excelField = new ExcelField(fieldName, field, propertyDescriptor);
        tabularDocument.addExcelFields(excelField);
        return this;
    }
}
