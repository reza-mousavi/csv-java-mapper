package com.lector.util.excel.datatype.excel;

import com.lector.util.excel.datatype.Blank;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfBlank implements ExcelDataType<Blank> {

    @Override
    public Cell fromJava(Row row, int column, Blank value) {
        final Cell cell = row.createCell(column);
        cell.setCellType(Cell.CELL_TYPE_BLANK);

        return cell;
    }

    @Override
    public Optional<Blank> toJava(Cell cell) {
        return Optional.empty();
    }

    @Override
    public Class<Blank> getClazz() {
        return Blank.class;
    }
}
