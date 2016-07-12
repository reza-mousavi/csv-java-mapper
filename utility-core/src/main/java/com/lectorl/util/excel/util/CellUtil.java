package com.lectorl.util.excel.util;

import com.lectorl.util.excel.exception.CellValueConvertException;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/7/2016
 */
public class CellUtil {

    private static final String DATE_CELL_FORMAT = "yyyy/mm/dd hh:mm";
    private static final String NUMBER_CELL_FORMAT = "0.00";

    public static Cell createCellForDate(Workbook workbook, Row row, int column, Date value) {
        final CreationHelper createHelper = workbook.getCreationHelper();
        final DataFormat dataFormat = createHelper.createDataFormat();
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(dataFormat.getFormat(DATE_CELL_FORMAT));

        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        return cell;
    }

    public static Cell createCellForLocalDate(Workbook workbook, Row row, int column, LocalDate value) {
        final CreationHelper createHelper = workbook.getCreationHelper();
        final DataFormat dataFormat = createHelper.createDataFormat();
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(dataFormat.getFormat(DATE_CELL_FORMAT));

        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        final ZonedDateTime zonedDateTime = value.atStartOfDay(ZoneId.systemDefault());
        final Instant instant = zonedDateTime.toInstant();
        final Date date = Date.from(instant);
        cell.setCellValue(date);
        return cell;
    }

    public static Cell createCellForBoolean(Workbook workbook, Row row, int column, Boolean value) {
        final CellStyle cellStyle = workbook.createCellStyle();

        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);

        return cell;
    }

    public static Cell createCellForDouble(Workbook workbook, Row row, int column, Double value) {
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(NUMBER_CELL_FORMAT));

        final Cell cell = row.createCell(column);
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);

        return cell;
    }

    public static Cell createCellForString(Workbook workbook, Row row, int column, String value) {
        final CellStyle cellStyle = workbook.createCellStyle();
        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        if (value != null) {
            cell.setCellValue(value);
            cell.setCellType(Cell.CELL_TYPE_STRING);
        } else {
            cell.setCellType(Cell.CELL_TYPE_BLANK);
        }

        return cell;
    }

    public static Cell createCell(Workbook workbook, Row row, int column, Object value) {
        if (value instanceof Date) return createCellForDate(workbook, row, column, (Date) value);
        else if (value instanceof LocalDate) return createCellForLocalDate(workbook, row, column, (LocalDate) value);
        else if (value instanceof Boolean) return createCellForBoolean(workbook, row, column, (Boolean) value);
        else if (value instanceof Double) return createCellForDouble(workbook, row, column, (Double) value);
        return createCellForString(workbook, row, column, value != null ? String.valueOf(value) : null);
    }

    public static Optional<String> getCellStringValue(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        return getCellStringValue(cellOptional);
    }

    public static Optional<Double> getCellDoubleValue(Row row, int column) {
        final Optional<Cell> cellOptional = getCell(row, column);
        return cellOptional.filter(e -> Cell.CELL_TYPE_NUMERIC == e.getCellType())
                .map(e -> Optional.ofNullable(e.getNumericCellValue()))
                .orElseThrow(() -> new CellValueConvertException("Cannot retrieve double from non-numeric cell with type."));
    }

    public static Optional<BigDecimal> getCellDecimalValue(Row row, int column) {
        final Optional<String> cellStringValue = getCellStringValue(row, column);
        return cellStringValue.map(BigDecimal::new);
    }

    public static Optional<LocalDate> getCellLocalDateValue(Row row, int index) {
        final Optional<Cell> localDateOptional = getCell(row, index);
        return localDateOptional
                .flatMap(c -> Optional.ofNullable(c.getDateCellValue()))
                .map(DateTimeUtil::toLocalDate);
    }

    public static Optional<Date> getCellDateValue(Row row, int index) {
        final Optional<Cell> cellOptional = getCell(row, index);
        return cellOptional.filter(e -> Cell.CELL_TYPE_NUMERIC == e.getCellType())
                .map(e -> Optional.ofNullable(e.getDateCellValue()))
                .orElseThrow(() -> new CellValueConvertException("Cannot retrieve date from non-numeric cell with type."));
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
