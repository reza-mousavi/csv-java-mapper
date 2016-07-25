package com.lector.util.excel.document;

import com.lector.util.excel.annotation.Field;

import java.beans.PropertyDescriptor;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class ExcelField {
    private int position;
    private String name;
    private PropertyDescriptor propertyDescriptor;

    public ExcelField(String name, int position, PropertyDescriptor propertyDescriptor) {
        this.position = position;
        this.name = name;
        this.propertyDescriptor = propertyDescriptor;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }



}
