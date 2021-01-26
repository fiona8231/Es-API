package com.fiona.funtionalInterface;

import java.util.function.Predicate;

public class Predicate01 {
    public static void main(String[] args) {
//       Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.isEmpty();
//            }
//        };

        Predicate<String> predicate=(s)->{return s.isEmpty(); };

        System.out.println(predicate.test("124"));
    }
}
