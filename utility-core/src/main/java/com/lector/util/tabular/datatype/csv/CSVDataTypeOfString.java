package com.lector.util.tabular.datatype.csv;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class CSVDataTypeOfString implements CSVDataType<String> {

    @Override
    public String fromJava(String value) {
        return value;
    }

    @Override
    public Optional<String> toJava(String value) {
        return Optional.ofNullable(value);
    }

    @Override
    public Class<String> getClazz() {
        return String.class;
    }

}
