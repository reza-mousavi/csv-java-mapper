package com.lectorl.util.excel.datatype.excel;

import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.*;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfString implements ExcelDataType<String> {

    @Override
    public Cell fromJava(Row row, int column, String value) {
        final Sheet sheet = row.getSheet();
        final Workbook workbook = sheet.getWorkbook();
        final CellStyle cellStyle = workbook.createCellStyle();
        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        cell.setCellType(Cell.CELL_TYPE_STRING);

        return cell;
    }

    @Override
    public Optional<String> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        return cellOptional
                .flatMap(e-> CellUtil.getCellStringValue(cell));
    }

    @Override
    public Class<String> getClazz() {
        return String.class;
    }
}
