package com.lector.util.excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class CellUtil {

    public static Optional<String> getCellStringValue(Cell cell) {
        final Optional<Cell> cellOptional = getNotBlankCell(cell);
        return cellOptional.map(Cell::getStringCellValue);
    }

    public static Optional<String> getCellStringValue(Row row, int index) {
        final Optional<Cell> cell = getNotBlankCell(row, index);
        return cell.map(Cell::getStringCellValue);
    }

    public static Optional<Cell> getNotBlankCell(Row row, int index) {
        final Optional<Cell> cellOptional = Optional.ofNullable(row.getCell(index));
        return cellOptional.filter(e -> Cell.CELL_TYPE_BLANK != e.getCellType());
    }

    private static Optional<Cell> getNotBlankCell(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        return cellOptional.filter(e -> Cell.CELL_TYPE_BLANK != e.getCellType());
    }
}
