package com.fiona.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
     test2();
    }
   //普通程序员
    public static void test1(){
        long start = System.currentTimeMillis();
        Long sum = 0L;
        for (int i = 0; i <= 10_0000_0000L ; i++) {
            sum+=i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum= " + "时间： " + (end-start));//2720
    }
    //forkjoin
    public static void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinDemo = new ForkJoinDemo(0L, 10_0000_0000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(forkJoinDemo);//提交任务有返回结果，execute没返回结果void
        Long sum = result.get();

        long end = System.currentTimeMillis();
        System.out.println("sum= "+ sum + "时间： " + (end-start)); //4266
    }
    //stream
    public static void test3(){
        long start = System.currentTimeMillis();
        //.rangeclose (]
        //.range ()
        LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);
        
        long end = System.currentTimeMillis();
        System.out.println("sum= " + "时间： " + (end-start));//114!!!!!
    }
}
