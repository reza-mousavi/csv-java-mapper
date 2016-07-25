package com.lector.util.tabular.manipulator;

import com.lector.util.tabular.AbstractDocumentManipulator;
import com.lector.util.tabular.document.TabularField;
import com.lector.util.tabular.util.AnnotationUtil;
import com.lector.util.tabular.datatype.excel.CellConverter;
import com.lector.util.tabular.document.TabularDocument;
import com.lector.util.tabular.exception.TabularDocumentCreationException;
import com.lector.util.tabular.util.RowUtil;
import com.lector.util.tabular.util.SheetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/14/2016
 */
public class PoiDocumentManipulator extends AbstractDocumentManipulator {

    private static final Log logger = LogFactory.getLog(PoiDocumentManipulator.class);

    private CellConverter cellConverter;
    private ImplementationType implementationType;

    public PoiDocumentManipulator(ImplementationType implementationType) {
        this.implementationType = implementationType;
        this.cellConverter = new CellConverter();
    }

    public ImplementationType getImplementationType() {
        return implementationType;
    }

    @Override
    public <T> List<T> read(TabularDocument<T> tabularDocument, InputStream inputStream) throws TabularDocumentCreationException {
        final Sheet sheet = SheetUtil.getSheet(implementationType, inputStream, 0);
        return IntStream.rangeClosed(sheet.getFirstRowNum() + 1, sheet.getLastRowNum())
                .mapToObj(sheet::getRow)
                .peek(instance -> logger.debug("Read object is : " + instance))
                .map(row -> fromRow(row, tabularDocument))
                .collect(Collectors.toList());

    }

    @Override
    public <T> void write(TabularDocument<T> tabularDocument, boolean createHeader, List<T> elements, OutputStream outputStream) {
        final String sheetName = tabularDocument.getTabularRow().getName();
        final Sheet sheet = SheetUtil.createSheet(implementationType, sheetName);

        try {
            if (createHeader) {
                toHeaderRow(tabularDocument, sheet);
            }
            elements.stream()
                    .peek(record -> logger.debug("Converting to tabular row : " + record))
                    .forEach(record -> toRow(tabularDocument, record, sheet));

            final Workbook workbook = sheet.getWorkbook();
            workbook.write(outputStream);
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException("Cannot write tabular result for output.", e);
        }

    }

    public <T> T fromRow(Row row, TabularDocument<T> tabularDocument) {
        final T instance = tabularDocument.newInstance();
        tabularDocument
                .getTabularFields()
                .stream()
                .peek(e -> logger.debug("Setting value for property : " + e.getPropertyDescriptor().getName()))
                .forEach(excelField -> fromColumnToJava(row, instance, excelField));
        return instance;
    }

    public <T> Row toHeaderRow(TabularDocument<T> tabularDocument, Sheet sheet) {
        final Row header = RowUtil.createRow(sheet);
        final Set<TabularField> fieldsStructure = tabularDocument.getTabularFields();
        fieldsStructure
                .stream()
                .peek(e -> logger.debug("\t\tMethod found for property : " + e.getPropertyDescriptor().getName()))
                .forEach(c -> cellConverter.fromJava(header, c.getPosition(), c.getName()));
        return header;
    }

    public <T> Row toRow(TabularDocument<T> tabularDocument, T record, Sheet sheet) {
        final Row row = RowUtil.createRow(sheet);
        tabularDocument
                .getTabularFields()
                .stream()
                .forEach(e -> fromJavaToColumn(record, row, e));
        return row;
    }

    private <T> void fromColumnToJava(Row row, T instance, TabularField tabularField) {
        final Optional<Object> fieldValue = extractValue(tabularField, row);
        final PropertyDescriptor propertyDescriptor = tabularField.getPropertyDescriptor();
        fieldValue.ifPresent(e -> AnnotationUtil.setPropertyValue(instance, e, propertyDescriptor));
    }

    private <T> Optional<T> extractValue(TabularField tabularField, Row row) {
        final PropertyDescriptor propertyDescriptor = tabularField.getPropertyDescriptor();
        final Class<?> propertyType = propertyDescriptor.getPropertyType();
        final int position = tabularField.getPosition();
        return cellConverter.toJava(row, position, (Class<T>) propertyType);
    }

    private <T> void fromJavaToColumn(T record, Row row, TabularField tabularField) {
        final PropertyDescriptor propertyDescriptor = tabularField.getPropertyDescriptor();
        final int position = tabularField.getPosition();
        final T value = AnnotationUtil.getPropertyValue(record, propertyDescriptor);
        logger.debug("Value for property is : " + value);
        Optional.ofNullable(value)
                .ifPresent(e -> cellConverter.fromJava(row, position, value));
    }

}
