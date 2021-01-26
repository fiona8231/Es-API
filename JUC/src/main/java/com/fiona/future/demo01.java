package com.fiona.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 异步调用: CompletableFutre
 * //异步执行
 * //成功回调
 * //失败回调
 */

public class demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        CompletableFuture<Void> completableFuture
                =  CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ "-》run Asyn");
        });
        completableFuture.get();//获取阻塞执行结果
        System.out.println("111111");
    }
}
