package com.fiona.volatiletest;

import java.util.concurrent.TimeUnit;

public class Demo1 {

    private  static int num = 0;

    public static void main(String[] args) throws InterruptedException {


        new Thread(()->{ //线程1
            //只要num = 0 就一直循环
            while (num==0){
                System.out.println("可以顺利退出");
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
        //main 线程
        num=1;
        System.out.println(num);
    }

}
