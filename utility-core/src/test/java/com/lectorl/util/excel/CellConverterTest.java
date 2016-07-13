package com.lectorl.util.excel;

import com.lectorl.util.excel.exception.UnknownCellTypeException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class CellConverterTest {

    private CellConverter cellConverter;

    @Before
    public void setUp() throws Exception {
        cellConverter = new CellConverter();
    }

    @Test
    public void testNullStringValue() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final String value = null;
        final Cell cell = cellConverter.fromJava(header, 0, value);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", Cell.CELL_TYPE_BLANK, cell.getCellType());
    }

    @Test
    public void testNullBDecimalValue() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final BigDecimal value = null;
        final Cell cell = cellConverter.fromJava(header, 0, value);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", Cell.CELL_TYPE_BLANK, cell.getCellType());
    }

    @Test
    public void testUnknownTypeNullValue() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final BigInteger value = null;
        final Cell cell = cellConverter.fromJava(header, 0, value);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", Cell.CELL_TYPE_BLANK, cell.getCellType());
    }

    @Test(expected = UnknownCellTypeException.class)
    public void testUnknownTypeNotNullValue() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("sample");
        final Row header = sheet.createRow(0);
        final BigInteger value = new BigInteger("123456");
        final Cell cell = cellConverter.fromJava(header, 0, value);

        Assert.assertNotEquals(null, cell);
        Assert.assertNotEquals(null, cell.getSheet());
        Assert.assertNotEquals(null, cell.getSheet().getSheetName());
        Assert.assertEquals("Cell sheet", "sample", cell.getSheet().getSheetName());
        Assert.assertEquals("Cell row", 0, cell.getRowIndex());
        Assert.assertEquals("Cell index", 0, cell.getColumnIndex());
        Assert.assertEquals("Cell type", Cell.CELL_TYPE_BLANK, cell.getCellType());
    }
}
