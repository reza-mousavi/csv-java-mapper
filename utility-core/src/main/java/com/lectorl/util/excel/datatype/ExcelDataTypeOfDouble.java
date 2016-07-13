package com.lectorl.util.excel.datatype;

import com.lectorl.util.excel.ExcelDataType;
import com.lectorl.util.excel.exception.CellValueConvertException;
import com.lectorl.util.excel.util.CellUtil;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfDouble implements ExcelDataType<Double> {

    private static final String NUMBER_CELL_FORMAT = "0.00";

    @Override
    public Cell fromJava(Row row, int column, Double value) {
        final Sheet sheet = row.getSheet();
        final Workbook workbook = sheet.getWorkbook();
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(NUMBER_CELL_FORMAT));

        final Cell cell = row.createCell(column);
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);

        return cell;
    }

    @Override
    public Optional<Double> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Row row = cell.getRow();
        final int columnIndex = cell.getColumnIndex();
        return cellOptional
                .flatMap(e-> getCellDoubleValue(row, columnIndex))
                .map(Double.class::cast);
    }

    public static Optional<Double> getCellDoubleValue(Row row, int column) {
        final Optional<Cell> cellOptional = CellUtil.getNotBlankCell(row, column);
        return cellOptional.filter(e -> Cell.CELL_TYPE_NUMERIC == e.getCellType())
                .map(e -> Optional.ofNullable(e.getNumericCellValue()))
                .orElseThrow(() -> new CellValueConvertException("Cannot retrieve double from non-numeric cell with type."));
    }



    @Override
    public Class<Double> getClazz() {
        return Double.class;
    }
}
