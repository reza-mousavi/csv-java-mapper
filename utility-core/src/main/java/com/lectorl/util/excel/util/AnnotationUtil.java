package com.lectorl.util.excel.util;

import com.lectorl.util.excel.annotation.Field;
import com.lectorl.util.excel.document.ExcelField;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class AnnotationUtil {

    private static final Log logger =  LogFactory.getLog(AnnotationUtil.class);

    public static String getFieldName(Field field, PropertyDescriptor propertyDescriptor) {
        final Optional<String> result = Optional.of(field.name());
        return result.filter(e -> !e.equals("")).orElse(propertyDescriptor.getName());
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

    public static <T> void setPropertyValue(T instance, T fieldValue, PropertyDescriptor propertyDescriptor) {
        final Optional<T> filedOptional = Optional.ofNullable(fieldValue);
        filedOptional.ifPresent(e -> {
            final Method writeMethod = propertyDescriptor.getWriteMethod();
            ReflectionUtils.invokeMethod(writeMethod, instance, fieldValue);
        });
    }

    public static <T, R> R getPropertyValue(T instance, PropertyDescriptor propertyDescriptor) {
        if (instance == null) return null;
        final Method readMethod = propertyDescriptor.getReadMethod();
        return (R) ReflectionUtils.invokeMethod(readMethod, instance);
    }

    public static Set<Class<?>> scanForAnnotatedClass(String path, Class<? extends Annotation> annotation) {
        final Reflections reflections = new Reflections(path);
        final Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(annotation);
        typesAnnotatedWith
                .stream()
                .map(s-> "Annotated model found -> <" + s.getName() + ">.")
                .forEach(logger::info);
        logger.debug("Searching has finished.");
        return typesAnnotatedWith;
    }

    public static <T> Set<? extends T> createImplementors(String path, Class<T> type) {
        return scanForImplementors(path, type)
                .stream()
                .map(AnnotationUtil::createNewInstance)
                .collect(Collectors.toSet());
    }

    private static <T> Set<Class<? extends T>> scanForImplementors(String path, Class<T> type) {
        final Reflections reflections = new Reflections(path);
        final Set<Class<? extends T>> implementations = reflections.getSubTypesOf(type);
        implementations
                .stream()
                .map(s-> "Implementor found -> <" + s.getName() + ">.")
                .forEach(logger::info);
        logger.debug("Searching has finished.");
        return implementations;
    }

    public static <T> T createNewInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            return null;
        }
    }

}
