package com.lectorl.util.excel.manipulation;

import com.lectorl.util.excel.CellConverter;
import com.lectorl.util.excel.ExcelManipulationConfiguration;
import com.lectorl.util.excel.ImplementationType;
import com.lectorl.util.excel.document.ExcelDocument;
import com.lectorl.util.excel.exception.CellValueConvertException;
import com.lectorl.util.excel.manipulator.PoiDocumentManipulator;
import com.lectorl.util.excel.model.Book;
import com.lectorl.util.excel.util.SheetUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/14/2016
 */
public class PoiDocumentManipulatorTest {

    private CellConverter cellConverter;

    @Before
    public void setUp() throws Exception {
        cellConverter = new CellConverter();
    }

    @Test
    public void testCreatedRowIsEmpty() throws IOException {
        ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final Book book = new Book();
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");
        final PoiDocumentManipulator poiDocumentManipulator = new PoiDocumentManipulator(ImplementationType.HSSF);
        final ExcelDocument bookDocument = configuration.lookupForDocument(Book.class);
        final Row row = poiDocumentManipulator.toRow(bookDocument,book, sheet);
        Assert.assertNotEquals(null, row);

        for (Cell cell : row) {
            Assert.assertEquals(null, cell);
        }
    }

    @Test
    public void testCreatedRowIsNotEmpty() throws IOException {
        ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final PoiDocumentManipulator poiDocumentManipulator = new PoiDocumentManipulator(ImplementationType.HSSF);
        final ExcelDocument bookDocument = configuration.lookupForDocument(java.awt.print.Book.class);
        final Book book = getSampleBook();
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");
        final Row row = poiDocumentManipulator.toRow(bookDocument, book, sheet);
        Assert.assertNotEquals(null, row);

        Assert.assertEquals("Title is null", false, cellConverter.toJava(row, 1, String.class).isPresent());
        Assert.assertEquals("Title field equality", book.getAuthor(), cellConverter.toJava(row, 2, String.class).get());
        Assert.assertEquals("Title field equality", book.getIsbn(), cellConverter.toJava(row, 9, String.class).get());
        Assert.assertEquals("Title field equality", book.getLanguage(), cellConverter.toJava(row, 6, String.class).get());
        Assert.assertEquals("Title field equality", book.getPrice(), cellConverter.toJava(row, 3, BigDecimal.class).get());
        Assert.assertEquals("Title field equality", book.getReleaseDate(), cellConverter.toJava(row, 4, LocalDate.class).get());
        Assert.assertEquals("Title field equality", false, cellConverter.toJava(row, 5, String.class).isPresent());
    }

    @Test
    public void testCreatedRowStringCellValue() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final PoiDocumentManipulator poiDocumentManipulator = new PoiDocumentManipulator(ImplementationType.HSSF);
        final ExcelDocument bookDocument = configuration.lookupForDocument(java.awt.print.Book.class);
        final Book book = new Book();
        final String title = "Reza";
        book.setTitle(title);
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");
        final Row row = poiDocumentManipulator.toRow(bookDocument, book, sheet);
        Assert.assertNotEquals(null, row);

        Assert.assertNotEquals(null, row.getCell(1));
        Assert.assertEquals(title, cellConverter.toJava(row, 1, String.class).get());
    }

    @Test
    public void testCreatedRowLocalDateCellValue() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final PoiDocumentManipulator poiDocumentManipulator = new PoiDocumentManipulator(ImplementationType.HSSF);
        final ExcelDocument bookDocument = configuration.lookupForDocument(java.awt.print.Book.class);
        final Book book = new Book();
        final LocalDate now = LocalDate.now();
        book.setReleaseDate(now);
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");
        final Row row = poiDocumentManipulator.toRow(bookDocument, book, sheet);
        Assert.assertNotEquals(null, row);

        Assert.assertNotEquals(null, row.getCell(4));
        Assert.assertEquals(now, cellConverter.toJava(row, 4, LocalDate.class).get());
    }

    @Test(expected = CellValueConvertException.class)
    public void testCreatedRowStringIncompatibleCellValue() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final PoiDocumentManipulator poiDocumentManipulator = new PoiDocumentManipulator(ImplementationType.HSSF);
        final ExcelDocument bookDocument = configuration.lookupForDocument(java.awt.print.Book.class);
        final Book book = new Book();
        final String title = "Reza";
        book.setTitle(title);
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");
        final Row row = poiDocumentManipulator.toRow(bookDocument, book, sheet);
        Assert.assertNotEquals(null, row);

        Assert.assertNotEquals(null, row.getCell(1));
        Assert.assertEquals(title, cellConverter.toJava(row, 1, String.class).get());
        Assert.assertNotEquals("Cell value", cellConverter.toJava(row, 1, Date.class));
    }

    @Test
    public void testHssfRowModel() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final PoiDocumentManipulator poiDocumentManipulator = new PoiDocumentManipulator(ImplementationType.HSSF);
        final ExcelDocument bookDocument = configuration.lookupForDocument(java.awt.print.Book.class);
        final Book book = new Book();
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");
        final Row row = poiDocumentManipulator.toRow(bookDocument, book, sheet);
        Assert.assertNotEquals(null, row);
        Assert.assertEquals("Row class", true, row instanceof HSSFRow);
        Assert.assertEquals("Row class", true, row.getSheet() instanceof HSSFSheet);
        Assert.assertEquals("Row class", true, row.getSheet().getWorkbook() instanceof HSSFWorkbook);
    }

    @Test
    public void testXssfRowModel() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final PoiDocumentManipulator poiDocumentManipulator = new PoiDocumentManipulator(ImplementationType.HSSF);
        final ExcelDocument bookDocument = configuration.lookupForDocument(java.awt.print.Book.class);
        final Book book = new Book();
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.XSSF, "sample");
        final Row row = poiDocumentManipulator.toRow(bookDocument, book, sheet);
        Assert.assertNotEquals(null, row);

        Assert.assertEquals("Row class", true, row instanceof XSSFRow);
        Assert.assertEquals("Row class", true, row.getSheet() instanceof XSSFSheet);
        Assert.assertEquals("Row class", true, row.getSheet().getWorkbook() instanceof XSSFWorkbook);
    }

    private Book getSampleBook() {
        Book book = new Book();
        book.setAuthor("Reza Mousavi");
        book.setIsbn("1235432");
        book.setLanguage("Persian");
        book.setPrice(new BigDecimal(12.5));
        book.setPublisher("Cambridge");
        book.setReleaseDate(LocalDate.now());
        //book.setTitle("A guide for java lovers");
        return book;
    }

}
