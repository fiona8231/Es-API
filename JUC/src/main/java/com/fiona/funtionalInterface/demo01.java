package com.fiona.funtionalInterface;

import java.util.function.Function;

public class demo01 {
    public static void main(String[] args) {
//
//        Function function = new Function<String, String>() {
//            @Override
//            public String apply(String str) {
//                return str;
//            }
//        };

        Function function = (str)->{return str;};

        System.out.println(function.apply("adc"));
    }
}
