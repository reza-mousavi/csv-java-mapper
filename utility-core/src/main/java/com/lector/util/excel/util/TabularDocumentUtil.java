package com.lector.util.excel.util;

import com.lector.util.excel.document.ExcelField;
import com.lector.util.excel.document.TabularDocument;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/15/2016
 */
public class TabularDocumentUtil {

    public static  <T> int getMaximumPosition(TabularDocument<T> tabularDocument) {
        return tabularDocument
                .getExcelFields()
                .stream()
                .mapToInt(ExcelField::getPosition)
                .max().orElse(0);
    }

}
