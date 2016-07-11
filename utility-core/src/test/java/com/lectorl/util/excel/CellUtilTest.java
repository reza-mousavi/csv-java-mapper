package com.lectorl.util.excel;

import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
@RunWith(JUnit4.class)
public class CellUtilTest {

    @Test
    public void testDateCellCreation() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row row = sheet.createRow(0);
        final Date date = new Date();
        final Cell cell = CellUtil.createCell(workbook, row, 0, date);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", 0, cell.getCellType());
        Assert.assertEquals("Cell value", date, cell.getDateCellValue());
    }

    @Test
    public void testLocalDateCellCreation() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final LocalDate date = LocalDate.now();
        final Cell cell = CellUtil.createCell(workbook, header, 0, date);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", 0, cell.getCellType());


        final ZonedDateTime zonedDateTime = date.atStartOfDay(ZoneId.systemDefault());
        final Instant instant = zonedDateTime.toInstant();
        final Date expected = Date.from(instant);

        Assert.assertEquals("Cell value", expected, cell.getDateCellValue());
    }

    @Test
    public void testBooleanCellCreation() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final Cell cell = CellUtil.createCell(workbook, header, 0, true);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", Cell.CELL_TYPE_BOOLEAN, cell.getCellType());
        Assert.assertEquals("Cell value", true, cell.getBooleanCellValue());
    }

    @Test
    public void testDoubleCellCreation() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final Double value = 1.2d;
        final Cell cell = CellUtil.createCell(workbook, header, 0, value);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", Cell.CELL_TYPE_NUMERIC, cell.getCellType());
        Assert.assertEquals("Cell value", value, (Double) cell.getNumericCellValue());
    }

    @Test
    public void assetStringCellValue() {
        testStringCellTypeWithValue("Reza", Cell.CELL_TYPE_STRING);
        testStringCellTypeWithValue("", Cell.CELL_TYPE_STRING);
        testStringCellTypeWithValue(null, Cell.CELL_TYPE_BLANK);
    }

    private void testStringCellTypeWithValue(String value, int cellTypeString) {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final Cell cell = CellUtil.createCellForString(workbook, header, 0, value);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", cellTypeString, cell.getCellType());
        Assert.assertNotEquals(null, cell.getStringCellValue());
        Assert.assertEquals("Cell value", value, CellUtil.getCellStringValue(cell));
    }

    @Test
    public void assetDecimalCellValue() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final BigDecimal value = new BigDecimal("123456787654");
        final Cell cell = CellUtil.createCell(workbook, header, 0, value);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", Cell.CELL_TYPE_STRING, cell.getCellType());
        Assert.assertNotEquals(null, cell.getStringCellValue());
        Assert.assertEquals("Cell value", value, new BigDecimal(cell.getStringCellValue()));
    }

    @Test
    public void assetDecimalCellNullValue() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final BigDecimal value = null;
        final Cell cell = CellUtil.createCell(workbook, header, 0, value);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", Cell.CELL_TYPE_BLANK, cell.getCellType());
        Assert.assertNotEquals(null, cell.getStringCellValue());
        Assert.assertEquals("Cell value", value, null);
    }

    @Test
    public void assetCellStringValue(){
        assertStringCell(null, Cell.CELL_TYPE_BLANK);
        assertStringCell("", Cell.CELL_TYPE_STRING);
        assertStringCell("Reza", Cell.CELL_TYPE_STRING);
    }

    @Test
    public void testGetCellStringValue(){
        final HSSFRow row = getRow();
        final HSSFSheet sheet = row.getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final String fieldValue = "Reza";
        CellUtil.createCellForString(workbook, row, 0, fieldValue);
        final String cellValue = CellUtil.getCellValue(row, 0, String.class);

        Assert.assertNotEquals(null, cellValue);
        Assert.assertNotEquals(null, row.getCell(0));
        Assert.assertNotEquals(null, row.getCell(0).getStringCellValue());
        Assert.assertEquals(null, fieldValue, cellValue);
    }

    @Test
    public void testGetCellLocalDateValue(){
        final HSSFRow row = getRow();
        final HSSFSheet sheet = row.getSheet();
        final HSSFWorkbook workbook = sheet.getWorkbook();
        final LocalDate localDate = LocalDate.now();
        CellUtil.createCell(workbook, row, 0, localDate);
        final LocalDate cellValue = CellUtil.getCellValue(row, 0, LocalDate.class);

        Assert.assertNotEquals(null, cellValue);
        Assert.assertNotEquals(null, row.getCell(0));
        Assert.assertNotEquals(null, row.getCell(0).getDateCellValue());
        Assert.assertEquals("Value assertion", localDate, cellValue);
    }


    private HSSFRow getRow() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet();
        return sheet.createRow(0);
    }


    private void assertStringCell(String value, int cellTypeString) {
        final Cell cell = createStringCell(value);

        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", cellTypeString, cell.getCellType());
        Assert.assertEquals(value, CellUtil.getCellStringValue(cell));
    }

    private Cell createStringCell(String value) {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        return CellUtil.createCellForString(workbook, header, 0, value);
    }

}
