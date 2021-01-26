package com.fiona.productconsume;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JucLock {
    public static void main(String[] args) {

        Data2 data = new Data2();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();

    }
}
class Data2{
    private int total = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    //+1
    public  void increment() throws InterruptedException {

        try {
            //lock
            lock.lock();
            //业务代码
            while(total!= 0){
                condition.await();
            }
            total++;
            System.out.println(Thread.currentThread().getName() + " = > " + total);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //unlock
            lock.unlock();
        }
    }
    //-1
    public  void decrement() throws InterruptedException {
        lock.lock();
        try {
            while (total==0){
                condition.await();
            }
            total--;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + " = > " + total);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}