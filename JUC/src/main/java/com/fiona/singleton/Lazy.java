package com.fiona.singleton;

import java.util.concurrent.TimeUnit;

//懒汉单利模式
public class Lazy {
    //私有化构造器
    private Lazy(){
        System.out.println(Thread.currentThread().getName() + " ok");

    }

    private volatile static Lazy lazyMan;

    public static Lazy getInstance(){

        if(lazyMan == null) {
            synchronized (Lazy.class) {
                if (lazyMan == null) {
                    lazyMan = new Lazy();//不是原子性操作
                }

            }
        }
        return lazyMan;
    }
   
    
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                 Lazy.getInstance();
            }).start();
        }
    }
}
