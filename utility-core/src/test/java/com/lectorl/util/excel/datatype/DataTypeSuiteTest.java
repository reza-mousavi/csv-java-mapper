package com.lectorl.util.excel.datatype;

import com.lectorl.util.excel.datatype.csv.CSVDataTypeTest;
import com.lectorl.util.excel.datatype.excel.ExcelDataTypeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/15/2016
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CSVDataTypeTest.class,
        ExcelDataTypeTest.class,
})
public class DataTypeSuiteTest {
}
