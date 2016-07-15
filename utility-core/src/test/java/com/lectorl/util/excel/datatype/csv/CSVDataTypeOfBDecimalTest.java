package com.lectorl.util.excel.datatype.csv;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/14/2016
 */
public class CSVDataTypeOfBDecimalTest {

    @Test
    public void testFromJava(){
        final CSVDataTypeOfString csvDataTypeOfString = new CSVDataTypeOfString();
        Assert.assertEquals(null, csvDataTypeOfString.fromJava(null));
        Assert.assertEquals("", csvDataTypeOfString.fromJava(""));
        Assert.assertEquals("Reza", csvDataTypeOfString.fromJava("Reza"));
        Assert.assertNotEquals("Hamid", csvDataTypeOfString.fromJava("Reza"));
    }

    @Test
    public void testToJava(){
        CSVDataTypeOfString csvDataTypeOfString = new CSVDataTypeOfString();
        Assert.assertEquals(false, csvDataTypeOfString.toJava(null).isPresent());
        Assert.assertEquals(true, csvDataTypeOfString.toJava("").isPresent());
        Assert.assertEquals("", csvDataTypeOfString.toJava("").get());

        Assert.assertEquals(true, csvDataTypeOfString.toJava("Reza").isPresent());
        Assert.assertEquals("Reza", csvDataTypeOfString.toJava("Reza").get());

        Assert.assertEquals(true, csvDataTypeOfString.toJava("Reza").isPresent());
        Assert.assertNotEquals("Hamid", csvDataTypeOfString.toJava("Reza").get());
    }
}
