package com.lectorl.util.excel.datatype;

import com.lectorl.util.excel.ExcelDataType;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfBigDecimal implements ExcelDataType<BigDecimal> {

    @Override
    public void fromJava(BigDecimal bigDecimal, Cell cell) {
    }

    @Override
    public Optional<BigDecimal> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Row row = cell.getRow();
        final int columnIndex = cell.getColumnIndex();
        return cellOptional
                .flatMap(e-> CellUtil.getCellDecimalValue(row, columnIndex))
                .map(BigDecimal.class::cast);
    }

    @Override
    public Class<BigDecimal> getClazz() {
        return BigDecimal.class;
    }
}
