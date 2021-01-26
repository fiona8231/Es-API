package com.fiona.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CASDemo {


    public static void main(String[] args) {


       // AtomicInteger atomicInteger = new AtomicInteger(2020);
        AtomicStampedReference<Integer> atomicStampedRefInt = new AtomicStampedReference<>(1, 1);
        //如果我的期望值达到了，就更新，否则，不更新

//        //+++++++++++++捣乱的线程++++++++++++++++ /
//        atomicInteger.compareAndSet(2020, 3333);
//        System.out.println(atomicInteger.get());
//
//        atomicInteger.compareAndSet(3333, 2020);
//        System.out.println(atomicInteger.get());
//
//        //+++++++++++++期望的线程++++++++++++++++ /
//        //返回布尔值
//        System.out.println(atomicInteger.compareAndSet(2020, 5555));
//        System.out.println(atomicInteger.get());
//        atomicInteger.getAndIncrement();//++

        new Thread(()->{
            int stamp = atomicStampedRefInt.getStamp();//获得版本号

            System.out.println("a1=> " + atomicStampedRefInt.getStamp());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedRefInt.compareAndSet(1, 2,
                    atomicStampedRefInt.getStamp(), atomicStampedRefInt.getStamp() + 1);

            System.out.println("a2=> " + atomicStampedRefInt.getStamp());

            //想动蛋糕把值改回去 =》但是版本号还是在累加
            System.out.println(atomicStampedRefInt.compareAndSet(2,  1,
                    atomicStampedRefInt.getStamp(), atomicStampedRefInt.getStamp() + 1));

            System.out.println("a3=> " + atomicStampedRefInt.getStamp());

        }, "a").start();

        new Thread(()->{
            int stamp = atomicStampedRefInt.getStamp();//获得版本号
            System.out.println("b1=> " + atomicStampedRefInt.getStamp());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println(atomicStampedRefInt.compareAndSet(1, 6,
                    atomicStampedRefInt.getStamp(),  atomicStampedRefInt.getStamp() + 1));

            System.out.println("b2=> " + atomicStampedRefInt.getStamp());

        }, "b").start();


    }
}
