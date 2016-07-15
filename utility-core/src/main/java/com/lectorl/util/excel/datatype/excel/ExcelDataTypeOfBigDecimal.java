package com.lectorl.util.excel.datatype.excel;

import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfBigDecimal implements ExcelDataType<BigDecimal> {

    @Override
    public Cell fromJava(Row row, int column, BigDecimal value) {
        final Sheet sheet = row.getSheet();
        final Workbook workbook = sheet.getWorkbook();
        String value1 = String.valueOf(value);
        final CellStyle cellStyle = workbook.createCellStyle();
        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value1);
        cell.setCellType(Cell.CELL_TYPE_STRING);

        return cell;
    }

    @Override
    public Optional<BigDecimal> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Optional<Row> rowOptional = cellOptional.map(c -> cell.getRow());
        return rowOptional.flatMap(e -> getCellDecimalValue(e, cell.getColumnIndex()));
    }

    public static Optional<BigDecimal> getCellDecimalValue(Row row, int column) {
        final Optional<String> cellStringValue = CellUtil.getCellStringValue(row, column);
        return cellStringValue.map(BigDecimal::new);
    }

    @Override
    public Class<BigDecimal> getClazz() {
        return BigDecimal.class;
    }
}
