package com.lector.util.tabular.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 * This annotation denotes tabular row.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Row {

    String name();

}
