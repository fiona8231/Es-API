package com.fiona.lock;


import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //加锁
    public void myLock(){
        Thread thread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName() + "==> myLock");
        //自旋锁 -》加锁
        while (atomicReference.compareAndSet(null, thread)){

        }
    }

    //解锁
    public void myUnLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "==> myLock");
        atomicReference.compareAndSet(thread, null);
    }
}
