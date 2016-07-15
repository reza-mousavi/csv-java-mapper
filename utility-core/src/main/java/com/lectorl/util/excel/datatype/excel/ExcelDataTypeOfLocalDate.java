package com.lectorl.util.excel.datatype.excel;

import com.lectorl.util.excel.util.CellUtil;
import com.lectorl.util.excel.util.DateTimeUtil;
import org.apache.poi.ss.usermodel.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfLocalDate implements ExcelDataType<LocalDate> {

    private static final String DATE_CELL_FORMAT = "yyyy/mm/dd hh:mm";

    @Override
    public Cell fromJava(Row row, int column, LocalDate value) {
        final Sheet sheet = row.getSheet();
        final Workbook workbook = sheet.getWorkbook();
        final CreationHelper createHelper = workbook.getCreationHelper();
        final DataFormat dataFormat = createHelper.createDataFormat();
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(dataFormat.getFormat(DATE_CELL_FORMAT));

        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        final Date date = DateTimeUtil.toDate(value);
        cell.setCellValue(date);
        return cell;
    }

    @Override
    public Optional<LocalDate> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Optional<Row> rowOptional = cellOptional.map(c -> cell.getRow());
        return rowOptional.flatMap(e -> getCellLocalDateValue(e, cell.getColumnIndex()));
    }

    public static Optional<LocalDate> getCellLocalDateValue(Row row, int index) {
        final Optional<Cell> localDateOptional = CellUtil.getNotBlankCell(row, index);
        return localDateOptional
                .flatMap(c -> Optional.ofNullable(c.getDateCellValue()))
                .map(DateTimeUtil::toLocalDate);
    }

    @Override
    public Class<LocalDate> getClazz() {
        return LocalDate.class;
    }
}
