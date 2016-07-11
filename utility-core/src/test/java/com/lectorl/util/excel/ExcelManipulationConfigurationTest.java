package com.lectorl.util.excel;

import com.lectorl.util.excel.exception.CellValueConvertException;
import com.lectorl.util.excel.exception.ModelNotFoundException;
import com.lectorl.util.excel.model.Book;
import com.lectorl.util.excel.model.Person;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/8/2016
 */
@RunWith(JUnit4.class)
public class ExcelManipulationConfigurationTest {

    public static final String TEST_XLS = "test.xls";

    @Test
    public void testCreatedRowIsEmpty() throws IOException {
        ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final Book book = new Book();
        final HSSFSheet sheet = getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final Row row = configuration.toRow(book, workbook, sheet, 0);
        Assert.assertNotEquals(null, row);

        for (Cell cell : row) {
            Assert.assertEquals(null, cell);
        }
    }

    @Test
    public void testCreatedRowIsNotEmpty() throws IOException {
        ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final Book book = getSampleBook();
        final HSSFSheet sheet = getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final Row row = configuration.toRow(book, workbook, sheet, 0);
        Assert.assertNotEquals(null, row);

        Assert.assertEquals("Title field equality", book.getTitle(), CellUtil.getCellValue(row, 1, String.class));
        Assert.assertEquals("Title field equality", book.getAuthor(), CellUtil.getCellValue(row, 2, String.class));
        Assert.assertEquals("Title field equality", book.getIsbn(), CellUtil.getCellValue(row, 9, String.class));
        Assert.assertEquals("Title field equality", book.getLanguage(), CellUtil.getCellValue(row, 6, String.class));
        Assert.assertEquals("Title field equality", book.getPrice(), CellUtil.getCellValue(row, 3, BigDecimal.class));
        Assert.assertEquals("Title field equality", book.getReleaseDate(), CellUtil.getCellValue(row, 4, LocalDate.class));
        Assert.assertEquals("Title field equality", null, CellUtil.getCellValue(row, 5, String.class));
    }

    @Test(expected = ModelNotFoundException.class)
    public void testEmptyModel() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        final HSSFSheet sheet = getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final Row row = configuration.toRow(new Book(), workbook, sheet, 0);
    }

    @Test(expected = ModelNotFoundException.class)
    public void testUnrelatedModel() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Person.class);
        final HSSFSheet sheet = getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final Row row = configuration.toRow(new Book(), workbook, sheet, 0);
    }
    @Test
    public void testCreatedRowStringCellValue() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final Book book = new Book();
        final String title = "Reza";
        book.setTitle(title);
        final HSSFSheet sheet = getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final Row row = configuration.toRow(book, workbook, sheet, 0);
        Assert.assertNotEquals(null, row);

        Assert.assertNotEquals(null, row.getCell(1));
        Assert.assertEquals(title, CellUtil.getCellValue(row, 1, String.class));
    }

    @Test
    public void testCreatedRowLocalDateCellValue() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final Book book = new Book();
        final LocalDate now = LocalDate.now();
        book.setReleaseDate(now);
        final HSSFSheet sheet = getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final Row row = configuration.toRow(book, workbook, sheet, 0);
        Assert.assertNotEquals(null, row);

        Assert.assertNotEquals(null, row.getCell(4));
        Assert.assertEquals(now, CellUtil.getCellValue(row, 4, LocalDate.class));
    }

    @Test(expected = CellValueConvertException.class)
    public void testCreatedRowStringIncompatibleCellValue() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Book.class);
        final Book book = new Book();
        final String title = "Reza";
        book.setTitle(title);
        final HSSFSheet sheet = getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final Row row = configuration.toRow(book, workbook, sheet, 0);
        Assert.assertNotEquals(null, row);

        Assert.assertNotEquals(null, row.getCell(1));
        Assert.assertEquals(title, CellUtil.getCellValue(row, 1, String.class));
        Assert.assertNotEquals("Cell value", CellUtil.getCellValue(row, 1, Date.class));
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

    private HSSFSheet getSheet() throws IOException {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        return workbook.createSheet();
    }

}
