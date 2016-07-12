package com.lectorl.util.excel.datatype;

import com.lectorl.util.excel.ExcelDataType;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfString implements ExcelDataType<String> {

    @Override
    public void fromJava(String value, Cell cell) {
    }

    @Override
    public Optional<String> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        return cellOptional
                .flatMap(e-> CellUtil.getCellStringValue(cell))
                .map(String.class::cast);
    }

    @Override
    public Class<String> getClazz() {
        return String.class;
    }
}
