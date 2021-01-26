package com.fiona;

import java.util.concurrent.*;

public class ThreadPoolAdv {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,//正在办理窗口
                5,//最多5个人办理
                3,//超时等待
                TimeUnit.SECONDS,//时间单位
                new LinkedBlockingDeque<>(3), //等待区域=
                Executors.defaultThreadFactory(), //一般不动
                new ThreadPoolExecutor.AbortPolicy()//银行满了，还有人进来，不处理，抛出异常
        );


        try {
            for (int i = 0; i <= 9; i++) {
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
