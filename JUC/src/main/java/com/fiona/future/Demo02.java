package com.fiona.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Demo02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> integerCompletableFuture
                = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"supllyAsync=>Integer");
            int i = 1/0;
            return 1024;
        });

        System.out.println(integerCompletableFuture.whenComplete((t, u) -> {
            System.out.println("t=> " + t);
            System.out.println("u=> " + u);
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 233;
        }).get());
    }
}
