package com.lector.util.excel;

import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/13/2016
 */
public class Test {

    public static void main(String[] args) {
        Object obj = "Reza";
        Optional<Object> op = Optional.of(obj);
        op.map(e-> obj.getClass());
        op.ifPresent(System.out::println);

    }
}
