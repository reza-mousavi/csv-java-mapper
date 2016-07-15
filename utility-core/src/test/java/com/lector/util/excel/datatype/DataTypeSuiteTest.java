package com.lector.util.excel.datatype;

import com.lector.util.excel.datatype.csv.CSVDataTypeTest;
import com.lector.util.excel.datatype.excel.ExcelDataTypeTest;
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
