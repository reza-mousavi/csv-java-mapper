package com.lectorl.util.excel.datatype.csv;

import com.lectorl.util.excel.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class CSVDataTypeOfLocalDate implements CSVDataType<LocalDate> {

    @Override
    public String fromJava(LocalDate value) {
        if (value == null) return null;
        return DateTimeUtil.toString(value);
    }

    @Override
    public Optional<LocalDate> toJava(String value) {
        return Optional.ofNullable(value)
                .flatMap(DateTimeUtil::fromStringLocalDate);
    }

    @Override
    public Class<LocalDate> getClazz() {
        return LocalDate.class;
    }

    @Override
    public Supplier<? extends CSVDataType<LocalDate>> getSupplier() {
        return CSVDataTypeOfLocalDate::new;
    }
}
