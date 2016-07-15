package com.lector.util.excel.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/11/2016
 */
public class RowUtil {

    public static Row createRow(Sheet sheet){
        final int rowNum = sheet.getPhysicalNumberOfRows();
        return sheet.createRow(rowNum);
    }

}
