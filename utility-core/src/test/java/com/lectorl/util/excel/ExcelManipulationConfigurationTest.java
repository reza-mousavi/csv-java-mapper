package com.lectorl.util.excel;

import com.lectorl.util.excel.exception.CellValueConvertException;
import com.lectorl.util.excel.exception.ModelNotFoundException;
import com.lectorl.util.excel.model.Book;
import com.lectorl.util.excel.model.Person;
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

    @Test(expected = ModelNotFoundException.class)
    public void testEmptyModel() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.lookupForDocument(Book.class);
    }

    @Test(expected = ModelNotFoundException.class)
    public void testUnrelatedModel() throws IOException {
        final ExcelManipulationConfiguration configuration = new ExcelManipulationConfiguration();
        configuration.addModel(Person.class);
        configuration.lookupForDocument(Book.class);
    }

}
