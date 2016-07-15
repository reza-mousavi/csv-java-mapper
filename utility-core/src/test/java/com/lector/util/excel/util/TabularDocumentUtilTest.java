package com.lector.util.excel.util;

import com.lector.util.excel.document.ExcelDocumentBuilder;
import com.lector.util.excel.document.TabularDocument;
import com.lector.util.excel.model.Book;
import com.lector.util.excel.model.Person;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/15/2016
 */
public class TabularDocumentUtilTest {

    @Test
    public void testMaximumPosition(){
        TabularDocument document = new ExcelDocumentBuilder<Person>()
                .setClass(Person.class)
                .build();
        Assert.assertNotEquals(null, document);
        Assert.assertNotEquals(null, document.getExcelFields());
        Assert.assertEquals(5, TabularDocumentUtil.getMaximumPosition(document));

        document = new ExcelDocumentBuilder<Book>()
                .setClass(Book.class)
                .build();
        Assert.assertNotEquals(null, document);
        Assert.assertNotEquals(null, document.getExcelFields());
        Assert.assertEquals(9, TabularDocumentUtil.getMaximumPosition(document));
    }
}
