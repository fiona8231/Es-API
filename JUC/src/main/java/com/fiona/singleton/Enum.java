package com.fiona.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

enum EnumSingleton {
     INSTANCE;
    public Enum getInstance(){
       return INSTANCE;
    }

}
class Test{
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        EnumSingleton instance1 = EnumSingleton.INSTANCE;
        Constructor<EnumSingleton> declaredConstructor = EnumSingleton.class.getDeclaredConstructor(String.class, int.class);
        declaredConstructor.setAccessible(true);
        EnumSingleton enumSingleton = declaredConstructor.newInstance();
        System.out.println(enumSingleton);
    }

}