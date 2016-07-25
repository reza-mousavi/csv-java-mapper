package com.lector.util.tabular.document;

import com.lector.util.tabular.annotation.Row;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class TabularRow<T> {

    private String name;
    private Class<T> clazz;

    public TabularRow(Class<T> clazz) {
        this.clazz = clazz;
        Optional
                .of(clazz.getAnnotation(Row.class))
                .map(Row::name)
                .ifPresent((e) -> this.name = e);
    }

    public String getName() {
        return name;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
