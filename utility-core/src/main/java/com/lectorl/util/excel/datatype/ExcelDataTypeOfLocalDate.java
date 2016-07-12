package com.lectorl.util.excel.datatype;

import com.lectorl.util.excel.ExcelDataType;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfLocalDate implements ExcelDataType<LocalDate> {

    @Override
    public void fromJava(LocalDate date, Cell cell) {
    }

    @Override
    public Optional<LocalDate> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Row row = cell.getRow();
        final int columnIndex = cell.getColumnIndex();
        return cellOptional
                .flatMap(e-> CellUtil.getCellLocalDateValue(row, columnIndex))
                .map(LocalDate.class::cast);
    }

    @Override
    public Class<LocalDate> getClazz() {
        return LocalDate.class;
    }
}
