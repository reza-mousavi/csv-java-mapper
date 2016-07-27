package com.lector.util.tabular.manipulator;

import com.lector.util.tabular.document.TabularDocumentBuilder;
import com.lector.util.tabular.document.TabularDocument;
import com.lector.util.tabular.exception.InvalidCSVRowException;
import com.lector.util.tabular.model.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/15/2016
 */
public class CSVDocumentManipulatorTest {

    private TabularDocument<Book> tabularDocumentOfBook;

    @Before
    public void setUp() throws Exception {
        final TabularDocumentBuilder<Book> tabularDocumentBuilder = new TabularDocumentBuilder<>();
        tabularDocumentBuilder.setClass(Book.class);
        tabularDocumentOfBook = tabularDocumentBuilder.build();
    }

    @Test
    public void testManipulatorDeliminator() {
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        Assert.assertEquals(null, manipulator.getFieldSeparator());

        manipulator.setFieldSeparator("#");
        Assert.assertNotEquals(null, manipulator.getFieldSeparator());
        Assert.assertEquals("#", manipulator.getFieldSeparator());
        Assert.assertNotEquals(",", manipulator.getFieldSeparator());
    }

    @Test(expected = InvalidCSVRowException.class)
    public void testFromStringNullForCommaSign() {
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator(",");
        final Book book = manipulator.fromString(null, tabularDocumentOfBook);
    }

    @Test(expected = InvalidCSVRowException.class)
    public void testFromStringNullForHashSign() {
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator("#");
        final Book book = manipulator.fromString(null, tabularDocumentOfBook);
    }

    @Test(expected = InvalidCSVRowException.class)
    public void testFromStringEmptyForComma() {
        String line = "";
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator(",");
        final Book book = manipulator.fromString(line, tabularDocumentOfBook);
    }

    @Test(expected = InvalidCSVRowException.class)
    public void testFromStringEmptyForHashSign() {
        String line = "";
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator("#");
        final Book book = manipulator.fromString(line, tabularDocumentOfBook);
    }

    @Test(expected = InvalidCSVRowException.class)
    public void testFromStringWithThreeColumns() {
        String line = "1,2,3";
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator(",");
        final Book book = manipulator.fromString(line, tabularDocumentOfBook);

    }

    @Test
    public void testFromEmptyObjectForComma() {
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator(",");
        String result = manipulator.toString(tabularDocumentOfBook, new Book());
        Assert.assertNotEquals(null, result);
        Assert.assertEquals(",,,,,,,,", result);

    }

    @Test
    public void testFromEmptyObjectForHashSign() {
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator("#");
        String result = manipulator.toString(tabularDocumentOfBook, new Book());
        Assert.assertNotEquals(null, result);
        Assert.assertEquals("########", result);

    }

    @Test
    public void testFromObjectWithSingleValueForComma() {
        Book book = new Book();
        book.setTitle("First");
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();

        manipulator.setFieldSeparator(",");
        String result = manipulator.toString(tabularDocumentOfBook, book);
        Assert.assertNotEquals(null, result);
        Assert.assertEquals("First,,,,,,,,", result);
        Assert.assertNotEquals(",First,,,,,,,", result);
        Assert.assertNotEquals(",,,,,,,,First", result);

        book = new Book();
        book.setAuthor("Reza");
        result = manipulator.toString(tabularDocumentOfBook, book);
        Assert.assertNotEquals(null, result);
        Assert.assertEquals(",Reza,,,,,,,", result);
        Assert.assertNotEquals(",,,,,,,,", result);
        Assert.assertNotEquals(",,Reza,,,,,,", result);
        Assert.assertNotEquals(",,,N,,,,,", result);

    }

    @Test
    public void testFromObjectWithSingleValueForHashSign() {
        Book book = new Book();
        book.setTitle("First");
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();

        manipulator.setFieldSeparator("#");
        String result = manipulator.toString(tabularDocumentOfBook, book);
        Assert.assertNotEquals(null, result);
        Assert.assertEquals("First########", result);
        Assert.assertNotEquals("#First######,", result);
        Assert.assertNotEquals("########First", result);

        book = new Book();
        book.setAuthor("Reza");
        result = manipulator.toString(tabularDocumentOfBook, book);
        Assert.assertNotEquals(null, result);
        Assert.assertEquals("#Reza#######", result);
        Assert.assertNotEquals("########", result);
        Assert.assertNotEquals("##Reza#####,", result);
        Assert.assertNotEquals("###N#####", result);

    }

    @Test
    public void testFromStringWithSingleValue() {
        String line = "First,,,,,,,,";
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator(",");
        Book result =  manipulator.fromString(line, tabularDocumentOfBook);

        Book book = new Book();
        book.setTitle("First");
        Assert.assertNotEquals(null, result);
        Assert.assertNotEquals(null, result.getTitle());
        Assert.assertEquals(book.getTitle(), result.getTitle());
        Assert.assertEquals(book, result);

        line = ",First,,,,,,,,";
        result = manipulator.fromString(line, tabularDocumentOfBook);
        book = new Book();
        book.setAuthor("First");
        Assert.assertNotEquals(null, result);
        Assert.assertNotEquals(null, result.getAuthor());
        Assert.assertEquals(book.getTitle(), result.getTitle());
        Assert.assertEquals(book.getAuthor(), result.getAuthor());
        Assert.assertEquals(book, result);

    }

    @Test
    public void testToObjectWithSingleValue() {
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator(",");
        final Book result = manipulator.fromString("First,,,,,,,,", tabularDocumentOfBook);
        Book book = new Book();
        book.setTitle("First");
        Assert.assertNotEquals(null, result);
        Assert.assertNotEquals(null, result.getTitle());
        Assert.assertEquals(book.getTitle(), result.getTitle());
        Assert.assertEquals(book, result);

    }

    @Test
    public void testFromString() {
        String line = ",Reza Mousavi,12.5,2016-07-15,,Persian,,,1235432";
        final CSVDocumentManipulator manipulator = new CSVDocumentManipulator();
        manipulator.setFieldSeparator(",");
        final Book book = manipulator.fromString(line, tabularDocumentOfBook);

    }

}
