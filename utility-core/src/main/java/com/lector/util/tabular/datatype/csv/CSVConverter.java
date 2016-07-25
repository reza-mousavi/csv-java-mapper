package com.lector.util.tabular.datatype.csv;

import com.lector.util.tabular.datatype.Blank;
import com.lector.util.tabular.util.AnnotationUtil;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class CSVConverter {

    private static final String EMPTY_STRING = "";
    private Map<Class<?>, CSVDataType> csvDataTypeMap;

    public CSVConverter() {
        csvDataTypeMap = new HashMap<>();
        final Package aPackage = getClass().getPackage();
        final String packageName = aPackage.getName();
        final Set<? extends CSVDataType> implementors = AnnotationUtil.createImplementors(packageName, CSVDataType.class);
        implementors.stream().forEach(e -> csvDataTypeMap.put(e.getClazz(), e));
    }

    public <T> String fromJava(T value) {
        final Optional<Class<?>> valueOp = Optional
                .ofNullable(value)
                .map(Object::getClass);
        final Class<?> clazz = valueOp.orElse(Blank.class);
        final Optional<CSVDataType<T>> dataTypeHandler = getHandler(clazz);
        return dataTypeHandler
                .map(e -> e.fromJava(value))
                .orElse(EMPTY_STRING);

    }

    public <T> Optional<T> toJava(String value, Class<T> resultClass) {
        final Optional<String> stringOp = Optional
                .ofNullable(value)
                .filter(StringUtils::hasText);

        if (!stringOp.isPresent()) return Optional.empty();
        final Optional<CSVDataType<T>> dataTypeHandler = getHandler(resultClass);
        return dataTypeHandler
                .map(e -> e.toJava(value))
                .orElseThrow(() -> new RuntimeException("Cannot find any handler for given type"));
    }

    private <T> Optional<CSVDataType<T>> getHandler(Class<?> clazz) {
        final CSVDataType<T> csvDataType = csvDataTypeMap.get(clazz);
        return Optional.ofNullable(csvDataType);
    }

}
