package com.lector.util.tabular.datatype.csv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/14/2016
 */
public class CSVDataTypeOfStringTest {

    private CSVDataTypeOfBigDecimal csvDataTypeOfBigDecimal;

    @Before
    public void setUp() throws Exception {
        csvDataTypeOfBigDecimal = new CSVDataTypeOfBigDecimal();
    }

    @Test
    public void testFromJava(){
        Assert.assertEquals(null, csvDataTypeOfBigDecimal.fromJava(null));
        Assert.assertEquals("1", csvDataTypeOfBigDecimal.fromJava(new BigDecimal("1")));
        Assert.assertEquals("1888.99999", csvDataTypeOfBigDecimal.fromJava(new BigDecimal("1888.99999")));
        Assert.assertEquals("-99999", csvDataTypeOfBigDecimal.fromJava(new BigDecimal("-99999")));
        Assert.assertNotEquals("-99998", csvDataTypeOfBigDecimal.fromJava(new BigDecimal("-99999")));
    }

    @Test
    public void testToJava(){
        Assert.assertEquals(false, csvDataTypeOfBigDecimal.toJava(null).isPresent());
        final Optional<BigDecimal> bigDecimal = csvDataTypeOfBigDecimal.toJava("1");
        Assert.assertEquals(true, bigDecimal.isPresent());
        Assert.assertEquals(new BigDecimal("1"), bigDecimal.get());

    }

    @Test(expected = NumberFormatException.class)
    public void testIncompatible(){
        final Optional<BigDecimal> bigDecimal = csvDataTypeOfBigDecimal.toJava("reza");
    }
}
