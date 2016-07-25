package com.lector.util.tabular.util;

import com.lector.util.tabular.document.TabularField;
import com.lector.util.tabular.document.TabularDocument;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/15/2016
 */
public class TabularDocumentUtil {

    public static  <T> int getMaximumPosition(TabularDocument<T> tabularDocument) {
        return tabularDocument
                .getTabularFields()
                .stream()
                .mapToInt(TabularField::getPosition)
                .max().orElse(0);
    }

}
