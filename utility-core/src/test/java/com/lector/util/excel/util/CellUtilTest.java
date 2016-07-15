package com.lector.util.excel.util;

import com.lector.util.excel.datatype.excel.CellConverter;
import com.lector.util.excel.manipulator.ImplementationType;
import org.apache.poi.ss.usermodel.*;
import org.junit.Assert;
import org.junit.Before;
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

    private CellConverter cellConverter;

    @Before
    public void setUp() throws Exception {
        this.cellConverter = new CellConverter();
    }

    @Test
    public void testDateCellCreation() {
        final Row row = getSampleRow();
        final Date date = new Date();
        final Cell cell = cellConverter.fromJava(row, 0, date);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", 0, cell.getCellType());
        Assert.assertEquals("Cell value", date, cell.getDateCellValue());
    }

    private Row getSampleRow() {
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");
        return RowUtil.createRow(sheet);
    }

    @Test
    public void testLocalDateCellCreation() {
        final Row header = getSampleRow();
        final LocalDate date = LocalDate.now();
        final Cell cell = cellConverter.fromJava(header, 0, date);

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
        final Row header = getSampleRow();
        final Cell cell = cellConverter.fromJava(header, 0, true);

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
        final Row header = getSampleRow();
        final Double value = 1.2d;
        final Cell cell = cellConverter.fromJava(header, 0, value);

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
        final Row header = getSampleRow();
        final CellStyle cellStyle = header.getSheet().getWorkbook().createCellStyle();
        final Cell cell = header.createCell(0);
        cell.setCellStyle(cellStyle);
        if (value != null) {
            cell.setCellValue(value);
            cell.setCellType(Cell.CELL_TYPE_STRING);
        } else {
            cell.setCellType(Cell.CELL_TYPE_BLANK);
        }

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", cellTypeString, cell.getCellType());
        Assert.assertNotEquals(null, cell.getStringCellValue());
        Assert.assertEquals("Cell value", value, CellUtil.getCellStringValue(cell).orElse(null));
    }

    @Test
    public void assetDecimalCellValue() {
        final Row header = getSampleRow();
        final BigDecimal value = new BigDecimal("123456787654");
        final Cell cell = cellConverter.fromJava(header, 0, value);

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
        final Row header = getSampleRow();
        final BigDecimal value = null;
        final Cell cell = cellConverter.fromJava(header, 0, value);

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
        final Row row = getSampleRow();
        final Sheet sheet = row.getSheet();
        final Workbook workbook = sheet.getWorkbook();
        final String fieldValue = "Reza";
        final CellStyle cellStyle = (workbook).createCellStyle();
        final Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        if (fieldValue != null) {
            cell.setCellValue(fieldValue);
            cell.setCellType(Cell.CELL_TYPE_STRING);
        } else {
            cell.setCellType(Cell.CELL_TYPE_BLANK);
        }

        final String cellValue = cellConverter.toJava(row, 0, String.class).get();

        Assert.assertNotEquals(null, cellValue);
        Assert.assertNotEquals(null, row.getCell(0));
        Assert.assertNotEquals(null, row.getCell(0).getStringCellValue());
        Assert.assertEquals(null, fieldValue, cellValue);
    }

    @Test
    public void testGetCellLocalDateValue(){
        final Row row = getSampleRow();
        final LocalDate localDate = LocalDate.now();
        cellConverter.fromJava(row, 0, localDate);
        final LocalDate cellValue =  cellConverter.toJava(row, 0, LocalDate.class).get();

        Assert.assertNotEquals(null, cellValue);
        Assert.assertNotEquals(null, row.getCell(0));
        Assert.assertNotEquals(null, row.getCell(0).getDateCellValue());
        Assert.assertEquals("Value assertion", localDate, cellValue);
    }


    private void assertStringCell(String value, int cellTypeString) {
        final Cell cell = createStringCell(value);

        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", cellTypeString, cell.getCellType());
        Assert.assertEquals(value, CellUtil.getCellStringValue(cell).orElse(null));
    }

    private Cell createStringCell(String value) {
        final Row header = getSampleRow();
        final CellStyle cellStyle = header.getSheet().getWorkbook().createCellStyle();
        final Cell cell = header.createCell(0);
        cell.setCellStyle(cellStyle);
        if (value != null) {
            cell.setCellValue(value);
            cell.setCellType(Cell.CELL_TYPE_STRING);
        } else {
            cell.setCellType(Cell.CELL_TYPE_BLANK);
        }

        return cell;
    }

}
