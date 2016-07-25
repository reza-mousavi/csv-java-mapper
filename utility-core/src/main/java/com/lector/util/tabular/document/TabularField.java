package com.lector.util.tabular.document;

import java.beans.PropertyDescriptor;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class TabularField {
    private int position;
    private String name;
    private PropertyDescriptor propertyDescriptor;

    public TabularField(String name, int position, PropertyDescriptor propertyDescriptor) {
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
