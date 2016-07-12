package com.lectorl.util.excel.datatype;

import com.lectorl.util.excel.ExcelDataType;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfDate implements ExcelDataType<Date> {

    @Override
    public void fromJava(Date date, Cell cell) {
    }

    @Override
    public Optional<Date> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Row row = cell.getRow();
        final int columnIndex = cell.getColumnIndex();
        return cellOptional
                .flatMap(e-> CellUtil.getCellDateValue(row, columnIndex))
                .map(Date.class::cast);
    }

    @Override
    public Class<Date> getClazz() {
        return Date.class;
    }
}
