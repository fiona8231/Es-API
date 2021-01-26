package com.fiona.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Reflection {

    private Reflection(){

        synchronized (Reflection.class){
           if( lazyMan!=null){
               throw new RuntimeException("不要试图使用反射破坏异常");
           }
        }
    }

    private volatile static Reflection lazyMan;
    public static Reflection getInstance(){

        if(lazyMan == null) {
            synchronized (Reflection.class) {
                if (lazyMan == null) {
                    lazyMan = new Reflection();//不是原子性操作
                }
            }
        }
        return lazyMan;
    }
     //利用反射破坏单利
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
       // Reflection instance1 = Reflection.getInstance();
        Constructor<Reflection> declaredConstructor = Reflection.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        Reflection instance1 = declaredConstructor.newInstance();
        Reflection instance2 = declaredConstructor.newInstance();
        System.out.println(instance1);
        System.out.println(instance2);
    }

}
