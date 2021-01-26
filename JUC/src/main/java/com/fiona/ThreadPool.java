package com.fiona;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static void main(String[] args) {
         ExecutorService threadPool = Executors.newSingleThreadExecutor();
         //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //ExecutorService threadPool = Executors.newCachedThreadPool();


        try {
            for (int i = 0; i < 10; i++) {
              threadPool.execute(()->{
                  System.out.println(Thread.currentThread().getName() + "ok");
              });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //关闭线程池
            threadPool.shutdown();
        }



    }
}
