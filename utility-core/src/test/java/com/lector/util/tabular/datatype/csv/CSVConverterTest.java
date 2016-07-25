package com.lector.util.tabular.datatype.csv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/15/2016
 */
public class CSVConverterTest {

    private CSVConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new CSVConverter();
    }

    @Test
    public void testFromJavaString(){
        Assert.assertNotEquals(null, converter.fromJava("Reza"));
        Assert.assertEquals("Reza", converter.fromJava("Reza"));
        Assert.assertNotEquals("reza", converter.fromJava("Reza"));
        Assert.assertNotEquals("rEza", converter.fromJava("Reza"));
    }

    @Test
    public void testToJavaStringForNotNull(){
        final Optional<String> stringOp = converter.toJava("Reza", String.class);
        Assert.assertNotEquals(null, stringOp);
        Assert.assertEquals(true, stringOp.isPresent());

        final String value = stringOp.get();
        Assert.assertEquals("Reza", value);
        Assert.assertNotEquals("reza", value);
        Assert.assertNotEquals("rEza", value);
    }

    @Test
    public void testToJavaForEmpty(){
        final Optional<String> stringOp = converter.toJava("", String.class);
        Assert.assertNotEquals(null, stringOp);
        Assert.assertEquals(false, stringOp.isPresent());

        final Optional<BigDecimal> bigDecimalOp = converter.toJava("", BigDecimal.class);
        Assert.assertNotEquals(null, bigDecimalOp);
        Assert.assertEquals(false, bigDecimalOp.isPresent());
    }

    @Test
    public void testToJavaStringForNull(){
        final Optional<String> stringOp = converter.toJava(null, String.class);
        Assert.assertNotEquals(null, stringOp);
        Assert.assertEquals(false, stringOp.isPresent());
    }

    //BidDecimal
    @Test
    public void testFromJavaBDecimal(){
        Assert.assertNotEquals(null, converter.fromJava(new BigDecimal("123245")));
        Assert.assertEquals("123245", converter.fromJava("123245"));
        Assert.assertNotEquals("12324", converter.fromJava("123245"));
        Assert.assertNotEquals("1232459", converter.fromJava("123245"));
    }

    @Test
    public void testToJavaBigDecimalForNotNull(){
        final Optional<BigDecimal> bigDecimalOptional = converter.toJava("123", BigDecimal.class);
        Assert.assertNotEquals(null, bigDecimalOptional);
        Assert.assertEquals(true, bigDecimalOptional.isPresent());

        final BigDecimal value = bigDecimalOptional.get();
        Assert.assertEquals(new BigDecimal("123"), value);
        Assert.assertNotEquals(new BigDecimal("12"), value);
        Assert.assertNotEquals(new BigDecimal("345678"), value);
    }

    @Test
    public void testToJavaBDecimalForEmpty(){
        final Optional<BigDecimal> bigDecimalOptional = converter.toJava("", BigDecimal.class);
        Assert.assertNotEquals(null, bigDecimalOptional);
        Assert.assertEquals(false, bigDecimalOptional.isPresent());
    }

    @Test
    public void testToJavaBigDecimalForNull(){
        final Optional<BigDecimal> bigDecimalOptional = converter.toJava(null, BigDecimal.class);
        Assert.assertNotEquals(null, bigDecimalOptional);
        Assert.assertEquals(false, bigDecimalOptional.isPresent());
    }
}
