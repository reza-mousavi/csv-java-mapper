package com.lector.util.excel.java8;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class Java8Test {

    private static Logger logger = Logger.getLogger(Java8Test.class.getName());

    public static void main(String[] args) {
        final List<Person> persons = new ArrayList<>();
        persons.add(new Person("Reza", "Mousavi"));
        persons.add(new Person("Mona", "Yazdanpanah"));
        persons.add(new Person("Saeed", "Jalilian"));
        persons.add(new Person("Abbas", "Sharifi"));
        persons.add(new Person("Abbas", "Sharifi"));
        persons.add(new Person("Alex", "Riix"));
        persons.add(new Person("Jens", "Rasmussen"));
        persons.add(new Person("Abbas", "Lalili"));

        persons.stream()
                //.filter((Person p) -> p.getName().startsWith("R"))
                .forEach(logger::debug);
        logger.debug("**************Sorted*******************");
        persons.stream()
                //.filter((Person p) -> p.getName().startsWith("A"))
                .sorted(comparing(Person::getName).thenComparing(Person::getFamily))
               // .distinct()
                //.reduce("", (p1, p2) -> p1 + " # " + p2);
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting()))
                .keySet().stream().forEach(logger::debug);
        ;
        //.forEach(logger::debug);
    }


    private static class Person{
        private String name;
        private String family;

        public Person(String name, String family) {
            this.name = name;
            this.family = family;
        }

        public String getName() {
            return name;
        }

        public String getFamily() {
            return family;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            if (!name.equals(person.name)) return false;
            return family.equals(person.family);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + family.hashCode();
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder stringBuilder = new StringBuilder("Person{");
            stringBuilder.append("name='").append(name).append('\'');
            stringBuilder.append(", family='").append(family).append('\'');
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }
}
