package com.lector.util.excel;

import com.lector.util.excel.exception.ModelNotFoundException;
import com.lector.util.excel.manipulator.CSVDocumentManipulator;
import com.lector.util.excel.manipulator.ImplementationType;
import com.lector.util.excel.manipulator.PoiDocumentManipulator;
import com.lector.util.excel.model.Book;
import com.lector.util.excel.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/8/2016
 */
@RunWith(JUnit4.class)
public class ConfigurationTest {

    @Test(expected = ModelNotFoundException.class)
    public void testEmptyModel() throws IOException {
        final Configuration configuration = new Configuration();
        configuration.lookupForDocument(Book.class);
    }

    @Test(expected = ModelNotFoundException.class)
    public void testUnrelatedModel() throws IOException {
        final Configuration configuration = new Configuration();
        configuration.addModel(Person.class);
        configuration.lookupForDocument(Book.class);
    }

    @Test
    public void testHssfImplementationType() throws IOException {
        final Configuration configuration = new Configuration();
        configuration.addModel(Person.class);
        configuration.setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.HSSF));

        Assert.assertNotEquals(null, configuration);
        Assert.assertNotEquals(null, configuration.getDocumentManipulator());
        Assert.assertEquals(PoiDocumentManipulator.class, configuration.getDocumentManipulator().getClass());
        Assert.assertNotEquals(CSVDocumentManipulator.class, configuration.getDocumentManipulator().getClass());
        final DocumentManipulator documentManipulator = configuration.getDocumentManipulator();
        final PoiDocumentManipulator poiDocumentManipulator = (PoiDocumentManipulator) documentManipulator;
        Assert.assertEquals(ImplementationType.HSSF, poiDocumentManipulator.getImplementationType());//HSSF
    }

    @Test
    public void testXssfImplementationType() throws IOException {
        final Configuration configuration = new Configuration();
        configuration.addModel(Person.class);
        configuration.setDocumentManipulator(new PoiDocumentManipulator(ImplementationType.XSSF));

        Assert.assertNotEquals(null, configuration);
        Assert.assertNotEquals(null, configuration.getDocumentManipulator());
        Assert.assertEquals(PoiDocumentManipulator.class, configuration.getDocumentManipulator().getClass());
        Assert.assertNotEquals(CSVDocumentManipulator.class, configuration.getDocumentManipulator().getClass());
        final DocumentManipulator documentManipulator = configuration.getDocumentManipulator();
        final PoiDocumentManipulator poiDocumentManipulator = (PoiDocumentManipulator) documentManipulator;
        Assert.assertEquals(ImplementationType.XSSF, poiDocumentManipulator.getImplementationType());//HSSF
    }

    @Test
    public void testCSVImplementationType() throws IOException {
        final Configuration configuration = new Configuration();
        configuration.addModel(Person.class);
        configuration.setDocumentManipulator(new CSVDocumentManipulator());

        Assert.assertNotEquals(null, configuration);
        Assert.assertNotEquals(null, configuration.getDocumentManipulator());
        Assert.assertEquals(CSVDocumentManipulator.class, configuration.getDocumentManipulator().getClass());
        Assert.assertNotEquals(PoiDocumentManipulator.class, configuration.getDocumentManipulator().getClass());
    }

}
