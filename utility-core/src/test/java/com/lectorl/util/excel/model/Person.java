package com.lectorl.util.excel.model;

import com.lectorl.util.excel.annotation.Field;
import com.lectorl.util.excel.annotation.Row;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/6/2016
 */
@Row(name = "persons")
public class Person {

    private String name;
    private String family;

    public Person() {
    }

    public Person(String name, String family) {
        this.name = name;
        this.family = family;
    }

    @Field(position = 5, name = "Reza")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return family != null ? family.equals(person.family) : person.family == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (family != null ? family.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", family='").append(family).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
