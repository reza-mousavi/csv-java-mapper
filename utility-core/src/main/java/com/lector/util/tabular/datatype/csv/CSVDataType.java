package com.lector.util.tabular.datatype.csv;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public interface CSVDataType<T> {

    String fromJava(T value);

    Optional<T> toJava(String value);

    Class<T> getClazz();

}
