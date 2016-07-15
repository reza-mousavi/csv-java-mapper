package com.lector.util.excel.datatype.excel;

import com.lector.util.excel.exception.CellValueConvertException;
import com.lector.util.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.*;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class ExcelDataTypeOfDate implements ExcelDataType<Date> {

    private static final String DATE_CELL_FORMAT = "yyyy/mm/dd hh:mm";

    @Override
    public Cell fromJava(Row row, int column, Date value) {
        final Sheet sheet = row.getSheet();
        final Workbook workbook = sheet.getWorkbook();
        final CreationHelper createHelper = workbook.getCreationHelper();
        final DataFormat dataFormat = createHelper.createDataFormat();
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(dataFormat.getFormat(DATE_CELL_FORMAT));

        final Cell cell = row.createCell(column);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        return cell;
    }

    @Override
    public Optional<Date> toJava(Cell cell) {
        final Optional<Cell> cellOptional = Optional.ofNullable(cell);
        final Optional<Row> rowOptional = cellOptional.map(c -> cell.getRow());
        return rowOptional.flatMap(e -> getCellDateValue(e, cell.getColumnIndex()));
    }

    public static Optional<Date> getCellDateValue(Row row, int index) {
        final Optional<Cell> cellOptional = CellUtil.getNotBlankCell(row, index);
        return cellOptional.filter(e -> Cell.CELL_TYPE_NUMERIC == e.getCellType())
                .map(e -> Optional.ofNullable(e.getDateCellValue()))
                .orElseThrow(() -> new CellValueConvertException("Cannot retrieve date from non-numeric cell with type."));
    }

    @Override
    public Class<Date> getClazz() {
        return Date.class;
    }
}
