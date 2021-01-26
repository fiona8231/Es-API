package com.fiona.productconsume;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockSpecific {
    public static void main(String[] args) {
        Data3 data = new Data3();
        new Thread(()->{ for (int i = 0; i < 10; i++) data.printA(); }, "A").start();
        new Thread(()->{ for (int i = 0; i < 10; i++) data.printB(); }, "B").start();
        new Thread(()->{ for (int i = 0; i < 10; i++) data.printC(); }, "C").start();
    }
}



class Data3{ //资源类
    Lock lock = new ReentrantLock();
    Condition w1 = lock.newCondition();
    Condition w2 = lock.newCondition();
    Condition w3 = lock.newCondition();
    private int num = 1; // num=1 让A 执行，num=2 让 B 执行，num=3 让 C 执行，
   public void printA(){
       lock.lock();
       try {
           //业务
          while (num!= 1){
              w1.await();
          }
          System.out.println(Thread.currentThread().getName() + "  =>AAAAAAAAA" );
          num = 2;
          w2.signal();

       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           lock.unlock();
       }
   }

    public void printB(){
        lock.lock();
        try {
            //业务
            while(num!=2){
                w2.await();
            }
            System.out.println(Thread.currentThread().getName() + "  =>BBBBBBBB" );
            num = 3;
            w3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printC(){
        lock.lock();
        try {
            //业务
         while(num!=3){
             w3.await();
         }
         System.out.println(Thread.currentThread().getName() + "  =>CCCCCCCCC" );
         num = 1;
         w1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}