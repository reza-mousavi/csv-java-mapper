package java8;

import java.util.stream.IntStream;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/15/2016
 */
public class Main {

    public static void main(String[] args) {

        IntStream.range(0,500)
                .forEach(System.out::println);

    }

}
