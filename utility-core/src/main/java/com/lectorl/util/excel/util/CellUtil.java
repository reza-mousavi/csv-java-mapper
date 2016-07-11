package com.lectorl.util.excel.util;

import com.lectorl.util.excel.exception.CellValueConvertException;
import com.sun.org.apache.bcel.internal.generic.FLOAD;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

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
        cell.setCellValue(new Double(value));

        return cell;
    }

    public static Cell createCellForString(Workbook workbook, Row row, int column, String value) {
        final CellStyle cellStyle = workbook.createCellStyle();
        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        if (value != null) {
            cell.setCellValue(value);
            cell.setCellType(Cell.CELL_TYPE_STRING);
        } else{
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

    public static String getCellStringValue(Cell cell) {
        if (cell == null) return null;
        if (Cell.CELL_TYPE_BLANK == cell.getCellType()) return null;
        return cell.getStringCellValue();
    }

    public static BigDecimal getCellDecimalValue(Row row, int column) {
        final String cellStringValue = getCellStringValue(row, column);
        if (cellStringValue == null) {
            return null;
        }
        return new BigDecimal(cellStringValue);
    }

    public static LocalDate getCellLocalDateValue(Row row, int index) {
        final Cell cell = getCell(row, index);
        if (cell == null) {
            return null;
        }
        final Date input = cell.getDateCellValue();
        final Instant instant = input.toInstant();
        final ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return zdt.toLocalDate();
    }

    public static Date getCellDateValue(Row row, int index) {
        final Cell cell = getCell(row, index);
        if (cell == null) {
            return null;
        }
        final int cellType = cell.getCellType();
        if (Cell.CELL_TYPE_NUMERIC != cellType) {
            throw new CellValueConvertException("Cannot retrieve date from non-numeric cell with type : " + cellType);
        }
        return cell.getDateCellValue();
    }

    public static String getCellStringValue(Row row, int index) {
        final Cell cell = getCell(row, index);
        return CellUtil.getCellStringValue(cell);
    }

    public static Cell getCell(Row row, int index) {
        final Cell cell = row.getCell(index);
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)
            return null;
        return cell;
    }

    public static <T> T getCellValue(Row row, int position, Class<T> valueType) {
        final Cell cell = getCell(row, position);
        if (cell == null) return null;
        if (Date.class.equals(valueType)) return valueType.cast(getCellDateValue(row, position));
        if (LocalDate.class.equals(valueType)) return valueType.cast(getCellLocalDateValue(row, position));
        else if (BigDecimal.class.equals(valueType)) return valueType.cast(getCellDecimalValue(row, position));
        return valueType.cast(cell.getStringCellValue());
    }
}
