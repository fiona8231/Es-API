package com.fiona.volatiletest;

import java.util.concurrent.atomic.AtomicInteger;

//不保证原子性
public class Demo2 {
    //volatile 不保证原子性
   // private volatile static int num = 0;
     private static AtomicInteger num = new AtomicInteger();

     public static void add(){
         //num++;
         num.getAndIncrement();//CAS


     }
     public static void main(String[] args) {
         //理论上为20000
         for (int i = 0; i < 20; i++) {
             new Thread(()->{
                 for (int j = 0; j < 1000; j++) {
                     add();
                 }
             }).start();
         }

         while (Thread.activeCount() > 2){//main gc
             Thread.yield();
         }
         System.out.println(Thread.currentThread().getName() + " " + num);
     }
}
