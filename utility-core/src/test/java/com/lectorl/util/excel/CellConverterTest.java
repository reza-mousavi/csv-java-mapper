package com.lectorl.util.excel;

import com.lectorl.util.excel.exception.UnknownCellTypeException;
import com.lectorl.util.excel.manipulator.CellConverter;
import com.lectorl.util.excel.util.RowUtil;
import com.lectorl.util.excel.util.SheetUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
        final Row header = getSampleRow();
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

    private Row getSampleRow() {
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");
        return RowUtil.createRow(sheet);
    }

    @Test
    public void testNullBDecimalValue() {
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
    }

    @Test
    public void testUnknownTypeNullValue() {
        final Row header = getSampleRow();
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
        final Row header = getSampleRow();
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
