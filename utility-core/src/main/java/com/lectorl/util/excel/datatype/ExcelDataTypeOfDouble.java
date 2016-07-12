package com.lectorl.util.excel.datatype;

import com.lectorl.util.excel.ExcelDataType;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfDouble implements ExcelDataType<Double> {

    @Override
    public void fromJava(Double value, Cell cell) {
    }

    @Override
    public Optional<Double> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Row row = cell.getRow();
        final int columnIndex = cell.getColumnIndex();
        return cellOptional
                .flatMap(e-> CellUtil.getCellDoubleValue(row, columnIndex))
                .map(Double.class::cast);
    }

    @Override
    public Class<Double> getClazz() {
        return Double.class;
    }
}
