package com.lectorl.util.excel.datatype.csv;

import java.util.Optional;
import java.util.function.Supplier;

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

    @Override
    public Supplier<? extends CSVDataType<String>> getSupplier() {
        return CSVDataTypeOfString::new;
    }
}
