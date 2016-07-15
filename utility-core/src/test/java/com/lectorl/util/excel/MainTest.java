package com.lectorl.util.excel;

import com.lectorl.util.excel.datatype.DataTypeSuiteTest;
import com.lectorl.util.excel.manipulator.DocumentManipulatorSuiteTest;
import com.lectorl.util.excel.util.UtilSuiteTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataTypeSuiteTest.class,
        UtilSuiteTest.class,
        DocumentManipulatorSuiteTest.class,
        ConfigurationTest.class,
        TabularDocumentWriterTest.class,
        BlackBoxTest.class,
})
public class MainTest {
}
