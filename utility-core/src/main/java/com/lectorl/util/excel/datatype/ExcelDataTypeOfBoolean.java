package com.lectorl.util.excel.datatype;

import com.lectorl.util.excel.ExcelDataType;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.*;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfBoolean implements ExcelDataType<Boolean> {

    @Override
    public Cell fromJava(Row row, int column, Boolean value) {
        final Sheet sheet = row.getSheet();
        final Workbook workbook = sheet.getWorkbook();
        final CellStyle cellStyle = workbook.createCellStyle();

        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);

        return cell;
    }

    @Override
    public Optional<Boolean> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Row row = cell.getRow();
        final int columnIndex = cell.getColumnIndex();
        return cellOptional
                .flatMap(e-> CellUtil.getCell(row, columnIndex))
                .map(Boolean.class::cast);
    }

    @Override
    public Class<Boolean> getClazz() {
        return Boolean.class;
    }
}
