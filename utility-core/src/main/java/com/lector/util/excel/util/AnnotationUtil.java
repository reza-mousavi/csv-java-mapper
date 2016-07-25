package com.lector.util.excel.util;

import com.lector.util.excel.annotation.Field;
import com.lector.util.excel.document.ExcelField;
import com.lector.util.excel.document.ExcelRow;
import com.lector.util.excel.document.TabularDocument;
import com.lector.util.excel.exception.NoModelException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class AnnotationUtil {

    private static final Log logger =  LogFactory.getLog(AnnotationUtil.class);

    public static <T> TabularDocument<T> getTabularDocument(Class<T> clazz) {
        return Optional.ofNullable(clazz)
                .map(ExcelRow::new)
                .map(TabularDocument::new)
                .orElseThrow(() ->
                        new NoModelException(
                                "Class : " + clazz.getName() + " is not a valid model. " +
                                        "It should have the annotation <Row> over it."));
    }

    public static <T> Collection<ExcelField> getTabularFields(Class<T> clazz) {
        return Stream.of(PropertyUtils.getPropertyDescriptors(clazz))
                .filter(AnnotationUtil::hasFieldAnnotation)
                .map(AnnotationUtil::getTabularField)
                .collect(Collectors.toSet());
    }

    private static ExcelField getTabularField(PropertyDescriptor propertyDescriptor) {
        final Method readMethod = propertyDescriptor.getReadMethod();
        final Field annotation = readMethod.getAnnotation(Field.class);
        final String fieldName = AnnotationUtil.getFieldName(annotation, propertyDescriptor);
        final int position = annotation.position();
        return new ExcelField(fieldName, position, propertyDescriptor);
    }

    public static String getFieldName(Field field, PropertyDescriptor propertyDescriptor) {
        final Optional<String> result = Optional.of(field.name());
        return result.filter(e -> !e.equals("")).orElse(propertyDescriptor.getName());
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
        final org.reflections.Configuration configuration = new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .addClassLoader(type.getClassLoader())
                .addScanners(new SubTypesScanner());

        final Reflections reflections = new Reflections(path, configuration);

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

    public static boolean hasFieldAnnotation(PropertyDescriptor propertyDescriptor) {
        return hasReadMethod(propertyDescriptor) && hasAnnotation(propertyDescriptor, Field.class);
    }

    private static <T extends Annotation> boolean hasAnnotation(PropertyDescriptor propertyDescriptor, Class<T> annotationClass) {
        final Method readMethod = propertyDescriptor.getReadMethod();
        return readMethod.getAnnotation(annotationClass) != null;
    }

    private static boolean hasReadMethod(PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor.getReadMethod() != null;
    }



}
