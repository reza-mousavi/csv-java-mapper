package com.lectorl.util.excel;

import com.lectorl.util.excel.annotation.Row;
import com.lectorl.util.excel.document.ExcelField;
import com.lectorl.util.excel.exception.NoModelException;
import com.lectorl.util.excel.datatype.excel.CellConverter;
import com.lectorl.util.excel.manipulator.PoiDocumentManipulator;
import com.lectorl.util.excel.model.Book;
import com.lectorl.util.excel.model.NonModelPerson;
import com.lectorl.util.excel.model.Person;
import com.lectorl.util.excel.util.AnnotationUtil;
import com.lectorl.util.excel.util.CellUtil;
import com.lectorl.util.excel.util.SheetUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lectorl.util.excel.ImplementationType.HSSF;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
@RunWith(JUnit4.class)
public class TabularDocumentWriterTest {

    public static final String TEST_XLS = "test.xls";

    private CellConverter cellConverter;

    @Before
    public void cleanTestFile() {
        this.cellConverter = new CellConverter();
        final File outputFile = new File(TEST_XLS);
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }

    @Test
    public void testCreatedExcelSheetName() throws IOException {
        testEmptyExcelForModel(Book.class);
        testEmptyExcelForModel(Person.class);
    }

    @Test(expected = NoModelException.class)
    public void testCreatedExcelSheetNameForNonModel() throws IOException {
        testEmptyExcelForModel(NonModelPerson.class);
    }

    @Test
    public void testCreatedExcelHeader() throws IOException {
        testHeaderExcelForModel(Book.class, false);
        testHeaderExcelForModel(Book.class, true);
        testHeaderExcelForModel(Person.class, true);
        testHeaderExcelForBookModelManual(Book.class);
    }

    @Test
    public void testCreateExcelColumns() throws IOException {
        testRowsExcelForModel(true, Book.class, getListOfBooks());
        testRowsExcelForModel(true, Person.class, getListOfPersons());
        testRowsExcelForModel(true, Book.class, getListOfNullBooks());
    }

    private <T> void testRowsExcelForModel(boolean createHeader, Class<T> modelClass, List<T> listOfRecords) throws IOException {
        cleanTestFile();
        final TabularDocumentWriter documentCreator = createExcelFile(createHeader, modelClass, listOfRecords);
        assertCreatedFileHasContent(createHeader, TEST_XLS, modelClass, documentCreator.getConfiguration(), listOfRecords);
    }

    private <T> void testHeaderExcelForBookModelManual(Class<T> clazz) throws IOException {
        cleanTestFile();
        final List<T> elements = new ArrayList<>();
        final TabularDocumentWriter documentCreator = createExcelFile(true, clazz, elements);
        assertCreatedFileHasContent(true, TEST_XLS, clazz, documentCreator.getConfiguration(), elements);

        final Sheet sheet = getSheet(TEST_XLS);
        final org.apache.poi.ss.usermodel.Row row = sheet.getRow(0);

        assertCreatedCell(row, "title", 1);
        assertCreatedCell(row, "PRIS", 3);
        assertCreatedCell(row, "PRIS", 3);

        final Optional<String> cellValue = CellUtil.getCellStringValue(row, 3);
        cellValue.ifPresent(c -> Assert.assertNotEquals("pRIS", c));
        cellValue.ifPresent(c -> Assert.assertNotEquals("Pris", c));

    }

    private <T> void testHeaderExcelForModel(Class<T> clazz, boolean createHeader) throws IOException {
        cleanTestFile();
        final List<T> elements = new ArrayList<>();
        final TabularDocumentWriter documentCreator = createExcelFile(createHeader, clazz, elements);
        assertCreatedFileHasContent(createHeader, TEST_XLS, clazz, documentCreator.getConfiguration(), elements);
    }

    private <T> void testEmptyExcelForModel(Class<T> clazz) throws IOException {
        cleanTestFile();
        final FileOutputStream out = new FileOutputStream(TEST_XLS);
        final Configuration configuration = new Configuration();
        configuration.addModel(clazz);
        configuration.setDocumentManipulator(new PoiDocumentManipulator(HSSF));
        final List<T> elements = new ArrayList<>();
        new TabularDocumentWriter()
                .setConfiguration(configuration)
                .setOutputStream(out)
                .write(clazz, elements);
        out.close();
        assertCreatedFileHasContent(true, TEST_XLS, clazz, configuration, elements);
    }

    private <T> void assertCreatedFileHasContent(boolean createHeader, String path, Class<T> clazz, Configuration configuration, List<T> records) throws IOException {
        assertFileHasContent(path);
        assertSheetName(path, clazz);
        if (createHeader) {
            assertFileHeader(path, clazz);
        }
        validateRecords(createHeader, path, clazz, configuration, records);
    }

