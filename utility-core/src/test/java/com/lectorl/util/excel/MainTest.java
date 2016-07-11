package com.lectorl.util.excel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CellUtilTest.class,
        ExcelManipulationConfigurationTest.class,
        ExcelDocumentCreatorTest.class,
        BlackBoxTest.class,
})
public class MainTest {
}
