package com.lector.util.excel.datatype.csv;

import com.lector.util.excel.util.DateTimeUtil;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class CSVDataTypeOfDate implements CSVDataType<Date> {

    @Override
    public String fromJava(Date value) {
        if (value == null) return null;
        return DateTimeUtil.toString(value);
    }

    @Override
    public Optional<Date> toJava(String value) {
        return Optional.ofNullable(value)
                .flatMap(DateTimeUtil::fromStringDate);
    }

    @Override
    public Class<Date> getClazz() {
        return Date.class;
    }

}
