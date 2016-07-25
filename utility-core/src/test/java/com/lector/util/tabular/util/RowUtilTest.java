package com.lector.util.tabular.util;

import com.lector.util.tabular.manipulator.ImplementationType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/13/2016
 */
public class RowUtilTest {

    @Test
    public void testCreateRowRowIndex() {
        final Sheet sheet = SheetUtil.createSheet(ImplementationType.HSSF, "sample");

        Assert.assertEquals("Row number ", 0, sheet.getLastRowNum());
        Assert.assertEquals("Physical Row number ", 0, sheet.getPhysicalNumberOfRows());

        final Row row = sheet.createRow(0);

        Assert.assertEquals("Row  0 ", 0, row.getRowNum());
        Assert.assertEquals("Row number ", 0, row.getRowNum());
        Assert.assertEquals("Last row number ", 0, sheet.getLastRowNum());
        Assert.assertEquals("Physical Row number ", 1, sheet.getPhysicalNumberOfRows());

        final Row duplicateRow = sheet.createRow(0);
        Assert.assertEquals("Row number ", 0, duplicateRow.getRowNum());
        Assert.assertEquals("Last row number ", 0, sheet.getLastRowNum());
        Assert.assertEquals("Physical Row number ", 1, sheet.getPhysicalNumberOfRows());

        final Row newRow = sheet.createRow(1);
        Assert.assertEquals("Row number ", 1, newRow.getRowNum());
        Assert.assertEquals("Last row number ", 1, sheet.getLastRowNum());
        Assert.assertEquals("Physical Row number ", 2, sheet.getPhysicalNumberOfRows());

    }
}