    private <T, R> void validateRecords(boolean containHeader, String path, Class<T> clazz, Configuration configuration, List<T> records) throws IOException {
        final Sheet sheet = getSheet(path);
        Assert.assertEquals("Same size", records.size(), sheet.getLastRowNum() - sheet.getFirstRowNum());
        int headerRowNumber = containHeader ? 1 : 0;
        for (int rowIndex = 0; rowIndex < records.size(); rowIndex++) {
            final T record = records.get(rowIndex);
            final org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowIndex + headerRowNumber);
            final List<ExcelField> fields = AnnotationUtil.getFields(clazz);
            for (ExcelField field : fields) {
                final PropertyDescriptor propertyDescriptor = field.getPropertyDescriptor();
                final Class<?> propertyType = propertyDescriptor.getPropertyType();
                final int position = field.getPosition();
                final R propertyValue = AnnotationUtil.getPropertyValue(record, propertyDescriptor);
                if (propertyValue != null) {
                    Assert.assertEquals("Asserting record with retrieved value", propertyValue, cellConverter.toJava(row, position, propertyType).get());
                } else{
                    Assert.assertEquals("Asserting record with retrieved value", false, cellConverter.toJava(row, position, propertyType).isPresent());
                }
            }
        }
    }

    private void assertFileHeader(String path, Class<?> clazz) throws IOException {
        final Sheet sheet = getSheet(path);
        final org.apache.poi.ss.usermodel.Row row = sheet.getRow(0);

        final List<ExcelField> excelFields = AnnotationUtil.getFields(clazz);
        for (ExcelField excelField : excelFields) {
            assertCreatedCell(row, excelField.getName(), excelField.getPosition());
        }
    }

    private void assertCreatedCell(org.apache.poi.ss.usermodel.Row row, String title, int index) {
        final Optional<String> cellValue = CellUtil.getCellStringValue(row, index);
        cellValue.ifPresent(e -> Assert.assertEquals(title, e));
    }

    private void assertSheetName(String path, Class<?> clazz) throws IOException {
        final Row annotation = clazz.getAnnotation(Row.class);

        final Sheet sheet = getSheet(path);

        Assert.assertNotEquals(null, sheet);
        Assert.assertNotEquals(null, sheet.getSheetName());
        Assert.assertEquals("Sheet name assertion", annotation.name(), sheet.getSheetName());
    }

    private Sheet getSheet(String path) throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(path);
        return SheetUtil.getSheet(HSSF, fileInputStream, 0);
    }

    private void assertFileHasContent(String path) {
        File result = new File(path);
        Assert.assertEquals("File exists", true, result.exists());
        Assert.assertNotEquals("File size not zero", 0L, result.length());
    }

    private <T> TabularDocumentWriter createExcelFile(boolean createHeader, Class<T> modelClass, List<T> listOfRecords) throws IOException {
        final FileOutputStream out = new FileOutputStream(TEST_XLS);
        final Configuration configuration = new Configuration();
        configuration.addModel(modelClass);
        configuration.setDocumentManipulator(new PoiDocumentManipulator(HSSF));
        final TabularDocumentWriter tabularDocumentWriter = new TabularDocumentWriter();
        tabularDocumentWriter.setConfiguration(configuration)
                .setCreateHeader(createHeader)
                .setOutputStream(out)
                .write(modelClass, listOfRecords);
        out.close();
        return tabularDocumentWriter;
    }

    private List<Book> getListOfBooks() {
        final List<Book> rows = new ArrayList<>();
        Book book = new Book();
        book.setAuthor("Reza Mousavi");
        book.setIsbn("1235432");
        book.setLanguage("Persian");
        book.setPrice(new BigDecimal(12.5));
        book.setPublisher("Cambridge");
        book.setReleaseDate(LocalDate.now());
        //book.setTitle("A guide for java lovers");
        rows.add(book);

        book = new Book();
        book.setAuthor("Alex Riis");
        book.setIsbn("35674576");
        book.setLanguage("Danish");
        book.setPrice(new BigDecimal(44.5));
        book.setPublisher("McGill");
        book.setReleaseDate(LocalDate.now());
        book.setTitle("A guide for lambda lovers");
        rows.add(book);

        book = new Book();
        book.setAuthor("Mona Yazdan panah");
        book.setIsbn("999999999999");
        book.setLanguage("Turkish");
        book.setPrice(new BigDecimal(22.5));
        book.setPublisher("Oxford");
        book.setReleaseDate(LocalDate.now());
        book.setTitle("Turkish in trip");
        rows.add(book);

        book = new Book();
        book.setAuthor("Jens Allan");
        book.setIsbn("324547576");
        book.setLanguage("Danish");
        book.setPrice(new BigDecimal(44.25));
        book.setPublisher("Dan Publishing");
        book.setReleaseDate(LocalDate.now());
        book.setTitle("Danish words");

        rows.add(book);

        return rows;
    }

    private List<Book> getListOfNullBooks() {
        final List<Book> books = new ArrayList<>(getListOfBooks());
        books.add(null);
        return books;

    }

    private List<Person> getListOfPersons() {
        final List<Person> rows = new ArrayList<>();
        rows.add(new Person("Reza", "Mousavi"));
        rows.add(new Person("Mona", "Yazdan"));
        return rows;
    }


}
