package com.lector.util.excel.util;

import com.lector.util.excel.document.TabularDocumentBuilder;
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
        TabularDocument document = new TabularDocumentBuilder<Person>()
                .setClass(Person.class)
                .build();
        Assert.assertNotEquals(null, document);
        Assert.assertNotEquals(null, document.getTabularFields());
        Assert.assertEquals(5, TabularDocumentUtil.getMaximumPosition(document));

        document = new TabularDocumentBuilder<Book>()
                .setClass(Book.class)
                .build();
        Assert.assertNotEquals(null, document);
        Assert.assertNotEquals(null, document.getTabularFields());
        Assert.assertEquals(9, TabularDocumentUtil.getMaximumPosition(document));
    }
}
