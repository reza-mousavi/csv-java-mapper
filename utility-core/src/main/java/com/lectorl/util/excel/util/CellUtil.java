package com.lectorl.util.excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class CellUtil {

    public static Optional<String> getCellStringValue(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        return getCellStringValue(cellOptional);
    }

    public static Optional<String> getCellStringValue(Row row, int index) {
        final Optional<Cell> cell = getCell(row, index);
        return getCellStringValue(cell);
    }

    private static Optional<String> getCellStringValue(Optional<Cell> cell) {
        return cell.filter(e -> e.getCellType() != Cell.CELL_TYPE_BLANK)
                .map(Cell::getStringCellValue);
    }

    public static Optional<Cell> getCell(Row row, int index) {
        final Optional<Cell> cellOptional = Optional.ofNullable(row.getCell(index));
        return cellOptional.filter(e -> Cell.CELL_TYPE_BLANK != e.getCellType());
    }
}
