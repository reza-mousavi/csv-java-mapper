package com.lector.util.tabular.datatype.csv;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class CSVDataTypeOfBigDecimal implements CSVDataType<BigDecimal> {

    @Override
    public String fromJava(BigDecimal value) {
        return value != null ? value.toString() : null;
    }

    @Override
    public Optional<BigDecimal> toJava(String value) {
        return Optional.ofNullable(value).map(BigDecimal::new);
    }

    @Override
    public Class<BigDecimal> getClazz() {
        return BigDecimal.class;
    }

}
