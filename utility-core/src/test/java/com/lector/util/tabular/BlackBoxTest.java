package com.lector.util.tabular;

import com.lector.util.tabular.exception.ExcelDocumentCreationException;
import com.lector.util.tabular.exception.ExcelManipulationIOException;
import com.lector.util.tabular.manipulator.CSVDocumentManipulator;
import com.lector.util.tabular.manipulator.ImplementationType;
import com.lector.util.tabular.manipulator.PoiDocumentManipulator;
import com.lector.util.tabular.model.Book;
import com.lector.util.tabular.model.Person;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
@RunWith(JUnit4.class)
public class BlackBoxTest {

    public static final String TEST_XLS = "test.xls";
    public static final String TEST_XLSX = "test.xlsx";
    public static final String TEST_CSV = "test.csv";

    @Before
    public void before() {
        cleanTestFolder();
    }

    @After
    public void after() {
        cleanTestFolder();
    }

    public void cleanTestFolder() {
        deleteFile(TEST_CSV);
        deleteFile(TEST_XLS);
        deleteFile(TEST_XLSX);
    }

    private void deleteFile(String testXls) {
        final File outputFile = new File(testXls);
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }

    @Test
    public void testWritingBooks() throws IOException, ExcelDocumentCreationException {
        final FileOutputStream out = new FileOutputStream(TEST_XLS);
        final List<Book> listOfRecords = getListOfBooks();
        final Configuration configuration = new Configuration();
        configuration.addModel(Book.class);
        new TabularDocumentWriter()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF))
                .setOutputStream(out)
                .write(Book.class, listOfRecords);
        out.close();
        final FileInputStream inputStream = new FileInputStream(TEST_XLS);
        final List<Book> books = new TabularDocumentReader()
                .setConfiguration(configuration)
                .setInputStream(inputStream)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF))
                .read(Book.class);
        assertElementsAreEqual(listOfRecords, books);
    }

    @Test(expected = ExcelManipulationIOException.class)
    public void testWritingBooksIncompatibleImplementation() throws IOException, ExcelDocumentCreationException {
        final FileOutputStream out = new FileOutputStream(TEST_XLS);
        final List<Book> listOfRecords = getListOfBooks();
        final Configuration configuration = new Configuration();
        configuration.addModel(Book.class);
        new TabularDocumentWriter()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.XSSF))
                .setOutputStream(out)
                .write(Book.class, listOfRecords);
        out.close();
        final FileInputStream inputStream = new FileInputStream(TEST_XLS);
        final List<Book> books = new TabularDocumentReader()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF))
                .setInputStream(inputStream)
                .read(Book.class);
        assertElementsAreEqual(listOfRecords, books);
    }

    @Test(expected = ExcelManipulationIOException.class)
    public void testWritingBooksIncompatibleImplementation2() throws IOException, ExcelDocumentCreationException {
        final FileOutputStream out = new FileOutputStream(TEST_XLS);
        final List<Book> listOfRecords = getListOfBooks();
        final Configuration configuration = new Configuration();
        configuration.addModel(Book.class);
        new TabularDocumentWriter()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF))
                .setOutputStream(out)
                .write(Book.class, listOfRecords);
        out.close();
        final FileInputStream inputStream = new FileInputStream(TEST_XLS);
        final List<Book> books = new TabularDocumentReader()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.XSSF))
                .setInputStream(inputStream)
                .read(Book.class);
        assertElementsAreEqual(listOfRecords, books);
    }

    @Test
    public void testWritingXssfBooks() throws IOException, ExcelDocumentCreationException {
        final FileOutputStream out = new FileOutputStream(TEST_XLSX);
        final List<Book> listOfRecords = getListOfBooks();
        final Configuration configuration = new Configuration();
        configuration.addModel(Book.class);
        new TabularDocumentWriter()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.XSSF))
                .setOutputStream(out)
                .write(Book.class, listOfRecords);
        out.close();
        final FileInputStream inputStream = new FileInputStream(TEST_XLSX);
        final List<Book> books = new TabularDocumentReader()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.XSSF))
                .setInputStream(inputStream)
                .read(Book.class);
        assertElementsAreEqual(listOfRecords, books);
    }

    @Test
    public void testWritingBooksInAnotherDocument() throws IOException, ExcelDocumentCreationException {
        final String fileName = "test2.xls";
        final FileOutputStream out = new FileOutputStream(fileName);
        final List<Book> listOfBooks = getListOfBooks();
        final Configuration configuration = new Configuration();
        configuration.addModel(Book.class);
        new TabularDocumentWriter()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF))
                .setOutputStream(out)
                .write(Book.class, listOfBooks);
        final FileInputStream inputStream = new FileInputStream(fileName);
        final List<Book> books = new TabularDocumentReader()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF))
                .setInputStream(inputStream)
                .read(Book.class);
        assertElementsAreEqual(listOfBooks, books);
        out.close();
    }

    @Test
    public void testWritingPersons() throws IOException, ExcelDocumentCreationException {
        final FileOutputStream out = new FileOutputStream(TEST_XLS);
        final List<Person> listOfRecords = getListOfPersons();
        final Configuration configuration = new Configuration()
                .addModel(Person.class);
        new TabularDocumentWriter()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF))
                .setOutputStream(out)
                .write(Person.class, listOfRecords);
        out.close();
        final FileInputStream inputStream = new FileInputStream(TEST_XLS);
        final List<Person> persons = new TabularDocumentReader()
                .setConfiguration(configuration)
                .setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF))
                .setInputStream(inputStream)
                .read(Person.class);
        assertElementsAreEqual(listOfRecords, persons);
    }

    @Test
    public void testWritingBooksInCSV() throws IOException, ExcelDocumentCreationException {
        final FileOutputStream out = new FileOutputStream(TEST_CSV);
        final List<Book> listOfRecords = getListOfBooks();
        final Configuration configuration = new Configuration();
        configuration.addModel(Book.class);
        new TabularDocumentWriter()
                .setConfiguration(configuration)
                .setDocumentManipulator(new CSVDocumentManipulator())
                .setOutputStream(out)
                .write(Book.class, listOfRecords);
        out.close();
        final FileInputStream inputStream = new FileInputStream(TEST_CSV);
        final List<Book> books = new TabularDocumentReader()
                .setConfiguration(configuration)
                .setDocumentManipulator(new CSVDocumentManipulator())
                .setInputStream(inputStream)
                .read(Book.class);
        assertElementsAreEqual(listOfRecords, books);
    }

    private <E> void assertElementsAreEqual(List<E> listOfRecords, List<E> books) {
        Assert.assertNotEquals(null, books);
        Assert.assertEquals("Size is not equal", listOfRecords.size(), books.size());
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

    private List<Person> getListOfPersons() {
        final List<Person> rows = new ArrayList<>();
        rows.add(new Person("Reza", "Mousavi"));
        rows.add(new Person("Mona", "Yazdan"));
        return rows;
    }

}
