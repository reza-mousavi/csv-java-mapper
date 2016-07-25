package com.lector.util.tabular.manipulator;

import com.lector.util.tabular.AbstractDocumentManipulator;
import com.lector.util.tabular.document.TabularField;
import com.lector.util.tabular.exception.InvalidCSVRowException;
import com.lector.util.tabular.util.AnnotationUtil;
import com.lector.util.tabular.util.SteamOperationUtil;
import com.lector.util.tabular.datatype.csv.CSVConverter;
import com.lector.util.tabular.document.TabularDocument;
import com.lector.util.tabular.exception.TabularDocumentCreationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/14/2016
 */
public class CSVDocumentManipulator extends AbstractDocumentManipulator {

    private static final Log logger = LogFactory.getLog(CSVDocumentManipulator.class);
    private static final String COMMA = ",";

    private CSVConverter cSVConverter;

    public CSVDocumentManipulator() {
        cSVConverter = new CSVConverter();
    }

    @Override
    public <T> List<T> read(TabularDocument<T> tabularDocument, InputStream inputStream) throws TabularDocumentCreationException {
        final List<T> result = new ArrayList<>();
        final List<String> lines = SteamOperationUtil.readLines(inputStream);
        lines.stream()
                .peek(l -> logger.debug("Read line is : " + l))
                .skip(1) //header
                .map(line -> fromString(line, tabularDocument))
                .forEach(result::add);
        return result;
    }

    public <R> R fromString(String line, TabularDocument<R> tabularDocument) {
        final R instance = tabularDocument.newInstance();
        if (line == null) {
            throw new InvalidCSVRowException("Row cannot be empty : '" + line + "'.");
        }
        final String[] fields = line.split(COMMA, Integer.MAX_VALUE);
        tabularDocument
                .getTabularFields()
                .stream()
                .peek(e -> logger.debug("Setting value for property : " + e.getPropertyDescriptor().getName()))
                .forEach(field -> fromStringToType(fields, instance, field));

        return instance;
    }

    @Override
    public <T> void write(TabularDocument<T> tabularDocument, boolean createHeader, List<T> elements, OutputStream outputStream) {
        final String header = writeHeader(tabularDocument);
        SteamOperationUtil.writeLine(outputStream, header);
        elements.stream()
                .peek(o -> logger.debug("Writing object : " + o))
                .map(obj -> toString(tabularDocument, obj))
                .forEach(p -> SteamOperationUtil.writeLine(outputStream, p));
    }

    private <T> void fromStringToType(String[] fields, T instance, TabularField tabularField) {
        final Optional<Object> fieldValue = extractValue(tabularField, fields);
        final PropertyDescriptor propertyDescriptor = tabularField.getPropertyDescriptor();
        fieldValue.ifPresent(e -> AnnotationUtil.setPropertyValue(instance, e, propertyDescriptor));
    }


    private <T> Optional<T> extractValue(TabularField tabularField, String[] fields) {
        final int position = tabularField.getPosition();
        int actualPosition = position -1;
        if (fields.length <= actualPosition) {
            throw new InvalidCSVRowException("Invalid row exception, expected column at : " + actualPosition);
        }
        final String value = fields[actualPosition];
        final PropertyDescriptor propertyDescriptor = tabularField.getPropertyDescriptor();
        final Class<?> propertyType = propertyDescriptor.getPropertyType();
        return cSVConverter.toJava(value, (Class<T>) propertyType);
    }

    public <T> String writeHeader(TabularDocument<T> tabularDocument) {
        final List<TabularField> fieldsList = new ArrayList<>();
        tabularDocument.getTabularFields()
                .stream()
                .forEach(element -> placeInTheList(fieldsList, element, element.getPosition()));
        final String result = fieldsList.stream()
                .map(e->e != null ? e.getName() : "")
                .collect(Collectors.joining(COMMA));
        logger.debug("Result is : "+ result);
        return result;
    }

    public <T> String toString(TabularDocument<T> tabularDocument, T record) {
        final List<TabularField> fieldsList = new ArrayList<>();
        tabularDocument.getTabularFields()
                .stream()
                .forEach(element -> placeInTheList(fieldsList, element, element.getPosition()));
        final String result = fieldsList.stream()
                .map(ef -> fromTypeToString(record, ef))
                .collect(Collectors.joining(COMMA));
        logger.debug("Result is : "+ result);
        return result;
    }

    private <T> void placeInTheList(List<T> list, T element, int position) {
        final int actualPosition = position - 1;
        IntStream.range(list.size(), actualPosition)
                .forEach(i -> list.add(i, null));
        list.add(actualPosition, element);
    }

    private <T> String fromTypeToString(T record, TabularField tabularField) {
        final Optional<TabularField> field = Optional.ofNullable(tabularField);
        if (!field.isPresent()) return "";
        final PropertyDescriptor propertyDescriptor = tabularField.getPropertyDescriptor();
        final T value = AnnotationUtil.getPropertyValue(record, propertyDescriptor);
        logger.debug("Value for property is : " + value);
        return cSVConverter.fromJava(value);
    }

}
