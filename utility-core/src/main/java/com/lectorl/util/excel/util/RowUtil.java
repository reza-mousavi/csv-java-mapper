package com.lectorl.util.excel.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/11/2016
 */
public class RowUtil {

    public static Row createRow(Sheet sheet, int rowNumber){
        return sheet.createRow(rowNumber);
    }

}
