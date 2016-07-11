package com.lectorl.util.excel.util;

import com.lectorl.util.excel.annotation.Field;
import com.lectorl.util.excel.document.ExcelField;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class AnnotationUtil {

    public static String getFieldName(Field field, PropertyDescriptor propertyDescriptor) {
        if (StringUtils.hasText(field.name())) {
            return field.name();
        }
        return propertyDescriptor.getName();
    }

    public static <T> List<ExcelField> getFields(Class<T> clazz) {
        final List<ExcelField> fields = new ArrayList<>();
        final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            final Method readMethod = propertyDescriptor.getReadMethod();
            final Field annotation = readMethod.getAnnotation(Field.class);
            if (annotation != null) {
                final String fieldName = AnnotationUtil.getFieldName(annotation, propertyDescriptor);
                fields.add(new ExcelField(fieldName, annotation, propertyDescriptor));
            }
        }
        return fields;
    }

    public static <T> T extractValue(ExcelField excelField, Row row){
        final PropertyDescriptor propertyDescriptor = excelField.getPropertyDescriptor();
        final Class<?> propertyType = propertyDescriptor.getPropertyType();
        final int position = excelField.getPosition();
        return CellUtil.getCellValue(row, position, (Class<T>) propertyType);
    }

    public static <T> void setPropertyValue(T instance, T fieldValue, PropertyDescriptor propertyDescriptor) {
        final Method writeMethod = propertyDescriptor.getWriteMethod();
        ReflectionUtils.invokeMethod(writeMethod, instance, fieldValue);
    }

    public static <T,R> R getPropertyValue(T instance, PropertyDescriptor propertyDescriptor) {
        final Method readMethod = propertyDescriptor.getReadMethod();
        return (R) ReflectionUtils.invokeMethod(readMethod, instance);
    }



}
