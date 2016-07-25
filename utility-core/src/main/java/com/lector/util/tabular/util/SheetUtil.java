package com.lector.util.tabular.util;

import com.lector.util.tabular.manipulator.ImplementationType;
import com.lector.util.tabular.exception.ExcelManipulationIOException;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/13/2016
 */
public class SheetUtil {

    public static Sheet createSheet(ImplementationType type, String name){
        if (ImplementationType.HSSF == type) {
            final Workbook workbook = new HSSFWorkbook();
            return workbook.createSheet(name);
        }
        final Workbook workbook = new XSSFWorkbook();
        return workbook.createSheet(name);
    }

    public static Sheet getSheet(ImplementationType type, InputStream inputStream, int index){
        try{
            if (ImplementationType.HSSF == type) {
                final HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
                return workbook.getSheetAt(index);
            }
            final Workbook workbook = new XSSFWorkbook(inputStream);
            return workbook.getSheetAt(index);
        } catch (OfficeXmlFileException|POIXMLException e){
            throw new ExcelManipulationIOException("Excel document is not compatible with given type : " + type, e);
        } catch (IOException e){
            throw new ExcelManipulationIOException("Cannot read tabular document", e);
        }
    }
}
