package com.lector.util.tabular.datatype.csv;

import com.lector.util.tabular.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.Optional;

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

}
