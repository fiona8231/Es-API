package com.fiona.stream;

import java.util.Arrays;
import java.util.List;

public class StreamDemo {

    public static void main(String[] args) {
        Person u1 = new Person(1, "a",21);
        Person u2 = new Person(2, "b",22);
        Person u3 = new Person(3, "c",23);
        Person u4 = new Person(4, "d",24);
        Person u5 = new Person(6, "e",25);
        //先转成集合
        List<Person> list = Arrays.asList(u1, u2, u3, u4, u5);
        list.stream().filter(u->{ return u.getId() % 2 == 0;})
                .filter(u->{return u.getAge() > 23;})
                .map(u->{return u.getName().toUpperCase();})
                .sorted((uu1, uu2)->{return uu2.compareTo(uu1);})
                .limit(1)
                .forEach(System.out::println);

    }

}
